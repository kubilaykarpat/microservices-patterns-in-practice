package microservices.orders.service

import microservices.orders.model.DeliveryCreatedEvent
import microservices.orders.model.DeliveryCreationFailedEvent
import microservices.orders.model.Order
import microservices.orders.model.OrderCreatedEvent
import microservices.orders.model.OrderStatus
import microservices.orders.repository.OrderRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException
import java.util.*

const val ORDER_CREATED_EVENT_TYPE = "OrderCreated"

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val outboxMessageRelay: OutboxMessageRelay
) {

    @Transactional
    fun createOrder(order: Order): Order {
        logger.info("Transaction 1: Create order in PENDING state")
        val createdOrder = orderRepository.save(order)
        outboxMessageRelay.addEventToOutbox(
            eventType = ORDER_CREATED_EVENT_TYPE,
            aggregateId = createdOrder.id.toString(),
            eventData = createdOrder.toOrderCreatedEvent()
        )
        return createdOrder
    }

    fun getAllOrders(): List<Order> {
        return orderRepository.findAll().toList()
    }

    fun getOrderById(id: UUID): Order? {
        return orderRepository.findByIdOrNull(id)
    }

    fun getOrdersByUserId(userId: UUID): List<Order> {
        return orderRepository.findByUserId(userId)
    }

    fun updateOrder(order: Order) {
        orderRepository.save(order)
    }

    fun deleteOrder(id: UUID) {
        orderRepository.deleteById(id)
    }

    fun handleDeliveryCreated(deliveryCreatedEvent: DeliveryCreatedEvent){
        logger.info("Transaction 3: Mark order as APPROVED")
        var order = getOrderById(deliveryCreatedEvent.orderId)
                ?: throw IllegalArgumentException("Cannot find order with ID ${deliveryCreatedEvent.orderId}")
        order = order.copy(status = OrderStatus.APPROVED)
        updateOrder(order)
    }

    fun handleDeliveryCreationFailed(deliveryCreationFailedEvent: DeliveryCreationFailedEvent){
        logger.info("Compensating Transaction 1: Mark order as DENIED")
        var order = getOrderById(deliveryCreationFailedEvent.orderId)
                ?: throw IllegalArgumentException("Cannot find order with ID ${deliveryCreationFailedEvent.orderId}")
        order = order.copy(status = OrderStatus.DENIED)
        updateOrder(order)
    }
}

private fun Order.toOrderCreatedEvent(): OrderCreatedEvent = OrderCreatedEvent(id!!, userId, items)

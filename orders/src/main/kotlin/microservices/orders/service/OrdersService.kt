package microservices.orders.service

import microservices.orders.model.Order
import microservices.orders.model.OrderCreatedEvent
import microservices.orders.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

const val ORDER_CREATED_EVENT_TYPE = "OrderCreated"

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val outboxService: OutboxService
) {

    @Transactional
    fun createOrder(order: Order): Order {
        return orderRepository.save(order)
            .also { outboxService.addEventToOutbox(ORDER_CREATED_EVENT_TYPE, it.id.toString(), it.toOrderCreatedEvent()) }
    }

    fun getAllOrders(): List<Order> {
        return orderRepository.findAll().toList()
    }

    fun getOrderById(id: UUID): Order {
        return orderRepository.findById(id).get()
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
}

private fun Order.toOrderCreatedEvent(): OrderCreatedEvent = OrderCreatedEvent(id!!, userId, items)

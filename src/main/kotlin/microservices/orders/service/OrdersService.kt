package microservices.orders.service

import microservices.orders.model.Order
import microservices.orders.repository.OrderRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderService(private val orderRepository: OrderRepository) {

    fun createOrder(order: Order): Order {
        return orderRepository.save(order)
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
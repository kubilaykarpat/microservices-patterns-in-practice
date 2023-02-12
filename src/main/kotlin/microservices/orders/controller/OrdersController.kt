package microservices.orders.controller

import microservices.orders.model.Order
import microservices.orders.service.OrderService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/orders")
class OrderController(private val orderService: OrderService) {

    @PostMapping
    fun createOrder(@RequestBody order: Order): Order {
        return orderService.createOrder(order)
    }

    @GetMapping
    fun getAllOrders(): List<Order> {
        return orderService.getAllOrders()
    }

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: UUID): Order {
        return orderService.getOrderById(id)
    }

    @GetMapping("/user/{userId}")
    fun getOrdersByUserId(@PathVariable userId: UUID): List<Order> {
        return orderService.getOrdersByUserId(userId)
    }

    @PutMapping
    fun updateOrder(@RequestBody order: Order) {
        orderService.updateOrder(order)
    }

    @DeleteMapping("/{id}")
    fun deleteOrder(@PathVariable id: UUID) {
        orderService.deleteOrder(id)
    }
}
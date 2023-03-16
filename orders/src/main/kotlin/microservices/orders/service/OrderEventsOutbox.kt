package microservices.orders.service

import microservices.orders.model.Order
import org.springframework.stereotype.Service

@Service
class OrderEventsOutbox {

    fun sendOrderCreatedEvent(order: Order) {

    }
}
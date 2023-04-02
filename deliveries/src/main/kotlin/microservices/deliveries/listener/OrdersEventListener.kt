package microservices.deliveries.listener

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import microservices.deliveries.model.OrderCreatedEvent
import microservices.deliveries.service.DeliveriesService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

var logger: Logger = LoggerFactory.getLogger(OrdersEventListener::class.java)


@Component
class OrdersEventListener(private val deliveriesService: DeliveriesService) {
    @KafkaListener(topics = ["OrderCreated"])
    fun handleOrderCreatedMessage(messageString: String) {
        logger.info("Deliveries service received OrderCreated event: $messageString")

        val message: OrderCreatedEvent = jacksonObjectMapper().readValue(messageString, OrderCreatedEvent::class.java)
        deliveriesService.createDelivery(message.id)
    }
}
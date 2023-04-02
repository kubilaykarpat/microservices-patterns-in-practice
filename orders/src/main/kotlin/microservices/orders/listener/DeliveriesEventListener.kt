package microservices.orders.listener

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import microservices.orders.model.DeliveryCreatedEvent
import microservices.orders.model.DeliveryCreationFailedEvent
import microservices.orders.service.OrderService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

var logger: Logger = LoggerFactory.getLogger(DeliveriesEventListener::class.java)

@Component
class DeliveriesEventListener(
    private val orderService: OrderService
) {

    @KafkaListener(topics = ["DeliveryCreated"])
    fun handleDeliveryCreatedMessage(messageString: String) {
        logger.info("Orders service received DeliveryCreated event: $messageString")

        val message: DeliveryCreatedEvent =
            jacksonObjectMapper().readValue(messageString, DeliveryCreatedEvent::class.java)
        orderService.handleDeliveryCreated(message)
    }

    @KafkaListener(topics = ["DeliveryCreationFailed"])
    fun handleDeliveryCreationFailedMessage(messageString: String) {
        logger.info("Orders service received DeliveryCreationFailed event: $messageString")

        val message: DeliveryCreationFailedEvent =
            jacksonObjectMapper().readValue(messageString, DeliveryCreationFailedEvent::class.java)
        orderService.handleDeliveryCreationFailed(message)
    }
}


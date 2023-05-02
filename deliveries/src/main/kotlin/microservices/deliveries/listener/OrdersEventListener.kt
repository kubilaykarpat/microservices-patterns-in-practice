package microservices.deliveries.listener

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

var logger: Logger = LoggerFactory.getLogger(OrdersEventListener::class.java)

@Component
class OrdersEventListener {

    @KafkaListener(topics = ["OrderCreated"])
    fun listener(message: String) {
        logger.info("Deliveries service received OrderCreated event: $message")
    }
}
package microservices.deliveries

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class OrderMessagesListener {

    @KafkaListener(topics = ["orders"])
    fun listener(data: String) {
        println("Deliveries service consumed: $data")
    }
}
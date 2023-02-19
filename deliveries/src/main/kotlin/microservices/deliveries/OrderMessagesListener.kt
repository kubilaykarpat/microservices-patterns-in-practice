package microservices.deliveries

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class OrderMessagesListener {

    @KafkaListener(topics = ["getAllOrders"])
    fun listener(data: String) {
        println("consumed: $data")
    }
}
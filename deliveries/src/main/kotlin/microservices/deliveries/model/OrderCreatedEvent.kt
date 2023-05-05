package microservices.deliveries.model

import java.util.*

data class OrderCreatedEvent(
    val id: UUID,
    val userId: UUID,
    val items: List<String>
)

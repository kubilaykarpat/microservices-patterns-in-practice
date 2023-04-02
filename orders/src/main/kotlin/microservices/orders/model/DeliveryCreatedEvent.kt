package microservices.orders.model

import java.util.*

data class DeliveryCreatedEvent(
    val id: UUID,
    val orderId: UUID
)
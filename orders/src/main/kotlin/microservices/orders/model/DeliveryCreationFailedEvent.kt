package microservices.orders.model

import java.util.*

data class DeliveryCreationFailedEvent(
    val orderId: UUID
)
package microservices.orders.model

import java.util.*

data class OrderEventOutbox(
    val id: UUID,
    val eventData: String
)

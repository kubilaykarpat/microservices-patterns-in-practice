package microservices.orders.model

import java.util.*

data class Order(
    val id: UUID,
    val userId: UUID,
    val items: List<String>
)
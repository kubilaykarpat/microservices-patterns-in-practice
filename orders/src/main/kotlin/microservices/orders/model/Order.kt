package microservices.orders.model

import org.springframework.context.annotation.Primary
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("orders")
data class Order(
    @Id
    val id: UUID? = null,
    val userId: UUID,
    val items: List<String>,
    val status: OrderStatus = OrderStatus.PENDING
)

enum class OrderStatus {
    PENDING, APPROVED, DENIED
}
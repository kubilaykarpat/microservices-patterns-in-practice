package microservices.deliveries.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("deliveries")
data class Delivery(
        @Id
        val id: UUID? = null,
        val orderId: UUID,
)
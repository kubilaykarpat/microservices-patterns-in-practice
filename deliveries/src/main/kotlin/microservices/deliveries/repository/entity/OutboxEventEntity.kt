package microservices.deliveries.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType


@Table(name = "outbox")
data class OutboxEventEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        val aggregateId: String?,

        val type: String,

        val eventData: String,

        var sent: Boolean = false,

        val createdAt: LocalDateTime = LocalDateTime.now(),

        val updatedAt: LocalDateTime = LocalDateTime.now()
)

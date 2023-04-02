package microservices.orders.repository

import microservices.orders.repository.entity.OutboxEventEntity
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.sql.LockMode
import org.springframework.data.relational.repository.Lock
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.persistence.*

@Repository
interface OutboxRepository : CrudRepository<OutboxEventEntity, Long> {

    @Lock(LockMode.PESSIMISTIC_WRITE)
    @Query(value = "SELECT * FROM outbox WHERE sent = false FOR UPDATE SKIP LOCKED")
    fun findEventsToSend(): List<OutboxEventEntity>

    @Modifying
    @Query("UPDATE outbox SET sent = true WHERE id = :id")
    fun markEventAsSent(@Param("id") id: Long)
}

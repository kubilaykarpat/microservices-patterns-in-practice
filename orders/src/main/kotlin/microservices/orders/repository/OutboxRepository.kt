package microservices.orders.repository

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import microservices.orders.model.OutboxEvent
import microservices.orders.repository.entity.OutboxEventEntity
import org.springframework.core.convert.converter.Converter
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.persistence.Convert
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.IdClass

@Repository
interface OutboxRepository : CrudRepository<OutboxEventEntity, Long> {

    @Query("SELECT * FROM outbox WHERE sent = false")
    fun findAllBySentIsFalse(): List<OutboxEventEntity>

    @Modifying
    @Query("UPDATE outbox SET sent = true WHERE id = :id")
    fun markEventAsSent(@Param("id") id: Long)
}

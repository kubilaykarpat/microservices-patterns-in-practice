package microservices.orders.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import microservices.orders.model.OutboxEvent
import microservices.orders.repository.OutboxRepository
import microservices.orders.repository.entity.OutboxEventEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service


@Service
class OutboxService(
    private val outboxRepository: OutboxRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper = jacksonObjectMapper()
) {

    fun addEventToOutbox(eventType: String, aggregateId: String?, eventData: Any) {
        val outboxEvent = OutboxEvent(eventType, aggregateId, eventData)
        outboxRepository.save(outboxEvent.toEntity())
    }

    @Scheduled(fixedRate = 5000) // Polls every 5 seconds
    fun processOutboxEvents() {
        val unprocessedEvents = outboxRepository.findAllBySentIsFalse()

        unprocessedEvents.forEach { event ->
            kafkaTemplate.send("orders", event.aggregateId ?: "", event.eventData)
            event.sent = true
            outboxRepository.save(event)
        }
    }
}


fun OutboxEvent.toEntity(): OutboxEventEntity {
    return OutboxEventEntity(
        aggregateId = aggregateId,
        type = type,
        eventData = jacksonObjectMapper().writeValueAsString(eventData)
    )
}
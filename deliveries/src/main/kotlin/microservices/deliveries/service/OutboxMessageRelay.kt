package microservices.deliveries.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import microservices.deliveries.model.OutboxEvent
import microservices.deliveries.repository.OutboxRepository
import microservices.deliveries.repository.entity.OutboxEventEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


var logger: Logger = LoggerFactory.getLogger(OutboxMessageRelay::class.java)


@Service
class OutboxMessageRelay(
    private val outboxRepository: OutboxRepository,
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {

    fun addEventToOutbox(eventType: String, aggregateId: String?, eventData: Any) {
        logger.info("Adding $eventType event for aggregate $aggregateId")

        val outboxEvent = OutboxEvent(eventType, aggregateId, eventData)
        outboxRepository.save(outboxEvent.toEntity())
    }

    @Scheduled(fixedRate = 5000) // Polls every 5 seconds
    @Transactional
    fun processOutboxEvents() {
        val unprocessedEvents = outboxRepository.findEventsToSend()

        if (unprocessedEvents.isEmpty()) {
            return
        }

        logger.info("Outbox message relay is processing ${unprocessedEvents.size} events in outbox table")


        unprocessedEvents.forEach { event ->
            sendEvent(event)
        }
        logger.info("Outbox message processed events in outbox table")

    }

    private fun sendEvent(event: OutboxEventEntity) {
        try {
            kafkaTemplate.send(event.type, event.aggregateId ?: "", event.eventData)
                .whenComplete { _, ex ->
                    if (ex != null) {
                        logger.warn("Can't send ${event.type} event for aggregate ${event.aggregateId}", ex)
                    } else {
                        logger.info("Sent ${event.type} event for aggregate ${event.aggregateId}", ex)
                        markAsProcessed(event)
                    }
                }
        } catch (ex: Exception) {
            logger.error("Error sending ${event.type} event for aggregate ${event.aggregateId}", ex)
        }
    }

    private fun markAsProcessed(event: OutboxEventEntity) {
        outboxRepository.markEventAsSent(event.id!!)
    }
}


fun OutboxEvent.toEntity(): OutboxEventEntity {
    return OutboxEventEntity(
        aggregateId = aggregateId,
        type = type,
        eventData = jacksonObjectMapper().writeValueAsString(eventData)
    )
}
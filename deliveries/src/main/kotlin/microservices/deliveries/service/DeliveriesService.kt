package microservices.deliveries.service

import microservices.deliveries.model.Delivery
import microservices.deliveries.model.DeliveryCreatedEvent
import microservices.deliveries.model.DeliveryCreationFailedEvent
import microservices.deliveries.repository.DeliveryRepository
import org.springframework.stereotype.Service
import java.util.*

const val DELIVERY_CREATED_EVENT_TYPE = "DeliveryCreated"
const val DELIVERY_CREATION_FAILED_EVENT_TYPE = "DeliveryCreationFailed"

@Service
class DeliveriesService(
        private val deliveryRepository: DeliveryRepository,
        private val outboxMessageRelay: OutboxMessageRelay
) {
    fun createDelivery(orderId: UUID) {
        val existingDelivery = deliveryRepository.findByOrderId(orderId)

        if (existingDelivery != null){
            logger.info("Delivery already exists for order $orderId.")
            return
        }

        logger.info("Transaction 2: Create delivery")


        var delivery = Delivery(
                orderId = orderId
        )
        try {
            // create delivery
            delivery = deliveryRepository.save(delivery)
            outboxMessageRelay.addEventToOutbox(eventType = DELIVERY_CREATED_EVENT_TYPE,
                    aggregateId = delivery.id.toString(),
                    eventData = delivery.toDeliveryCreatedEvent())
        } catch (e: Exception) {
            logger.error("Delivery creation failed for order $orderId.", e)
            outboxMessageRelay.addEventToOutbox(eventType = DELIVERY_CREATION_FAILED_EVENT_TYPE,
                    aggregateId = null,
                    eventData = delivery.toDeliveryCreationFailedEvent())
        }
    }
}

private fun Delivery.toDeliveryCreatedEvent() = DeliveryCreatedEvent(
        id = this.id!!,
        orderId = this.orderId
)

private fun Delivery.toDeliveryCreationFailedEvent() = DeliveryCreationFailedEvent(
        orderId = this.orderId
)
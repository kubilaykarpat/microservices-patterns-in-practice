package microservices.deliveries.model

import java.util.*

data class DeliveryCreationFailedEvent(val orderId: UUID)
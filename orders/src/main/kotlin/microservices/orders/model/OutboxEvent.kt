package microservices.orders.model

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

data class OutboxEvent(
    val type: String,
    val aggregateId: String?,
    val eventData: Any
) {
    fun toPayload(): String {
        val mapper = jacksonObjectMapper()
        return mapper.writeValueAsString(this)
    }
}

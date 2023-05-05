package microservices.deliveries.repository

import microservices.deliveries.model.Delivery
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DeliveryRepository : CrudRepository<Delivery, Long>{
    fun findByOrderId(orderId: UUID): Delivery?
}


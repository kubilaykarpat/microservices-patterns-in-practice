package microservices.orders.repository

import microservices.orders.model.Order
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface OrderRepository : CrudRepository<Order, UUID> {

    @Query("SELECT * FROM orders WHERE user_id = :userId")
    fun findByUserId(userId: UUID): List<Order>
}
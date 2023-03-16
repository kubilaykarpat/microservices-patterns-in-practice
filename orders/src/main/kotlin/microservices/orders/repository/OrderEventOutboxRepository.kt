package microservices.orders.repository

interface OrderEventOutboxRepository : CrudRepository<OrderEventOutbox, UUID>{

}
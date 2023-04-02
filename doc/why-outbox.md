Without transactional outbox
```mermaid
sequenceDiagram
    actor Customer
    box Orders Microservice
    participant Orders
    participant Orders DB
    end
    participant Message Broker
    box Deliveries Microservice
    participant Deliveries
    participant Deliveries DB
    end
    actor Courier
    Customer ->>+ Orders: Give an order
    Orders ->> Orders DB: Create Order row
    Orders->> Message Broker: Send OrderCreated event
    Message Broker->> Deliveries: Send OrderCreated event
    Deliveries ->> Deliveries DB: Create Deliveries row
    Deliveries ->> Courier: Send out courier for delivery



```


With transactional outbox
```mermaid
sequenceDiagram
    actor Customer
    box Orders Microservice
    participant Orders
    participant Orders DB
    participant Message Relay
    end
    participant Message Broker
    box Deliveries Microservice
    participant Deliveries
    participant Deliveries DB
    end
    actor Courier
    Customer ->>+ Orders: Give an order
    Orders ->> Orders DB: Create Order row
    Orders->> Orders DB: Store OrderCreated event
    Message Relay ->> Orders DB: Fetch unpublished events
    Message Relay ->> Message Broker: Send OrderCreated event 
    Message Broker->> Deliveries: Send OrderCreated event
    Deliveries ->> Deliveries DB: Create Deliveries row
    Deliveries ->> Courier: Send out courier for delivery

```
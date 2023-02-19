package microservices.deliveries

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DeliveriesApplication

fun main(args: Array<String>) {
	runApplication<DeliveriesApplication>(*args)
}

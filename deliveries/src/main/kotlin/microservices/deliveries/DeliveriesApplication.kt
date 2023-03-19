package microservices.deliveries

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class DeliveriesApplication

fun main(args: Array<String>) {
	runApplication<DeliveriesApplication>(*args)
}

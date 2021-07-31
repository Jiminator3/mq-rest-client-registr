package ru.jiminator.mqrestclientregistr

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.CrudRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.RouterFunctions.route
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.ok
import ru.jiminator.mqrestclientregistr.kafka.KafkaProducerConfig
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Configuration
@SpringBootApplication
class MqRestClientRegistrApplication


fun main(args: Array<String>) {
    runApplication<MqRestClientRegistrApplication>(*args)
}


interface ClientRepository : CrudRepository<Client, Long>

@Entity
class Client(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    val factAddress: String,
    val regAddress: String,
    val phone: Long,
) {

    companion object {
        private val properties = arrayOf(Client::factAddress, Client::regAddress, Client::phone)
    }

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = properties)

    override fun toString() = kotlinToString(properties = properties)

    override fun hashCode() = kotlinHashCode(properties = properties)
}

@Controller
class ClientHandler(private val repository: ClientRepository) {

    fun hello(request: ServerRequest): ServerResponse {
        return ok().body("Hello")
    }

    fun allClients(request: ServerRequest): ServerResponse {
        return ok().body(repository.findAll().toList())
    }

    fun clientById(request: ServerRequest): ServerResponse {
        val id = request.param("id").get().toLong()
        return if (repository.findById(id).isPresent) {
            ok().body(repository.findById(id))
        } else {
            ServerResponse.notFound().build()
        }
    }

    fun postClient(request: ServerRequest): ServerResponse {

        val fAddress = request.param("fAddress").get()
        val rAddress = request.param("rAddress").get()
        val phone = request.param("phone").get().toLong()

        val producer = KafkaProducerConfig().kafkaTemplate()
        val client = Client(factAddress = fAddress, regAddress = rAddress, phone = phone)
        val clientTopic = "external.in.client-info"

        producer.send(clientTopic, client).completable().join()
        return ok().body("Post Client Successful!")
    }

    @Bean
    fun router(handler: ClientHandler): RouterFunction<ServerResponse> {
        return route()
            .GET("/hello", handler::hello)
            .GET("/clients", handler::allClients)
            .GET("/post", handler::postClient)
            .GET("/client", handler::clientById).build()
    }
}

// Получаем с Kafka JSON сообщение.
@Component
class ClientConsumer(private val repository: ClientRepository) {

    private val logger = KotlinLogging.logger {}

    @KafkaListener(topics = ["external.in.client-info"])
    fun clientSave(message: Client) {
        logger.info { "Get message: $message" }
        // Сохраняем в БД.
        repository.save(message)
    }

}

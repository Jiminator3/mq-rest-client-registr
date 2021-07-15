package ru.jiminator.mqrestclientregistr

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.repository.CrudRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import javax.persistence.*


@SpringBootApplication
class MqRestClientRegistrApplication

fun main(args: Array<String>) {
    runApplication<MqRestClientRegistrApplication>(*args)
}

interface ClientRepository : CrudRepository<Client, Long>

@Entity
class Client(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null, val factAddress: String, val regAddress: String, val phone: Long) {

    companion object {
        private val properties = arrayOf(Client::factAddress, Client::regAddress, Client::phone)
    }

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = properties)

    override fun toString() = kotlinToString(properties = properties)

    override fun hashCode() = kotlinHashCode(properties = properties)
}

// Получаем с Kafka JSON сообщение.
@Component
class ClientConsumer(private val clients: ClientRepository) {

    private val logger = KotlinLogging.logger {}

    @KafkaListener(topics = ["external.in.client-info"])
    fun clientSave(message: Client) {
        logger.info { "Get message: $message" }
        // Сохраняем в БД.
        clients.save(message)
    }

}

// REST API позволяет получить данные по этим объектам.
@RestController
class ClientResource(private val clients: ClientRepository) {

    @GetMapping("/client")
    fun index(): List<Client> {
        return clients.findAll().toList()
    }

    @GetMapping("/client/{id}")
    fun findClientById(@PathVariable id: Long): Client? {
        return clients.findById(id).orElse(null)

    }
}

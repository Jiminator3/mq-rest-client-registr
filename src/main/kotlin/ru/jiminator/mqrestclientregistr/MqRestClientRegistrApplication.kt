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
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_PLAIN
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.web.servlet.function.router
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.ok
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


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




class ClientHandler(private val clients: ClientRepository) {

    fun findAll(req: ServerRequest): ServerResponse {
        return ok().body("asd")

    }

    fun byId(id: Long): Client? {
        return clients.findById(id).orElse(null)
    }
}

//@Bean
//fun routes(clients: ClientService): RouterFunction<ServerResponse> {
//    return route()
//        .GET("/client", serverRequest -> ServerResponse.ok().body(clients.all()))
//}

//@Bean
//fun myRoutes(builder: RouteLocatorBuilder): RouteLocator {
//    return builder.routes().build()
//}

//restApiHandler: RestApiHandler
internal fun buildLegacyApiRoutes() = router {
    "/client".nest {
        GET("/hello") { ok().body("Hello World") }
//        { request ->
//            val id = kotlin.runCatching { request.pathVariable("id") }
//            if (id.isSuccess) {
//                restApiHandler.Clients(request.pathVariable("id").toLong())
//            } else {
//                ServerResponse.badRequest().body(id.exceptionOrNull()?.message.orEmpty())
//            }
//        }
//        GET("/clients", restApiHandler::Clients(4))
    }
}
// TODO(Тут остановился, дочитать доки по спрингу. Лежат в ВК)
//val repository: ClientRepository = ClientRepository.save(Client(factAddress = "asd", regAddress = "asd", phone = 12312))
//
//val handler = ClientHandler(repository);
//
//val otherRoute = router {  }
//
//val route = router {
//    GET("/person/{id}", accept(APPLICATION_JSON), handler::findAll)
//}.and(otherRoute)

//var route = router {
//    GET("/hello-world", accept(TEXT_PLAIN)) {
//        ServerResponse.ok().body("Hello World")
//    }
//}

//var hello: HandlerFunction<*> =
//    HandlerFunction { ok().body(fromObject("Hello")) }
//
//var router: RouterFunction<*> = route(GET("/"), hello)

//HttpServer
//.create("localhost", 8080)
//.newHandler(new ReactorHttpHandlerAdapter(httpHandler))
//.block();

//
//class RestApiHandler(
//    private val httpClient: HttpClient,
//    private val url: URI,
//    private val repository: ClientRepository,
//) {
//
//    fun Clients(id: Long): ServerResponse {
////        private val clients: ClientRepository
////        clients.findAllById(id).orElse(null)
//
//        val clientById = id.let { repository.findById(it) }
//        val request = HttpRequest.newBuilder()
//            .uri(url.resolve("http://localhost:8080/client/"))
//            .build()
//        val resp = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream())
//        return when (resp.statusCode()) {
//            HTTP_OK -> {
//                val contentLen = resp.headers().firstValue("Content-Length").map { it.toLong() }.orElse(null)
//                val client = repository.findById(id).orElse(null)
//                val mediaType = resp.headers().firstValue("Content-Type")
//                    .map { MediaType.parseMediaType(it) }
//                    .orElse(MediaType.APPLICATION_OCTET_STREAM)
//                val stream = resp.body()
//                ok().apply {
//                    contentType(mediaType)
//                    if (contentLen != null) {
//                        contentLength(contentLen)
//                    }
//                }.body(client)
//            }
//            HTTP_NOT_FOUND -> {
//                notFound().build()
//            }
//            else -> {
//                status(HttpStatus.INTERNAL_SERVER_ERROR).build()
//            }
//        }
//    }
//}

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
//@RestController
//class ClientResource(private val clients: ClientRepository) {
//
//    @GetMapping("/client")
//    fun index(): List<Client> {
//        return clients.findAll().toList()
//    }
//
//    @GetMapping("/post")
//    fun post(
//        @RequestParam fAddress: String,
//        @RequestParam rAddress: String,
//        @RequestParam phone: Long,
//    ): List<Client> {
//
//        val producer = KafkaProducerConfig().kafkaTemplate()
//        val client = Client(factAddress = fAddress, regAddress = rAddress, phone = phone)
//        val clientTopic = "external.in.client-info"
//        producer.send(clientTopic, client).completable().join()
//
//        return clients.findAll().toList()
//    }
//
//    @GetMapping("/client/{id}")
//    fun findClientById(@PathVariable id: Long): Client? {
//        return clients.findById(id).orElse(null)
//
//    }
//}

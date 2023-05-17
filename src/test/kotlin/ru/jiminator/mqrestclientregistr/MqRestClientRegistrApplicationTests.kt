package ru.jiminator.mqrestclientregistr


import io.restassured.RestAssured.get
import org.junit.AfterClass
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.context.jdbc.SqlGroup
import ru.jiminator.mqrestclientregistr.kafka.KafkaProducerConfig


@SpringBootTest(
    classes = [MqRestClientRegistrApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@SqlGroup(
    Sql(
        "/delete-import-clients.sql",
        config = SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
)
@DirtiesContext
class KafkaClientMessageTest(@Autowired val restTemplate: TestRestTemplate) {

    // @Value("external.in.client-info")
    private val clientTopic = "external.in.client-info"

//    @AfterClass
//    @Test
//    fun `01 - Test post client in to the repository`() {
//        val url = restTemplate.rootUri
//        get("$url/post?fAddress=3mkr&rAddress=3mkr&phone=955543")
//            .then().assertThat()
//            .statusCode(200)
//    }

    //@KafkaListener(topics = ["external.in.client-info"])
//    @Test
//    fun `02 - Test client from message queue and store in database`() {

//        val url = restTemplate.rootUri
//        val producer = KafkaProducerConfig().kafkaTemplate()
//        val client = Client(id = 100500, factAddress = "3mkr", regAddress = "3mkr", phone = 955543)
//        producer.send(clientTopic, client).completable().join()
//        producer.flush()
//        get("$url/client/1")
//            .then().assertThat()
//            .statusCode(200)
//            .body("id", `is`(1))
//            .body("phone", `is`(955543))
//        get("$url/client/1").then().assertThat().statusCode(200)
//            .contentType(ContentType.JSON)
//            .body("id", `is`(1))
//            .body("factAddress", `is`("3mkr"))
//            .body("regAddress", `is`("3mkr"))
//            .body("phone", `is`(955543))
//        producer.send(clientTopic, client).completable().join()
//        assert(producer.send(clientTopic, client).completable().isDone)
//        val res = RestAssured.registerParser("text/plain", Parser.TEXT)
//    }

//    @Order(1)
//    @KafkaListener(topics = ["external.in.client-info"])
//    fun kafkaSaveMessage(repository: ClientRepository, message: Client) {
//        repository.save(message)
//    }
//
//    @Order(0)
//    fun kafkaProduce() {
//        val producer = KafkaProducerConfig().kafkaTemplate()
//        val client = Client(factAddress = "5 mkr", regAddress = "6mkr", phone = 89889778864)
//        producer.send(clientTopic, client).completable().join()
//    }
}

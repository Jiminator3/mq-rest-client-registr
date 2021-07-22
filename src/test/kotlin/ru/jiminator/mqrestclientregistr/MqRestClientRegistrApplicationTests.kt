package ru.jiminator.mqrestclientregistr


import io.restassured.RestAssured
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerConfig
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.context.jdbc.SqlGroup
import ru.jiminator.mqrestclientregistr.kafka.KafkaProducerConfig
import javax.swing.UIManager.put
import kotlin.time.measureTimedValue


@SpringBootTest(classes = [MqRestClientRegistrApplication::class])
@SqlGroup(
    Sql(
        "/delete-import-clients.sql",
        config = SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
)
@DirtiesContext
class KafkaClientMessageTest {

    val consume: ConsumerRecord<String, Client>? = null

//    @Value("{external.in.client-info}")
    private val clientTopic = "external.in.client-info"

    @KafkaListener(topics = ["external.in.client-info"])
    @Test
    fun `01 - Test client from message queue and store in database`() {
//        val producerProps = KafkaTestUtils.producerProps(embeddedKafkaBroker!!.first()).apply {
//            put(
//                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                "org.springframework.kafka.support.serializer.JsonSerializer"
//            )
//        }
        val producer = KafkaProducerConfig().kafkaTemplate()
        val client = Client(10,"5 mkr","6mkr",89889778864)
        producer.send(clientTopic, client).completable().join()
        RestAssured.get("/client").then()
            .statusCode(200)
//        println(consume!!.value())
//        val body = record.value()
//        rep.save(record.value())
//        producer.execute(KafkaOperations.ProducerCallback { Client.Companion.equals(1) })
//        RestAssured.get("/client").then()
//            .body("factAddress", `is`("5 mkr")).body("regAddress", `is`("6mkr")).body("phone", `is`(89889778864))
    }
}

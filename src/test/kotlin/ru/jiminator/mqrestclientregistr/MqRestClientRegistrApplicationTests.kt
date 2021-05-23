package ru.jiminator.mqrestclientregistr


import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.apache.kafka.clients.producer.ProducerConfig
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.utils.KafkaTestUtils


@AutoConfigureEmbeddedDatabase(type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES)
@SpringBootTest(classes = [MqRestClientRegistrApplication::class])
@EmbeddedKafka(
    partitions = 1,
    controlledShutdown = false,
    brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"]
)
@AutoConfigureTestDatabase
class KafkaClientMessageTest(
    @Autowired val embeddedKafkaBroker: List<EmbeddedKafkaBroker>,
    @Autowired val clientRepository: ClientRepository,
) {

    private val clientTopic = "external.in.client-info"


    @Test
    fun `01 - Test client from message queue and store in database`() {

        val producerProps = KafkaTestUtils.producerProps(embeddedKafkaBroker.first()).apply {
            put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.springframework.kafka.support.serializer.JsonSerializer"
            )
        }
        val producer = KafkaTemplate(DefaultKafkaProducerFactory<String, Client>(producerProps))
        val client = Client(factAddress = "5 mkr", regAddress = "6mkr", phone = 89889778864)
        producer.send(clientTopic, client).completable().join()
        val res = clientRepository.findAll().firstOrNull()
        Assertions.assertNotNull(res)
        Assertions.assertEquals(client.copy(id = res?.id), res)

    }

}

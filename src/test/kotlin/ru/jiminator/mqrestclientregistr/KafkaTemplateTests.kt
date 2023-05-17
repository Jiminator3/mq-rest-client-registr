package ru.jiminator.mqrestclientregistr

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.hamcrest.MatcherAssert.assertThat
import org.junit.ClassRule
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.KafkaMessageListenerContainer
import org.springframework.kafka.listener.MessageListener
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.hamcrest.KafkaMatchers.*
import org.springframework.kafka.test.rule.EmbeddedKafkaRule
import org.springframework.kafka.test.utils.ContainerTestUtils
import org.springframework.kafka.test.utils.KafkaTestUtils
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

//@SpringBootTest
//@EmbeddedKafka
//class KafkaTemplateTests {
//
//    @Test
//    @Throws(Exception::class)
//    fun testTemplate() {
//        val consumerProps = KafkaTestUtils.consumerProps(
//            "testT", "false",
//            embeddedKafka.embeddedKafka
//        )
//        val cf = DefaultKafkaConsumerFactory<Int, String>(consumerProps)
//        val containerProperties = ContainerProperties(TEMPLATE_TOPIC)
//        val container = KafkaMessageListenerContainer(cf, containerProperties)
//        val records: BlockingQueue<ConsumerRecord<Int, String>> = LinkedBlockingQueue()
//        container.setupMessageListener(MessageListener<Int, String> {
//            @Override
//            fun onMessage(record: ConsumerRecord<Int, String>) {
//                println(record)
//                records.add(record)
//            }
//
//        })
//        container.setBeanName("templateTests")
//        container.start()
//        ContainerTestUtils.waitForAssignment(
//            container,
//            embeddedKafka.embeddedKafka.partitionsPerTopic
//        )
//        val producerProps = KafkaTestUtils.producerProps(embeddedKafka.embeddedKafka)
//        val pf: ProducerFactory<Int, String> = DefaultKafkaProducerFactory(producerProps)
//        val template = KafkaTemplate(pf)
//        template.defaultTopic = TEMPLATE_TOPIC
//        template.sendDefault("foo")
//        assertThat(records.poll(10, TimeUnit.SECONDS), hasValue("foo"))
//        template.sendDefault(0, 2, "bar")
//        var received = records.poll(10, TimeUnit.SECONDS)
//        assertThat(received, hasKey(2))
//        assertThat(received, hasPartition(0))
//        assertThat(received, hasValue("bar"))
//        template.send(TEMPLATE_TOPIC, 0, 2, "baz")
//        received = records.poll(10, TimeUnit.SECONDS)
//        assertThat(received, hasKey(2))
//        assertThat(received, hasPartition(0))
//        assertThat(received, hasValue("baz"))
//    }
//
//    companion object {
//        private const val TEMPLATE_TOPIC = "templateTopic"
//
//        @ClassRule
//        var embeddedKafka = EmbeddedKafkaRule(1, true, TEMPLATE_TOPIC)
//    }
//}
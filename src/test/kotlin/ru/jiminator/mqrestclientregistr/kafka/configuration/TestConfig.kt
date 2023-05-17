package ru.jiminator.mqrestclientregistr.kafka.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.utils.KafkaTestUtils

@EmbeddedKafka
@EnableKafka
@TestConfiguration
class TestConfig {

//    @Autowired
//    lateinit var kafkaEmbeded: KafkaEmbedded

//    @Bean
//    fun producerFactory(): ProducerFactory<String, String> {
//        return DefaultKafkaProducerFactory(KafkaTestUtils.producerProps(kafkaEmbedded))
//    }

//    @Bean
//    fun kafkaTemplate(): KafkaTemplate<String, String> {
//        val kafkaTemplate: KafkaTemplate<String, String> = KafkaTemplate(producerFactory())
//        kafkaTemplate.defaultTopic = "topic"
//        return kafkaTemplate
//    }
}
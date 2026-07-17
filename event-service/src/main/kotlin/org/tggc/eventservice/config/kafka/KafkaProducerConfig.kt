package org.tggc.eventservice.config.kafka

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JacksonJsonSerializer
import java.util.Map

@Configuration
open class KafkaProducerConfig(
    @param:Value($$"${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String
) {

    private val producerProperties: MutableMap<String, Any> = Map.of<String, Any>(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer::class.java
    )


    @Bean
    open fun producerFactory(): ProducerFactory<String, Any> = DefaultKafkaProducerFactory(producerProperties)


    @Bean
    open fun kafkaTemplate(factory: ProducerFactory<String, Any>): KafkaTemplate<String, Any> =
        KafkaTemplate(factory)
}

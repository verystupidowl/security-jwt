package org.tggc.eventservice.config.kafka

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.serializer.JacksonJsonSerializer
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderOptions
import java.util.Map

@Configuration
open class KafkaProducerConfig(
    @param:Value($$"${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String
) {
    @Bean
    open fun kafkaSender(): KafkaSender<String?, Any?> {
        val senderOptions = SenderOptions.create<String?, Any?>(producerProperties())
        return KafkaSender.create<String?, Any?>(senderOptions)
    }

    private fun producerProperties(): MutableMap<String?, Any?> {
        return Map.of<String?, Any?>(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer::class.java
        )
    }
}

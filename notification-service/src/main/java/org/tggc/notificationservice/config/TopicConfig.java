package org.tggc.notificationservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {
    @Value("${spring.kafka-topic.simple-notification}")
    private String simpleNotificationTopic;

    @Bean
    public NewTopic resultTopic() {
        return TopicBuilder
                .name(simpleNotificationTopic)
                .build();
    }
}

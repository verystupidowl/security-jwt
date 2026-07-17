package org.tggc.notificationservice.config;

import lombok.NonNull;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<@NonNull String, @NonNull Object> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory factory, ObjectMapper objectMapper) {
        RedisSerializationContext<@NonNull String, @NonNull Object> context = RedisSerializationContext
                .<String, Object>newSerializationContext(new StringRedisSerializer())
                .value(new GenericJacksonJsonRedisSerializer(objectMapper))
                .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}

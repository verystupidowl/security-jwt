package org.tggc.authenticationservice.config.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.tggc.notificationapi.dto.NotificationRq;

public class NotificationSerializer implements Serializer<NotificationRq> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, NotificationRq data) {
        try {
            if (data == null) {
                return new byte[]{};
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error serializing NotificationRq", e);
        }
    }
}

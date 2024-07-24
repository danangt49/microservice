package com.ecommerce.product.config.kafka.consumer;

import java.util.Map;

public record OrderKafkaListenerPayload(
        String id,
        String title,
        String body,
        Map<String, Object> data
) {
}

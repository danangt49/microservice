package com.ecommerce.order.config.kafka.producer;

import java.util.Map;

public record ProductPayload(
        String id,
        String title,
        String body,
        Map<String, Object> data
) {
}

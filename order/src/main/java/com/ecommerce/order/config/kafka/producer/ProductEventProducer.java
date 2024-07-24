package com.ecommerce.order.config.kafka.producer;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductEventProducer {
    private final KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    @Value("${kafka.config.topic.order}")
    private String topic;

    public void send(ProductPayload vo) {
        log.info("Send message from payment\n---\nTOPIC: {}; \nPAYLOAD: {}\n---", topic, new Gson().toJson(vo));

        Map<String, Object> map = new HashMap<>();
        map.put("id", vo.id());
        map.put("title", vo.title());
        map.put("body", vo.body());
        map.put("data", vo.data());
        map.putAll(vo.data());
        kafkaTemplate.send(topic, vo.id(), map);
    }
}

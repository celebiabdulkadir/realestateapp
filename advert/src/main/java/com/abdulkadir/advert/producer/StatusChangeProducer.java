package com.abdulkadir.advert.producer;


import com.abdulkadir.advert.config.RabbitMQConfig;
import com.abdulkadir.advert.model.enums.AdvertStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class StatusChangeProducer {
    private final AmqpTemplate amqpTemplate;
    private final RabbitMQConfig rabbitMQConfig;

    public StatusChangeProducer(AmqpTemplate amqpTemplate, RabbitMQConfig rabbitMQConfig) {
        this.amqpTemplate = amqpTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
    }

    public void sendStatusChange(Long advertId, AdvertStatus status) {
        Map<String, Object> message = new HashMap<>();
        message.put("advertId", advertId);
        message.put("status", status);
        log.info("Sending status change message: {}", message);
        amqpTemplate.convertAndSend(rabbitMQConfig.getExchange(), rabbitMQConfig.getRoutingKey(), message);
    }
}

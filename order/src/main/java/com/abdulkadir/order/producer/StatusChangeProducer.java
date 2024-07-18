package com.abdulkadir.order.producer;


import com.abdulkadir.order.config.RabbitMQConfig;
import com.abdulkadir.order.model.enums.OrderStatus;
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

    public void sendStatusChange(Long orderId, OrderStatus status) {
        Map<String, Object> message = new HashMap<>();
        message.put("orderId", orderId);
        message.put("status", status);
        log.info("Sending status change message: {}", message);
        amqpTemplate.convertAndSend(rabbitMQConfig.getExchange(), rabbitMQConfig.getRoutingKey(), message);
    }
}

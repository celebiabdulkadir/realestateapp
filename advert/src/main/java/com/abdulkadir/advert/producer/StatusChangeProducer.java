package com.abdulkadir.advert.producer;


import com.abdulkadir.advert.config.RabbitMQConfig;
import com.abdulkadir.advert.model.enums.AdvertStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class StatusChangeProducer {

    private final AmqpTemplate amqpTemplate;
    private final RabbitMQConfig rabbitMQConfig;

    public void sendStatusChange(AdvertStatus status) {
        log.info("Sending status change message: {}", status);
        amqpTemplate.convertAndSend(rabbitMQConfig.getExchange(),rabbitMQConfig.getRoutingKey(), status);
    }
}

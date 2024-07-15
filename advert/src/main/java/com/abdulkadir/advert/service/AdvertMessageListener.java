package com.abdulkadir.advert.service;

import com.abdulkadir.advert.model.Advert;
import com.abdulkadir.advert.model.enums.AdvertStatus;
import com.abdulkadir.advert.repository.AdvertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@Slf4j
public class AdvertMessageListener {

    @Autowired
    private AdvertRepository advertRepository;

    @RabbitListener(queues = "${status.queue}")
    @Transactional
    public void onAdvertStatusMessage(Map<String, Object> message) {
        try {
            Long advertId = Long.valueOf(message.get("advertId").toString());
            AdvertStatus status = AdvertStatus.valueOf((String) message.get("status"));
            Advert advert = advertRepository.findById(advertId).orElseThrow(() -> new RuntimeException("Advert not found"));
            advert.setAdvertStatus(status);
            advertRepository.save(advert);
            log.info("Advert status updated successfully for advertId: {}", advertId);
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
            // Depending on your RabbitMQ configuration, you might want to throw the exception
            // to signal an unacknowledged message, or handle it differently.
        }
    }
}
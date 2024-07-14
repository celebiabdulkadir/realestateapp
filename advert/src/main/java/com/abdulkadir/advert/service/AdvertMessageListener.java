package com.abdulkadir.advert.service;

import com.abdulkadir.advert.model.Advert;
import com.abdulkadir.advert.model.enums.AdvertStatus;
import com.abdulkadir.advert.repository.AdvertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Component
@Slf4j
public class AdvertMessageListener {

    @Autowired
    private AdvertRepository advertRepository;

    @RabbitListener(queues = "advertStatusQueue")
    public void onAdvertStatusMessage(Map<String, Object> message) {
        Long advertId = (Long) message.get("advertId");
        AdvertStatus status = (AdvertStatus) message.get("status");
        Advert advert = advertRepository.findById(advertId).orElseThrow(() -> new RuntimeException("Advert not found"));
        advert.setAdvertStatus(status);
        advertRepository.save(advert);
    }
}
package com.abdulkadir.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "advert", url = "http://advert:8082/advert")
public interface AdvertClient {
    @GetMapping("/adverts/{id}")
    int getAdvertCount(@PathVariable("id") Long id);
}
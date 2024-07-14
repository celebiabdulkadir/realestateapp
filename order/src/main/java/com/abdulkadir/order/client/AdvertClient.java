package com.abdulkadir.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "advert", url = "localhost:8082")
public interface AdvertClient {
    @GetMapping("/adverts/{id}")
    int getAdvertCount(@PathVariable("id") Long id);
}
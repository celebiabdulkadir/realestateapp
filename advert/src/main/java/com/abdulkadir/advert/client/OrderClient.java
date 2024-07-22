package com.abdulkadir.advert.client;

import com.abdulkadir.advert.dto.response.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "order", url = "http://order:8083/order")

public interface OrderClient {
    @GetMapping("/availableAdvertRights/{id}")
    int getAvailableAdvertRights(@PathVariable("id") Long id);
}

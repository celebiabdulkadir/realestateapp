package com.abdulkadir.advert.client;

import com.abdulkadir.advert.dto.response.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "order", url = "localhost:8083")

public interface OrderClient {
    @GetMapping("/{userId}/availableAdvertRights")
    int getAvailableAdvertRights(@PathVariable("userId") Long userId);
}

package com.abdulkadir.advert.client;

import com.abdulkadir.advert.dto.response.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user", url = "http://user:8081/user")

public interface UserClient {
    @GetMapping("/exists/{id}")
    Boolean existsById(@PathVariable Long id);
}

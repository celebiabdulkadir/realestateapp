package com.abdulkadir.order.client;



import com.abdulkadir.order.client.dto.PaymentClientRequestDTO;
import com.abdulkadir.order.client.dto.PaymentClientResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "payment", url = "http://payment:8084/payment")
public interface PaymentClient {
    @PostMapping()
    ResponseEntity<PaymentClientResponseDTO> pay(@RequestBody PaymentClientRequestDTO paymentClientRequestDTO);
}

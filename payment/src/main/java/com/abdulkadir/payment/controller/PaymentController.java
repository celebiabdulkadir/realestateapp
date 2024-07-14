package com.abdulkadir.payment.controller;


import com.abdulkadir.payment.dto.request.PaymentRequestDTO;
import com.abdulkadir.payment.dto.response.PaymentResponseDTO;
import com.abdulkadir.payment.model.Payment;
import com.abdulkadir.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public List<PaymentResponseDTO> getAll() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public PaymentResponseDTO getById(Long id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/{orderId}")
    public List<PaymentResponseDTO> getByOrderId(Long orderId) {
        return paymentService.getPaymentsByOrderId(orderId);
    }

    @PostMapping
    public PaymentResponseDTO create(@RequestBody @Valid PaymentRequestDTO paymentRequestDTO) {
        return paymentService.createPayment(paymentRequestDTO);
    }

    @PutMapping("/{id}")
    public PaymentResponseDTO update(Long id, Payment payment) {
        return paymentService.updatePayment(id, payment);
    }
}

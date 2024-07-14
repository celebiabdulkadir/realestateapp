package com.abdulkadir.payment.mapper;

import com.abdulkadir.payment.dto.request.PaymentRequestDTO;
import com.abdulkadir.payment.dto.response.PaymentResponseDTO;
import com.abdulkadir.payment.model.Payment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PaymentMapper {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PaymentResponseDTO toPaymentResponseDTO(Payment payment) {
        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .totalPrice(payment.getTotalPrice())
                .paymentDate(payment.getPaymentDate().format(formatter))
                .build();
    }

    public Payment toPayment(PaymentRequestDTO paymentRequestDTO) {
        return Payment.builder()
                .orderId(paymentRequestDTO.getOrderId())
                .totalPrice(paymentRequestDTO.getTotalPrice())
                .paymentDate(LocalDateTime.now()) // Assuming payment date is set to current date/time when creating a new payment
                .build();
    }

    public Payment updatePaymentFields(Payment existingPayment, Payment paymentRequestDTO) {
        existingPayment.setTotalPrice(paymentRequestDTO.getTotalPrice());
        // Add more fields to update as necessary
        return existingPayment;
    }
}
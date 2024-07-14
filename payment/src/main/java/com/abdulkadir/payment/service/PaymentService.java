package com.abdulkadir.payment.service;


import com.abdulkadir.payment.dto.request.PaymentRequestDTO;
import com.abdulkadir.payment.dto.response.PaymentResponseDTO;
import com.abdulkadir.payment.exception.EntityNotFoundException;
import com.abdulkadir.payment.mapper.PaymentMapper;
import com.abdulkadir.payment.model.Payment;
import com.abdulkadir.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentResponseDTO getPaymentById(Long id) {
      return paymentRepository.findById(id)
              .map(paymentMapper::toPaymentResponseDTO)
              .orElseThrow(() -> new EntityNotFoundException("Payment not found with id " + id));
    }

    public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO) {
        Payment payment = paymentMapper.toPayment(paymentRequestDTO);
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toPaymentResponseDTO(savedPayment);

    }

    public PaymentResponseDTO updatePayment(Long id, Payment payment) {
        Payment existingPayment = paymentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payment not found with id " + id));
        Payment updatedPayment = paymentMapper.updatePaymentFields(existingPayment, payment);
        Payment savedPayment = paymentRepository.save(updatedPayment);
        return paymentMapper.toPaymentResponseDTO(savedPayment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public List<PaymentResponseDTO> getAllPayments() {
        List<PaymentResponseDTO> collect;
        collect = paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toPaymentResponseDTO)
                .collect(Collectors.toList());
        return collect;
    }

    public List<PaymentResponseDTO> getPaymentsByOrderId(Long orderId) {
        List<PaymentResponseDTO> collect;
        collect = paymentRepository.findAll()
                .stream()
                .filter(payment -> payment.getOrderId().equals(orderId))
                .map(paymentMapper::toPaymentResponseDTO)
                .collect(Collectors.toList());
        return collect;
    }



}

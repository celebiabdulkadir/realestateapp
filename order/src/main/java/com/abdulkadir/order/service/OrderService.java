package com.abdulkadir.order.service;

import com.abdulkadir.order.client.AdvertClient;
import com.abdulkadir.order.client.PaymentClient;
import com.abdulkadir.order.client.UserClient;
import com.abdulkadir.order.client.dto.PaymentClientRequestDTO;
import com.abdulkadir.order.client.dto.PaymentClientResponseDTO;
import com.abdulkadir.order.dto.request.OrderRequestDTO;
import com.abdulkadir.order.dto.response.OrderResponseDTO;
import com.abdulkadir.order.model.enums.PaymentStatus;
import com.abdulkadir.order.exception.EntityNotFoundException;
import com.abdulkadir.order.mapper.OrderMapper;
import com.abdulkadir.order.model.Order;
import com.abdulkadir.order.model.enums.OrderStatus;
import com.abdulkadir.order.producer.StatusChangeProducer;
import com.abdulkadir.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final AdvertClient advertClient;
    private final PaymentClient paymentClient;
    private final UserClient userClient;
    private final StatusChangeProducer statusChangeProducer;

    public List<OrderResponseDTO> getAll() {
        return orderRepository
                .findAll()
                .stream()
                .map(orderMapper::toOrderResponseDTO)
                .collect(Collectors.toList());
    }

    public OrderResponseDTO getById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toOrderResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    public List<OrderResponseDTO> getOrderByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(orderMapper::toOrderResponseDTO)
                .collect(Collectors.toList());
    }

    public OrderResponseDTO create(OrderRequestDTO orderRequestDTO) {

        if(userClient.existsById(orderRequestDTO.getUserId()) == null) {
            throw new EntityNotFoundException("User not found with id: " + orderRequestDTO.getUserId());
        }

        // Step 1: Create the order without payment details
        Order newOrder = orderMapper.toOrder(orderRequestDTO);
        newOrder.setPaymentStatus(PaymentStatus.WAITING); // Set initial payment status
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setExpiryDate(LocalDateTime.now().plusMonths(1));

        // Step 2: Save the order to generate orderId
        newOrder = orderRepository.save(newOrder);

        // Step 3: Perform the payment operation
        PaymentClientRequestDTO paymentClientRequestDTO = new PaymentClientRequestDTO();
        paymentClientRequestDTO.setOrderId(newOrder.getId());
        paymentClientRequestDTO.setTotalPrice(newOrder.getTotalPrice());

        ResponseEntity<PaymentClientResponseDTO> paymentResponse = paymentClient.pay(paymentClientRequestDTO);

        // Step 4: Update the order with payment details based on the payment response
        if (paymentResponse.getStatusCode().is2xxSuccessful()) {
            newOrder.setPaymentStatus(PaymentStatus.PAID);
            newOrder.setPaymentId(Objects.requireNonNull(paymentResponse.getBody()).getId());
            newOrder.setPaymentDate(LocalDateTime.now());
            newOrder.setPaymentAmount(newOrder.getTotalPrice());
        } else {
            newOrder.setPaymentStatus(PaymentStatus.CANCELLED);
        }

        // Step 5: Save the updated order
        Order savedOrder = orderRepository.save(newOrder);
        statusChangeProducer.sendStatusChange(savedOrder.getId(), OrderStatus.DELIVERED);


        return orderMapper.toOrderResponseDTO(savedOrder);
    }

    public OrderResponseDTO update(Long id, OrderRequestDTO orderRequestDTO) {
        return orderRepository.findById(id).map(order -> {
            Order updatedOrder = orderMapper.updateOrderFields(order, orderRequestDTO);
            Order savedOrder = orderRepository.save(updatedOrder);
            return orderMapper.toOrderResponseDTO(savedOrder);
        }).orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    public long getAvailableAdvertRights(Long userId) {
        List<Order> validOrders = orderRepository.findByUserIdAndExpiryDateAfterAndOrderStatus(userId, LocalDateTime.now(), OrderStatus.DELIVERED);
        long totalAdvertRights = validOrders.stream()
                .mapToInt(Order::getAdvertCount)
                .sum();

        long usedAdvertRights = calculateUsedAdvertRights(userId);

        return totalAdvertRights - usedAdvertRights;
    }

    private long calculateUsedAdvertRights(Long userId) {
        return advertClient.getAdvertCount(userId);
    }

    public void decrementAdvertRights(Long userId) {
        List<Order> validOrders = orderRepository.findByUserIdAndExpiryDateAfterAndOrderStatus(userId, LocalDateTime.now(),OrderStatus.DELIVERED)
                .stream()
                .sorted(Comparator.comparing(Order::getExpiryDate))
                .toList();

        for (Order order : validOrders) {
            if (order.getAdvertCount() > 0) {
                order.setAdvertCount(order.getAdvertCount() - 1);
                orderRepository.save(order);
                break;
            }
        }
    }
}
package com.abdulkadir.order.service;

import com.abdulkadir.order.client.AdvertClient;
import com.abdulkadir.order.dto.request.OrderRequestDTO;
import com.abdulkadir.order.dto.response.OrderResponseDTO;
import com.abdulkadir.order.enums.PaymentStatus;
import com.abdulkadir.order.exception.EntityNotFoundException;
import com.abdulkadir.order.mapper.OrderMapper;
import com.abdulkadir.order.model.Order;
import com.abdulkadir.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final AdvertClient advertClient;

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

    public OrderResponseDTO create(OrderRequestDTO orderRequestDTO) {
        // Process the order and add advert rights
        Order newOrder = orderMapper.toOrder(orderRequestDTO);
        newOrder.setPaymentStatus(PaymentStatus.valueOf("PAID"));
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setExpiryDate(LocalDateTime.now().plusMonths(1));
        Order savedOrder = orderRepository.save(newOrder);
        return orderMapper.toOrderResponseDTO(savedOrder);
    }

    public OrderResponseDTO update(Long id, OrderRequestDTO orderRequestDTO) {
        return orderRepository.findById(id).map(order -> {
            Order updatedOrder = orderMapper.updateOrderFields(order, orderRequestDTO);
            Order savedOrder = orderRepository.save(updatedOrder);
            return orderMapper.toOrderResponseDTO(savedOrder);
        }).orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    public int getAvailableAdvertRights(Long userId) {
        List<Order> validOrders = orderRepository.findByUserIdAndExpiryDateAfter(userId, LocalDateTime.now());
        int totalAdvertRights = validOrders.stream()
                .mapToInt(Order::getAdvertCount)
                .sum();

        int usedAdvertRights = calculateUsedAdvertRights(userId);

        return totalAdvertRights - usedAdvertRights;
    }

    private int calculateUsedAdvertRights(Long userId) {

        return advertClient.getAdvertCount(userId);
        // Implement logic to calculate the used advert rights based on user adverts
        // For example, query the adverts table for adverts created by the user

    }

    public void decrementAdvertRights(Long userId) {
        List<Order> validOrders = new ArrayList<>(orderRepository.findByUserIdAndExpiryDateAfter(userId, LocalDateTime.now()));
        validOrders.sort(Comparator.comparing(Order::getExpiryDate));

        for (Order order : validOrders) {
            if (order.getAdvertCount() > 0) {
                order.setAdvertCount(order.getAdvertCount() - 1);
                orderRepository.save(order);
                break;
            }
        }
    }
}
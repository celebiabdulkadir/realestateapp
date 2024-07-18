package com.abdulkadir.order.service;


import com.abdulkadir.order.model.Order;
import com.abdulkadir.order.model.enums.OrderStatus;
import com.abdulkadir.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@Slf4j
public class OrderMessageListener {

    @Autowired
    private OrderRepository orderRepository;

    @RabbitListener(queues = "${order.queue}")
    @Transactional
    public void onAdvertStatusMessage(Map<String, Object> message) {
        try {
            Long orderId = Long.valueOf(message.get("orderId").toString());
            OrderStatus status = OrderStatus.valueOf((String) message.get("status"));
            Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
            order.setOrderStatus(status);
            orderRepository.save(order);
            log.info("Order status updated successfully for advertId: {}", orderId);
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
            // Depending on your RabbitMQ configuration, you might want to throw the exception
            // to signal an unacknowledged message, or handle it differently.
        }
    }
}
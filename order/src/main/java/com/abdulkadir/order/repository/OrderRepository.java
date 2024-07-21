package com.abdulkadir.order.repository;

import com.abdulkadir.order.model.Order;
import com.abdulkadir.order.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Number userId);
    List<Order> findByUserIdAndExpiryDateAfterAndOrderStatus(Long userId, LocalDateTime now, OrderStatus orderStatus);


}
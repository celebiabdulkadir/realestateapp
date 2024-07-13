package com.abdulkadir.order.repository;

import com.abdulkadir.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserId(Number userId);
    List<Order> findByUserIdAndExpiryDateAfter(Long userId, LocalDateTime now);


}
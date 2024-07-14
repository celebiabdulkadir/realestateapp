package com.abdulkadir.order.repository;

import com.abdulkadir.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserId(Number userId);
    List<Order> findByUserIdAndExpiryDateAfter(Long userId, LocalDateTime now);


}
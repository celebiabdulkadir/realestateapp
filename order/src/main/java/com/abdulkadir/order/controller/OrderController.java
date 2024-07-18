package com.abdulkadir.order.controller;

import com.abdulkadir.order.dto.request.OrderRequestDTO;
import com.abdulkadir.order.dto.response.OrderResponseDTO;
import com.abdulkadir.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderResponseDTO> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @GetMapping("/availableAdvertRights/{id}")
    public long getAvailableAdvertRights(@PathVariable("id") Long userId) {
        return orderService.getAvailableAdvertRights(userId);
    }

    @PostMapping
    public OrderResponseDTO create(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        return orderService.create(orderRequestDTO);
    }
    @PutMapping("/{id}")
    public OrderResponseDTO update(@PathVariable Long id, @RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        return orderService.update(id, orderRequestDTO);
    }
}

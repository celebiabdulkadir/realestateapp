package com.abdulkadir.order.mapper;

import com.abdulkadir.order.dto.request.OrderRequestDTO;
import com.abdulkadir.order.dto.response.OrderResponseDTO;
import com.abdulkadir.order.model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public OrderResponseDTO toOrderResponseDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .packageId(order.getPackageId())
                .packageQuantity(order.getPackageQuantity())
                .packagePrice(order.getPackagePrice())
                .packageAdvertQuantity(order.getPackageAdvertQuantity())
                .advertCount(order.getAdvertCount())
                .totalPrice(order.getTotalPrice())
                .paymentAmount(order.getPaymentAmount())
                .paymentId(order.getPaymentId())
                .paymentDate(order.getPaymentDate())
                .expiryDate(order.getExpiryDate())
                .orderDate(order.getOrderDate())
                .updateDate(order.getUpdateDate())
                .orderStatus(order.getOrderStatus())
                .paymentStatus(order.getPaymentStatus())
                .build();
    }

    public Order toOrder(OrderRequestDTO orderRequestDTO) {
        return Order.builder()
                .userId(orderRequestDTO.getUserId())
                .packageId(orderRequestDTO.getPackageId())
                .packageQuantity(orderRequestDTO.getPackageQuantity())
                .packagePrice(orderRequestDTO.getPackagePrice())
                .packageAdvertQuantity(orderRequestDTO.getPackageAdvertQuantity())

                // Fields like paymentAmount, paymentId, and paymentDate are set after the payment process
                .build();
    }

    public Order updateOrderFields(Order existingOrder, OrderRequestDTO orderRequestDTO) {
        existingOrder.setPackageId(orderRequestDTO.getPackageId());
        existingOrder.setPackageQuantity(orderRequestDTO.getPackageQuantity());
        existingOrder.setPackagePrice(orderRequestDTO.getPackagePrice());
        existingOrder.setPackageAdvertQuantity(orderRequestDTO.getPackageAdvertQuantity());

        // Note: We do not update userId, orderDate, orderStatus, and paymentStatus as these fields are typically not changed after order creation
        return existingOrder;
    }
}
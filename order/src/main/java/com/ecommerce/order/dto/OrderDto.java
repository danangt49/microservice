package com.ecommerce.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.ecommerce.order.entity.Order}
 */
public record OrderDto(LocalDateTime createdAt, LocalDateTime updatedAt, Integer orderId, String customerId,
                       String shippingAddress, String orderStatus, BigDecimal totalPrice,
                       Set<OrderDetailDto> orderDetails) implements Serializable {
}
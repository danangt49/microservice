package com.ecommerce.order.dto;

import com.ecommerce.order.entity.OrderDetail;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link OrderDetail}
 */
public record OrderDetailDto(Integer orderDetailId, Integer productId,
                             Integer quantity, BigDecimal price) implements Serializable {
}
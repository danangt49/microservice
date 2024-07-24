package com.ecommerce.order.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
@Data
@RequiredArgsConstructor
public class OrderDetailVo {
    @NotNull(message = "Product Not Null")
    private Integer productId;

    @NotNull(message = "Quantity Not Null")
    @Min(1)
    private Integer quantity;

    @NotNull(message = "Price Not Null")
    private BigDecimal price;
}

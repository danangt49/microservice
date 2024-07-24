package com.ecommerce.order.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@RequiredArgsConstructor
public class OrderVo {
    @NotNull(message = "Customer Not Null")
    private String customerId;

    @NotNull(message = "Shipping Address Not Null")
    private String shippingAddress;
    private String note;

    @NotNull(message = "Total Price Not Null")
    private BigDecimal totalPrice;
    @NotNull
    private List<OrderDetailVo> item;

}

package com.ecommerce.product.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private int id;
    private String name;
    private BigDecimal price;
    private String description;
    private String image;
    private String category;
    private Integer stock;
}

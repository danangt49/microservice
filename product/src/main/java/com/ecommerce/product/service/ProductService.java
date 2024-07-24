package com.ecommerce.product.service;

import com.ecommerce.product.config.kafka.consumer.OrderKafkaListenerPayload;
import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.vo.CheckStockVo;
import com.ecommerce.product.vo.ProductQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<ProductDto> page(ProductQueryVo vo, Pageable pageable);
    String checkStock(List<CheckStockVo> vo);
    void updateStock(OrderKafkaListenerPayload payload);
}

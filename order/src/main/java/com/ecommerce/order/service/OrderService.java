package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderDto;
import com.ecommerce.order.vo.OrderQueryVo;
import com.ecommerce.order.vo.OrderVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderDto> page(OrderQueryVo vo, Pageable pageable);
    String create(OrderVo vo);
}

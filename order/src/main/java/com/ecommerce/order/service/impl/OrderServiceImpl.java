package com.ecommerce.order.service.impl;

import com.ecommerce.order.config.exception.CustomException;
import com.ecommerce.order.config.kafka.producer.ProductEventProducer;
import com.ecommerce.order.config.kafka.producer.ProductPayload;
import com.ecommerce.order.dto.OrderDetailDto;
import com.ecommerce.order.dto.OrderDto;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderDetail;
import com.ecommerce.order.entity.enumeric.OrderStatus;
import com.ecommerce.order.httpclient.HttpRequest;
import com.ecommerce.order.repository.OrderDetailRepository;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.order.service.OrderService;
import com.ecommerce.order.vo.OrderDetailVo;
import com.ecommerce.order.vo.OrderQueryVo;
import com.ecommerce.order.vo.OrderVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final ProductEventProducer productEventProducer;

    public String generateInvoiceNumber() {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear() % 100;
        int month = currentDate.getMonthValue();

        long sequenceNumber = orderRepository.count() + 1;
        String invoiceNumber = String.format("INV%02d%02d%08d", year, month, sequenceNumber);

        log.debug("Generated invoice number: {}", invoiceNumber);

        return invoiceNumber;
    }

    private OrderDto convertToDto(Order order) {
        Set<OrderDetailDto> orderDetailDtos = order.getOrderDetails().stream()
                .map(this::convertToOrderDetailDto)
                .collect(Collectors.toSet());

        return new OrderDto(
                order.getCreatedAt(),
                order.getUpdatedAt(),
                order.getOrderId(),
                order.getCustomerId(),
                order.getShippingAddress(),
                order.getOrderStatus().name(),
                order.getTotalPrice(),
                orderDetailDtos
        );
    }

    private OrderDetailDto convertToOrderDetailDto(OrderDetail orderDetail) {
        return new OrderDetailDto(
                orderDetail.getOrderDetailId(),
                orderDetail.getProductId(),
                orderDetail.getQuantity(),
                orderDetail.getPrice()
        );
    }

    @Override
    public Page<OrderDto> page(OrderQueryVo vo, Pageable pageable) {
        log.info("Fetching order page with query: {} and pageable: {}", vo, pageable);
        Page<Order> orderPage = orderRepository.findByQuery(vo.getQ(), pageable);

        List<OrderDto> orderDtos = orderPage.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(orderDtos, pageable, orderPage.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, CustomException.class})
    public String create(OrderVo vo) {
        log.info("Creating order for customer ID: {}", vo.getCustomerId());

        List<OrderDetailVo> body = new ArrayList<>(vo.getItem());
        log.debug("Order details for stock check: {}", body);

        RequestEntity<List<OrderDetailVo>> requestEntity = new RequestEntity<>(
                body, null, HttpRequest.CHECKSTOCK.getHttpMethod(), URI.create(HttpRequest.CHECKSTOCK.getUrl())
        );
        log.debug("Sending stock check request: {}", requestEntity);

        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        log.info("Stock check response: {}", response.getBody());

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Stock check failed with status: {} and message: {}", response.getStatusCode(), response.getBody());
            throw new CustomException(response.getBody(), (HttpStatus) response.getStatusCode());
        }

        Order order = new Order();
        order.setCustomerId(vo.getCustomerId());
        order.setShippingAddress(vo.getShippingAddress());
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setTotalPrice(vo.getTotalPrice());
        order.setInvoice(generateInvoiceNumber());
        order.setNote(vo.getNote());
        log.debug("Created Order entity: {}", order);

        Set<OrderDetail> orderDetails = new HashSet<>();
        for (OrderDetailVo detailVo : vo.getItem()) {
            log.debug("Processing order detail: {}", detailVo);

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProductId(detailVo.getProductId());
            orderDetail.setQuantity(detailVo.getQuantity());
            orderDetail.setPrice(detailVo.getPrice());
            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);

        orderRepository.save(order);
        orderDetailRepository.saveAll(orderDetails);
        log.info("Saved order and order details to the database");

        Map<String, Object> map = new HashMap<>();
        map.put("list", body);
        productEventProducer.send(new ProductPayload(
                UUID.randomUUID().toString(),
                "UPDATE_STOCK",
                "null",
                map
        ));
        log.debug("Sent product event for stock update with payload: {}", map);

        log.info("Order created successfully with invoice number: {}", order.getInvoice());
        return order.getInvoice();
    }
}

package com.ecommerce.product.service.impl;

import com.ecommerce.product.config.exception.CustomException;
import com.ecommerce.product.config.kafka.consumer.OrderKafkaListenerPayload;
import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.ProductService;
import com.ecommerce.product.vo.CheckStockVo;
import com.ecommerce.product.vo.ProductQueryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Page<ProductDto> page(ProductQueryVo vo, Pageable pageable) {
        log.info("Fetching products page with query: {} and pageable: {}", vo, pageable);
        return productRepository.findByQ(vo.getQ(), pageable);
    }

    @Override
    public String checkStock(List<CheckStockVo> vo) {
        log.info("Checking stock for {} items", vo.size());

        vo.forEach(checkStockVo -> {
            log.debug("Checking stock for product id: {} with requested quantity: {}", checkStockVo.getProductId(), checkStockVo.getQuantity());

            var product = getById(checkStockVo.getProductId());
            validateStock(product, checkStockVo.getQuantity());
            log.debug("Stock check passed for product id: {}", checkStockVo.getProductId());
        });

        log.info("Stock check completed successfully");
        return "Success Check Stock";
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, CustomException.class})
    public void updateStock(OrderKafkaListenerPayload payload) {
        log.info("Updating stock with payload: {}", payload);

        Map<String, Object> map = payload.data();
        var listProduct = (List<Map<String, Object>>) map.get("list");

        listProduct.forEach(data -> {
            Integer id = (Integer) data.get("id");
            Integer quantity = (Integer) data.get("quantity");

            log.debug("Processing product id: {} with quantity: {}", id, quantity);

            var product = getById(id);
            validateStock(product, quantity);

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);

            log.debug("Updated stock for product id: {}. New stock: {}", id, product.getStock());
        });

        log.info("Stock update completed successfully");
    }

    private Product getById(Integer id) {
        log.debug("Fetching product by id: {}", id);

        return productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", id);
                    return new CustomException("Product not found with id " + id, HttpStatus.NOT_FOUND);
                });
    }

    private void validateStock(Product product, int quantity) {
        if (product.getStock() < quantity) {
            log.error("Stock insufficient for product id: {}. Available: {}, Requested: {}", product.getProductId(), product.getStock(), quantity);
            throw new CustomException("Quantity exceeds stock for product id " + product.getProductId(), HttpStatus.BAD_REQUEST);
        }
    }
}

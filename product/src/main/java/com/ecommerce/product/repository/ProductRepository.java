package com.ecommerce.product.repository;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(
            value = "SELECT p.product_id as id, p.product_name as name, p.description as description, p.price as price," +
                    " p.image as image, c.category_name as category, p.stock as stock " +
                    "FROM Products p " +
                    "LEFT JOIN Categories c ON p.category_id = c.category_id " +
                    "WHERE LOWER(p.product_name) LIKE LOWER(CONCAT('%', :q, '%'))",
            nativeQuery = true
    )
    Page<ProductDto> findByQ(@Param("q") String q, Pageable pageable);
}
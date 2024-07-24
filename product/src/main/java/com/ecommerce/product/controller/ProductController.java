package com.ecommerce.product.controller;

import com.ecommerce.product.config.GlobalApiResponse;
import com.ecommerce.product.service.ProductService;
import com.ecommerce.product.vo.CheckStockVo;
import com.ecommerce.product.vo.ProductQueryVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "OrderDetail", description = "Management product")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get device with pagination")
    @Parameters({
            @Parameter(name = "page", in = ParameterIn.QUERY, description = "Page number", example = "0"),
            @Parameter(name = "size", in = ParameterIn.QUERY, description = "Page size", example = "10"),
            @Parameter(name = "sort", in = ParameterIn.QUERY, description = "Sort criteria in the format:" +
                    " property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.",
                    example = "createdAt,asc")
    })
    @GetMapping
    public GlobalApiResponse<?> page(
            @ParameterObject ProductQueryVo vo,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return new GlobalApiResponse<>(productService.page(vo, pageable), HttpStatus.OK);
    }

    @PostMapping("check-stock")
    public GlobalApiResponse<?> page(
            @RequestBody @Valid List<CheckStockVo> vo
    ) {
        return new GlobalApiResponse<>(productService.checkStock(vo), HttpStatus.OK);
    }
}

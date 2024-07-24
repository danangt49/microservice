package com.ecommerce.order.controller;

import com.ecommerce.order.config.GlobalApiResponse;
import com.ecommerce.order.service.OrderService;
import com.ecommerce.order.vo.OrderQueryVo;
import com.ecommerce.order.vo.OrderVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Order", description = "Management Order")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {

    private final OrderService orderService;

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
            @ParameterObject OrderQueryVo vo,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return new GlobalApiResponse<>(orderService.page(vo, pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get device with pagination")
    @PostMapping()
    public GlobalApiResponse<?> create(
            @RequestBody @Validated OrderVo vo
    ) {
        return new GlobalApiResponse<>(orderService.create(vo), HttpStatus.OK);
    }
}

package com.ecommerce.order.httpclient;

import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
public enum HttpRequest {
    CHECKSTOCK("http://localhost:8888/api/v1/products/chek-stock", HttpMethod.POST);

    private final String url;
    private final HttpMethod httpMethod;

    HttpRequest(String url, HttpMethod httpMethod) {
        this.url = url;
        this.httpMethod = httpMethod;
    }
}

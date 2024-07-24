package com.ecommerce.product.vo;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductQueryVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String q;
    public String getQ() {
        return Strings.isNullOrEmpty(q) ? "" : q;
    }
}

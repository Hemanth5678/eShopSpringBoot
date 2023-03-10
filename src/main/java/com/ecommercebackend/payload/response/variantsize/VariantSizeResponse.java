package com.ecommercebackend.payload.response.variantsize;

import com.ecommercebackend.payload.response.size.SizeResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VariantSizeResponse {
    private long variantSizeId;
    private String sku;
    private boolean isInStock;
    private SizeResponse size;
}

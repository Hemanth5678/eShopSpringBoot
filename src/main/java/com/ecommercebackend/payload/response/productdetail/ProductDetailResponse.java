package com.ecommercebackend.payload.response.productdetail;

import com.ecommercebackend.payload.response.product.ProductResponse;
import com.ecommercebackend.payload.response.productvariant.ProductVariantResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductDetailResponse {
    private ProductResponse product;
    private List<ProductVariantResponse> colors;
}

package com.ecommercebackend.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommercebackend.dto.Product;
import com.ecommercebackend.payload.response.productdetail.ProductDetailAdminResponse;
import com.ecommercebackend.payload.response.productdetail.ProductDetailResponse;

@Component
public class ProductDetailMapper {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductVariantMapper productVariantMapper;

    public ProductDetailResponse toProductDetailResponse(Product product) {
        return ProductDetailResponse.builder()
                .product(productMapper.toProductResponse(product))
                .colors(productVariantMapper.toListProductVariantResponse(product.getProductVariants())).build();
    }

    public ProductDetailAdminResponse toProductDetailAdminResponse(Product product) {
        return ProductDetailAdminResponse.builder()
                .product(productMapper.toProductAdminResponse(product))
                .colors(productVariantMapper.toListProductVariantResponse(product.getProductVariants())).build();
    }
}

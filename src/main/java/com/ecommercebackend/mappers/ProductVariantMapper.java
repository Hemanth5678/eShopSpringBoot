package com.ecommercebackend.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommercebackend.dto.Product;
import com.ecommercebackend.dto.ProductVariant;
import com.ecommercebackend.payload.request.color.ColorOfCreateNewProduct;
import com.ecommercebackend.payload.response.productvariant.ProductVariantResponse;

@Component
public class ProductVariantMapper {

    @Autowired
    private ColorMapper colorMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private VariantSizeMapper variantSizeMapper;

    public ProductVariant toProductVariant(ColorOfCreateNewProduct colorOfCreateNewProduct, Product product) {
        return ProductVariant.builder()
                .color(colorMapper.toColor(colorOfCreateNewProduct))
                .product(product).build();
    }

    public ProductVariantResponse toProductVariantResponse(ProductVariant productVariant) {
        return ProductVariantResponse.builder()
                .variantId(productVariant.getId())
                .color(colorMapper.toColorResponse(productVariant.getColor()))
                .images(imageMapper.toListImageResponse(productVariant.getImages()))
                .sizes(variantSizeMapper.toListVariantSizeResponse(productVariant.getVariantSizes())).build();
    }

    public List<ProductVariantResponse> toListProductVariantResponse(Set<ProductVariant> productVariants) {
        return productVariants.stream().map(this::toProductVariantResponse).collect(Collectors.toList());
    }
}

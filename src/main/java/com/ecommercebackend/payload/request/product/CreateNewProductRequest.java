package com.ecommercebackend.payload.request.product;

import com.ecommercebackend.payload.request.category.CategoryRequest;
import com.ecommercebackend.payload.request.productvariant.ProductVariantOfCreateNewProduct;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CreateNewProductRequest {
    private String name;
    private Long price;
    private String description;
    private Boolean isVisible;
    private String primaryImageName;
    private String primaryImageUrl;
    private String secondaryImageName;
    private String secondaryImageUrl;

    private CategoryRequest category;
    private List<ProductVariantOfCreateNewProduct> colors;
}

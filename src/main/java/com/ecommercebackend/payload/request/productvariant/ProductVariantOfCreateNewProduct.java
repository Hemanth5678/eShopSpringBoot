package com.ecommercebackend.payload.request.productvariant;

import com.ecommercebackend.payload.request.color.ColorOfCreateNewProduct;
import com.ecommercebackend.payload.request.image.ImageOfCreateNewProduct;
import com.ecommercebackend.payload.request.size.SizeRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductVariantOfCreateNewProduct {
    private String sku;
    private ColorOfCreateNewProduct color;
    private List<ImageOfCreateNewProduct> images;
    private List<SizeRequest> sizes;
}

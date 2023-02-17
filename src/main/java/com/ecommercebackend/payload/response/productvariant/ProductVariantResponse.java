package com.ecommercebackend.payload.response.productvariant;

import com.ecommercebackend.payload.response.color.ColorResponse;
import com.ecommercebackend.payload.response.image.ImageResponse;
import com.ecommercebackend.payload.response.variantsize.VariantSizeResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductVariantResponse {
    private long variantId;
    private ColorResponse color;
    private List<ImageResponse> images;
    private List<VariantSizeResponse> sizes;
}

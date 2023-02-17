package com.ecommercebackend.mappers;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommercebackend.dto.ProductVariant;
import com.ecommercebackend.dto.Dimensions;
import com.ecommercebackend.dto.VariantSize;
import com.ecommercebackend.payload.request.size.SizeRequest;
import com.ecommercebackend.payload.response.variantsize.VariantSizeResponse;
import com.ecommercebackend.utils.SkuUtil;

@Component
public class VariantSizeMapper {

    @Autowired
    private SizeMapper sizeMapper;
    @Autowired
    private SkuUtil skuUtil;

    public List<VariantSize> toListVariantSizeWithProductVariant(List<SizeRequest> sizes, ProductVariant productVariant, String rootSku, String colorName) {
        return sizes.stream().map(sizeRequest ->
                        VariantSize.builder()
                                .sku(skuUtil.getProductSku(rootSku, colorName, sizeRequest.getSize()))
                                .isInStock(true)
                                .size(sizeMapper.toSize(sizeRequest))
                                .productVariant(productVariant).build())
                .collect(Collectors.toList());
    }

    public List<VariantSize> toListVariantSizeWithProductVariant(Dimensions size, Set<ProductVariant> productVariants, String rootSku) {
        return productVariants.stream().map(productVariant ->
                        VariantSize.builder()
                                .sku(skuUtil.getProductSku(rootSku, productVariant.getColor().getName(), size.getSize()))
                                .isInStock(true)
                                .size(size)
                                .productVariant(productVariant).build())
                .collect(Collectors.toList());
    }

    public VariantSizeResponse toVariantSizeResponse(VariantSize variantSize) {
        return VariantSizeResponse.builder()
                .variantSizeId(variantSize.getId())
                .sku(variantSize.getSku())
                .isInStock(variantSize.isInStock())
                .size(sizeMapper.toSizeResponse(variantSize.getSize())).build();
    }

    public List<VariantSizeResponse> toListVariantSizeResponse(Set<VariantSize> variantSizes) {
        return variantSizes.stream().map(this::toVariantSizeResponse).collect(Collectors.toList());
    }
}

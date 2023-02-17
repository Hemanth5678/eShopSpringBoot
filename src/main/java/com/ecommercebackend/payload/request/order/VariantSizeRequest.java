package com.ecommercebackend.payload.request.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VariantSizeRequest {
    private Long variantSizeId;
    private Integer quantity;
    private Long price;
}

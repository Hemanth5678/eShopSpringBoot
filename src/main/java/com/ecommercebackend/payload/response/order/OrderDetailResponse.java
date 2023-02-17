package com.ecommercebackend.payload.response.order;

import com.ecommercebackend.payload.response.category.CategoryResponse;
import com.ecommercebackend.payload.response.color.ColorResponse;
import com.ecommercebackend.payload.response.size.SizeResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDetailResponse {
    private String name;
    private String slug;
    private String imageUrl;
    private ColorResponse color;
    private SizeResponse size;
    private CategoryResponse category;
    private int quantity;
    private long price;
}

package com.ecommercebackend.payload.request.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryRequest {
    private Integer id;
    private String name;
}

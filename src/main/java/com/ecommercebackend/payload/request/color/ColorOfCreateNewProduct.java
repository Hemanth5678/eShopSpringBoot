package com.ecommercebackend.payload.request.color;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ColorOfCreateNewProduct {
    private Integer id;
    private String name;
}

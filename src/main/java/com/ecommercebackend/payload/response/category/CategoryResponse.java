package com.ecommercebackend.payload.response.category;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryResponse implements Serializable{
    private int id;
    private String name;
}

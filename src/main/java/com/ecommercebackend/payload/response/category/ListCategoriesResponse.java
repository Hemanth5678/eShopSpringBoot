package com.ecommercebackend.payload.response.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class ListCategoriesResponse implements Serializable{
    List<CategoryResponse> categories;
}

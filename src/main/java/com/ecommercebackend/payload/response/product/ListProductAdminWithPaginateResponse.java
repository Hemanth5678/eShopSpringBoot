package com.ecommercebackend.payload.response.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class ListProductAdminWithPaginateResponse implements Serializable{
    private List<ProductAdminResponse> products;
    private long totalRows;
    private int totalPages;
}

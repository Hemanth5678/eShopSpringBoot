package com.ecommercebackend.service;

import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.productdetail.ProductDetailResponse;

public interface ProductDetailService {
    public SuccessResponse<ProductDetailResponse> getProductDetailBySlug(String slug) throws NotFoundException;
}

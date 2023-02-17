package com.ecommercebackend.service;

import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.category.ListCategoriesResponse;

public interface CategoryService {
    public SuccessResponse<ListCategoriesResponse> getAllCategory();
}

package com.ecommercebackend.service;

import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.request.category.CreateNewCategoryRequest;
import com.ecommercebackend.payload.request.category.UpdateNameCategoryRequest;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.category.CategoryResponse;
import com.ecommercebackend.payload.response.category.ListCategoriesResponse;

public interface CategoryAdminService {

    public SuccessResponse<CategoryResponse> createNewCategory(CreateNewCategoryRequest createNewCategoryRequest) throws BadRequestException;

    public SuccessResponse<ListCategoriesResponse> getAllCategory();

    public SuccessResponse<CategoryResponse> updateNameCategory(UpdateNameCategoryRequest updateNameCategoryRequest) throws BadRequestException;

    public SuccessResponse<NoData> deleteCategory(int categoryId) throws NotFoundException, BadRequestException;
}

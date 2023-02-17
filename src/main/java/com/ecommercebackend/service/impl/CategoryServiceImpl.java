package com.ecommercebackend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommercebackend.dto.Category;
import com.ecommercebackend.mappers.CategoryMapper;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.category.CategoryResponse;
import com.ecommercebackend.payload.response.category.ListCategoriesResponse;
import com.ecommercebackend.repository.CategoryRepository;
import com.ecommercebackend.service.CategoryService;

import lombok.Builder;

@Service
@Builder
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public SuccessResponse<ListCategoriesResponse> getAllCategory() {
        List<Category> categories = categoryRepository.findAllByIsDeletedFalse();
        List<CategoryResponse> categoryResponseList = categories.stream().map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
        ListCategoriesResponse listCategoriesResponse =
                ListCategoriesResponse.builder().categories(categoryResponseList).build();
        return new SuccessResponse<>(listCategoriesResponse, "Get list category success.");
    }

}

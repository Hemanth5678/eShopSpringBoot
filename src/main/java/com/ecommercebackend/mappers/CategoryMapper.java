package com.ecommercebackend.mappers;

import org.springframework.stereotype.Component;

import com.ecommercebackend.dto.Category;
import com.ecommercebackend.payload.request.category.CategoryRequest;
import com.ecommercebackend.payload.request.category.CreateNewCategoryRequest;
import com.ecommercebackend.payload.response.category.CategoryResponse;

@Component
public class CategoryMapper {

    public Category toCategory(CreateNewCategoryRequest createNewCategoryRequest) {
        return Category.builder().name(createNewCategoryRequest.getName()).isDeleted(false).build();
    }

    public Category toCategory(CategoryRequest categoryRequest) {
        return Category.builder()
                .id(categoryRequest.getId())
                .name(categoryRequest.getName()).build();
    }

    public CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder().id(category.getId()).name(category.getName()).build();
    }
}

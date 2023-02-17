package com.ecommercebackend.service.impl;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.ecommercebackend.dto.Category;
import com.ecommercebackend.dto.Product;
import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.mappers.CategoryMapper;
import com.ecommercebackend.payload.request.category.CreateNewCategoryRequest;
import com.ecommercebackend.payload.request.category.UpdateNameCategoryRequest;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.category.CategoryResponse;
import com.ecommercebackend.payload.response.category.ListCategoriesResponse;
import com.ecommercebackend.repository.CategoryRepository;
import com.ecommercebackend.service.CategoryAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import lombok.Builder;

@Service
@Builder
public class CategoryAdminServiceImpl implements CategoryAdminService {

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

    @Override
    public SuccessResponse<CategoryResponse> createNewCategory(CreateNewCategoryRequest createNewCategoryRequest)
            throws BadRequestException {
        Optional<Category> categoryOptional = categoryRepository.findByNameAndIsDeletedFalse(createNewCategoryRequest.getName());
        if (categoryOptional.isPresent()) {
            throw new BadRequestException("This category already exists");
        }
        Category category = categoryMapper.toCategory(createNewCategoryRequest);
        Category result = categoryRepository.save(category);
        return new SuccessResponse<>(categoryMapper.toCategoryResponse(result), "Create new category successful.");
    }


    @Override
    public SuccessResponse<CategoryResponse> updateNameCategory(UpdateNameCategoryRequest updateNameCategoryRequest) throws BadRequestException {
        Optional<Category> categoryOptionalFindById = categoryRepository.findById(updateNameCategoryRequest.getId());
        if (categoryOptionalFindById.isEmpty()) {
            throw new BadRequestException("Id of category is not exist.");
        }
        if (categoryOptionalFindById.get().getName().equals(updateNameCategoryRequest.getNewName())) {
            throw new BadRequestException("New category name is the same with old category name. Nothing to update.");
        }
        Optional<Category> categoryOptionalFindByName = categoryRepository.findByNameAndIsDeletedFalse(updateNameCategoryRequest.getNewName());
        if (categoryOptionalFindByName.isPresent()) {
            throw new BadRequestException("This category name already exists.");
        }
        Category category = categoryOptionalFindById.get();
        category.setName(updateNameCategoryRequest.getNewName());
        categoryRepository.save(category);
        return new SuccessResponse<>(categoryMapper.toCategoryResponse(category), "Update name category success.");
    }

    @Override
    public SuccessResponse<NoData> deleteCategory(int categoryId) throws NotFoundException, BadRequestException {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new NotFoundException("Category with this id doesn't exist");
        }
        Set<Product> productSet = categoryOptional.get().getProducts();
        if (productSet.size() > 0) {
            throw new BadRequestException("This category had products. Cannot delete!");
        }
        Category category = categoryOptional.get();
        category.setDeleted(true);
        categoryRepository.save(category);
        return new SuccessResponse<>(NoData.builder().build(), "Delete category successfully.");
    }
}

package com.ecommercebackend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommercebackend.dto.Category;
import com.ecommercebackend.dto.Product;
import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.mappers.ProductMapper;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.product.ListProductResponse;
import com.ecommercebackend.payload.response.product.ListProductWithPaginateResponse;
import com.ecommercebackend.payload.response.product.ProductResponse;
import com.ecommercebackend.repository.CategoryRepository;
import com.ecommercebackend.repository.ProductRepository;
import com.ecommercebackend.service.ProductService;
import com.ecommercebackend.utils.PageableUtil;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PageableUtil pageableUtil;

    @Override
    @Cacheable(value = "product", key = "{#root.methodName, #offset, #limit, #sortBase, #sorType}")
    public SuccessResponse<ListProductWithPaginateResponse> getAllProductsWithPaginateAndSort(Integer offset, Integer limit, String sortBase, String sorType)
            throws BadRequestException {
        Pageable pageable = pageableUtil.getPageable(offset, limit, sortBase, sorType);
        Page<Product> productPage = productRepository.findAllByIsDeletedFalseAndIsVisibleTrue(pageable);
        List<ProductResponse> productResponseList =
                productPage.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
        ListProductWithPaginateResponse listProductWithPaginateResponse =
                ListProductWithPaginateResponse.builder()
                        .products(productResponseList)
                        .totalRows(productPage.getTotalElements())
                        .totalPages(productPage.getTotalPages()).build();
        return new SuccessResponse<>(listProductWithPaginateResponse, "Get list products success.");
    }

    @Override
    @Cacheable(value = "product", key = "{#categoryName, #offset, #limit, #sortBase, #sorType}")
    public SuccessResponse<ListProductWithPaginateResponse> getAllProductsByCategoryNameWithPaginateAndSort(
            String categoryName, Integer offset, Integer limit, String sortBase, String sorType) throws BadRequestException {
        Optional<Category> categoryOptional = categoryRepository.findByNameAndIsDeletedFalse(categoryName.trim().toUpperCase());
        if (categoryOptional.isEmpty()) {
            throw new BadRequestException("Category \"" + categoryName + "\" does not exist.");
        }
        Pageable pageable = pageableUtil.getPageable(offset, limit, sortBase, sorType);
        Page<Product> productPage = productRepository.findAllByIsDeletedFalseAndIsVisibleTrueAndCategoryId(categoryOptional.get().getId(), pageable);
        List<ProductResponse> productResponseList =
                productPage.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
        ListProductWithPaginateResponse listProductWithPaginateResponse =
                ListProductWithPaginateResponse.builder()
                        .products(productResponseList)
                        .totalRows(productPage.getTotalElements())
                        .totalPages(productPage.getTotalPages()).build();
        return new SuccessResponse<>(listProductWithPaginateResponse, "Get list products success.");
    }

    @Override
    @Cacheable(value = "product", key = "{#query, #offset, #limit}")
    public SuccessResponse<ListProductWithPaginateResponse> getAllProductsSearchByNameWithPaginate(String query, Integer offset, Integer limit) {
        Page<Product> productPage = productRepository.findAllByIsDeletedFalseAndIsVisibleTrueAndNameLikeIgnoreCase("%" + query + "%", PageRequest.of(offset, limit));
        List<ProductResponse> productResponseList =
                productPage.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
        ListProductWithPaginateResponse listProductWithPaginateResponse =
                ListProductWithPaginateResponse.builder()
                        .products(productResponseList)
                        .totalRows(productPage.getTotalElements())
                        .totalPages(productPage.getTotalPages()).build();
        return new SuccessResponse<>(listProductWithPaginateResponse, "Get list products success.");
    }

    @Override
    @Cacheable(value = "product", key = "{#categoryId, #numberElements}")
    public SuccessResponse<ListProductResponse> getNRelatedProductByCategoryId(int categoryId, int numberElements) throws NotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findByIdAndIsDeletedFalse(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new NotFoundException("Don't exist category with this id.");
        }
        List<Product> productList = productRepository.findAllByCategoryId(categoryId);
        List<ProductResponse> productResponseList = new ArrayList<>();
        if (productList.isEmpty()) {
            return new SuccessResponse<>(ListProductResponse.builder().products(productResponseList).build(), "Related products is empty");
        }
        if (productList.size() <= numberElements) {
            productResponseList = productList.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
            return new SuccessResponse<>(ListProductResponse.builder().products(productResponseList).build(), "Get related products success.");
        }
        Random random = new Random();
        for (int i = 0; i < numberElements; i++) {
            int randomIndex = random.nextInt(productList.size());
            productResponseList.add(productMapper.toProductResponse(productList.get(randomIndex)));
            productList.remove(randomIndex);
        }
        return new SuccessResponse<>(ListProductResponse.builder().products(productResponseList).build(), "Get related products success.");
    }

}

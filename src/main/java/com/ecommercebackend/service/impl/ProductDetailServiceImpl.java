package com.ecommercebackend.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommercebackend.dto.Product;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.mappers.ProductDetailMapper;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.productdetail.ProductDetailResponse;
import com.ecommercebackend.repository.ProductRepository;
import com.ecommercebackend.service.ProductDetailService;

import lombok.Builder;

@Service
@Builder
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Override
    public SuccessResponse<ProductDetailResponse> getProductDetailBySlug(String slug) throws NotFoundException {
        Optional<Product> productOptional = productRepository.findByIsDeletedFalseAndSlug(slug);
        if (productOptional.isEmpty()) {
            throw new NotFoundException("Product with this slug doesn't exist");
        }
        ProductDetailResponse productDetailResponse = productDetailMapper.toProductDetailResponse(productOptional.get());
        return new SuccessResponse<>(productDetailResponse, "Get product detail success.");
    }
}

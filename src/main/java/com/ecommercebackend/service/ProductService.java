package com.ecommercebackend.service;

import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.product.ListProductResponse;
import com.ecommercebackend.payload.response.product.ListProductWithPaginateResponse;

public interface ProductService {

    public SuccessResponse<ListProductWithPaginateResponse> getAllProductsWithPaginateAndSort(Integer offset, Integer limit, String sortBase, String sortType) throws BadRequestException;

    public SuccessResponse<ListProductWithPaginateResponse> getAllProductsByCategoryNameWithPaginateAndSort(String categoryName, Integer offset, Integer limit, String sortBase, String sortType) throws BadRequestException;

    public SuccessResponse<ListProductWithPaginateResponse> getAllProductsSearchByNameWithPaginate(String query, Integer offset, Integer limit);

    public SuccessResponse<ListProductResponse> getNRelatedProductByCategoryId(int categoryId, int numberElements) throws NotFoundException;
}

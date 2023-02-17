package com.ecommercebackend.service;

import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.request.product.CreateNewProductRequest;
import com.ecommercebackend.payload.request.product.UpdateProductRequest;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.product.ListProductAdminWithPaginateResponse;
import com.ecommercebackend.payload.response.productdetail.ProductDetailAdminResponse;

public interface ProductAdminService {
    public SuccessResponse<NoData> createNewProduct(CreateNewProductRequest createNewProductRequest) throws BadRequestException;

    public SuccessResponse<ListProductAdminWithPaginateResponse> getAllProductWithPaginate(int limit, int offset);

    public SuccessResponse<ProductDetailAdminResponse> getProductDetailByProductId(Integer productId) throws NotFoundException;

    public SuccessResponse<NoData> deleteProductByProductId(Integer productId) throws NotFoundException;

    public SuccessResponse<NoData> updateProductByProductId(Integer productId, UpdateProductRequest updateProductRequest)
            throws NotFoundException, BadRequestException;
}

package com.ecommercebackend.service;

import org.springframework.stereotype.Component;

import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.request.size.CreateNewSizeRequest;
import com.ecommercebackend.payload.request.size.UpdateSizeRequest;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.size.ListSizesResponse;
import com.ecommercebackend.payload.response.size.SizeResponse;

public interface SizeAdminService {
    public SuccessResponse<SizeResponse> createNewSize(CreateNewSizeRequest createNewSizeRequest) throws BadRequestException;

    public SuccessResponse<ListSizesResponse> getAllSizes();

    public SuccessResponse<SizeResponse> updateSize(UpdateSizeRequest updateSizeRequest) throws BadRequestException;

    public SuccessResponse<NoData> deleteSize(int sizeId) throws NotFoundException, BadRequestException;
}

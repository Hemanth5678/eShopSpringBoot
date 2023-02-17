package com.ecommercebackend.service;

import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.request.color.CreateNewColorRequest;
import com.ecommercebackend.payload.request.color.UpdateColorNameRequest;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.color.ColorResponse;
import com.ecommercebackend.payload.response.color.ListColorsResponse;

public interface ColorAdminService {
    public SuccessResponse<ColorResponse> createNewColor(CreateNewColorRequest createNewColorRequest) throws BadRequestException;

    public SuccessResponse<ListColorsResponse> getAllColors();

    public SuccessResponse<ColorResponse> updateColorName(UpdateColorNameRequest updateColorNameRequest) throws BadRequestException;

    public SuccessResponse<NoData> deleteColor(int colorId) throws BadRequestException, NotFoundException;
}

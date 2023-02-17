package com.ecommercebackend.service;

import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.request.rating.CreateNewRatingRequest;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.rating.ListRatingsWithPaginateResponse;

public interface RatingService {
    public SuccessResponse<NoData> createNewRatingProductOfUser(CreateNewRatingRequest createNewRatingRequest) throws NotFoundException, BadRequestException;

    public SuccessResponse<ListRatingsWithPaginateResponse> getAllRatingsOfProductWithPaginateAndSort(int productId, int offset, int limit, String sortType) throws BadRequestException, NotFoundException;
}

package com.ecommercebackend.service;

import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.ForbiddenException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.request.user.UserChangePasswordRequest;
import com.ecommercebackend.payload.request.user.UserUpdateRequest;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.user.UserResponse;

public interface UserService {

    public SuccessResponse<UserResponse> getCurrentUserInformation(long userId) throws NotFoundException, ForbiddenException;

    public SuccessResponse<UserResponse> updateCurrentUserInformation(long userId, UserUpdateRequest userUpdateRequest) throws BadRequestException, NotFoundException, ForbiddenException;

    public SuccessResponse<NoData> changePasswordOfCurrentUser(long userId, UserChangePasswordRequest userChangePasswordRequest) throws BadRequestException, NotFoundException, ForbiddenException;
}

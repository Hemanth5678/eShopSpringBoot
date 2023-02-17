package com.ecommercebackend.service;

import com.ecommercebackend.dto.User;
import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.request.user.UserLoginRequest;
import com.ecommercebackend.payload.request.user.UserRegisterRequest;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.user.UserLoginResponse;

public interface AuthService {
    public SuccessResponse<UserLoginResponse> loginHandler(UserLoginRequest userLoginRequest) throws BadRequestException;

    public User getUserLoggedIn() throws NotFoundException;

    public SuccessResponse<NoData> userRegister(UserRegisterRequest userRegisterRequest) throws BadRequestException;
}

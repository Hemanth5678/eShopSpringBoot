package com.ecommercebackend.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommercebackend.dto.User;
import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.ForbiddenException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.mappers.UserMapper;
import com.ecommercebackend.payload.request.user.UserChangePasswordRequest;
import com.ecommercebackend.payload.request.user.UserUpdateRequest;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.user.UserResponse;
import com.ecommercebackend.repository.UserRepository;
import com.ecommercebackend.service.AuthService;
import com.ecommercebackend.service.UserService;

import lombok.Builder;


@Service
@Builder
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthService authService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SuccessResponse<UserResponse> getCurrentUserInformation(long userId) throws NotFoundException, ForbiddenException {
        User user = authService.getUserLoggedIn();
        if (user.getId() != userId) {
            throw new ForbiddenException("You do not have permission to perform this action.");
        }
        return new SuccessResponse<>(userMapper.toUserResponse(user),
                "Get current user's information successfully.");
    }

    @Override
    public SuccessResponse<UserResponse> updateCurrentUserInformation(long userId, UserUpdateRequest userUpdateRequest)
            throws BadRequestException, NotFoundException, ForbiddenException {
        User user = authService.getUserLoggedIn();
        if (user.getId() != userId) {
            throw new ForbiddenException("You do not have permission to perform this action.");
        }
        Optional<User> userOptionalWithPhone = userRepository.findByPhone(userUpdateRequest.getPhone());
        if (userOptionalWithPhone.isPresent() && userOptionalWithPhone.get().getId() != user.getId()) {
            throw new BadRequestException("This phone number already exists.");
        }
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setPhone(userUpdateRequest.getPhone());
        user.setAddress(userUpdateRequest.getAddress());
        user.setUpdatedAt(new Date());
        userRepository.save(user);
        return new SuccessResponse<>(userMapper.toUserResponse(user), "Updated information successfully.");
    }

    @Override
    public SuccessResponse<NoData> changePasswordOfCurrentUser(long userId, UserChangePasswordRequest userChangePasswordRequest)
            throws BadRequestException, NotFoundException, ForbiddenException {
        if (!userChangePasswordRequest.getNewPassword().equals(userChangePasswordRequest.getConfirmPassword())) {
            throw new BadRequestException("Confirm password not match new password.");
        }
        User user = authService.getUserLoggedIn();
        if (user.getId() != userId) {
            throw new ForbiddenException("You do not have permission to perform this action.");
        }
        if (!passwordEncoder.matches(userChangePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Old password is invalid.");
        }
        if (passwordEncoder.matches(userChangePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new BadRequestException("New password is the same with old password. Nothing change.");
        }
        user.setPassword(passwordEncoder.encode(userChangePasswordRequest.getNewPassword()));
        user.setUpdatedAt(new Date());
        userRepository.save(user);
        return new SuccessResponse<>(NoData.builder().build(), "Changed password successfully.");
    }

}

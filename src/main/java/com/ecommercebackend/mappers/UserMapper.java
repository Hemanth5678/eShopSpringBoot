package com.ecommercebackend.mappers;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ecommercebackend.dto.User;
import com.ecommercebackend.enums.EAccountStatus;
import com.ecommercebackend.enums.ERoles;
import com.ecommercebackend.payload.request.user.CreateNewAdminUserRequest;
import com.ecommercebackend.payload.request.user.UserRegisterRequest;
import com.ecommercebackend.payload.response.user.UserLoginResponse;
import com.ecommercebackend.payload.response.user.UserRatingResponse;
import com.ecommercebackend.payload.response.user.UserResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toUser(UserRegisterRequest userRegisterRequest) {
        return User.builder()
                .email(userRegisterRequest.getEmail())
                .phone(userRegisterRequest.getPhone())
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .role(ERoles.USER)
                .status(EAccountStatus.ACTIVE)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    public User toUser(CreateNewAdminUserRequest createNewAdminUserRequest) {
        return User.builder()
                .email(createNewAdminUserRequest.getEmail())
                .phone(createNewAdminUserRequest.getPhone())
                .firstName(createNewAdminUserRequest.getFirstName())
                .lastName(createNewAdminUserRequest.getLastName())
                .password(passwordEncoder.encode(createNewAdminUserRequest.getPassword()))
                .role(ERoles.ADMIN)
                .status(EAccountStatus.ACTIVE)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public UserLoginResponse toUserLoginResponse(User user) {
        return UserLoginResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt()).build();
    }

    public UserRatingResponse toUserRatingResponse(User user) {
        return UserRatingResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName()).build();
    }
}

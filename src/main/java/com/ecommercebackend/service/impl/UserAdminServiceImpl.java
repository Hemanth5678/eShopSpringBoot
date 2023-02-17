package com.ecommercebackend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommercebackend.dto.User;
import com.ecommercebackend.enums.EAccountStatus;
import com.ecommercebackend.enums.ERoles;
import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.mappers.UserMapper;
import com.ecommercebackend.payload.request.user.CreateNewAdminUserRequest;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.user.ListUsersWithPaginateResponse;
import com.ecommercebackend.payload.response.user.UserResponse;
import com.ecommercebackend.repository.UserRepository;
import com.ecommercebackend.service.UserAdminService;
import com.ecommercebackend.utils.PageableUtil;

import lombok.Builder;

@Service
@Builder
public class UserAdminServiceImpl implements UserAdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PageableUtil pageableUtil;

    @Override
    public SuccessResponse<ListUsersWithPaginateResponse> getListUsersByRoleAndStatusWithPaginate(
            ERoles role, EAccountStatus status, Integer limit, Integer offset) {
        Pageable pageable = pageableUtil.getPageable(offset, limit);
        Page<User> userPage = userRepository.findAllByRoleAndStatus(role, status, pageable);
        List<UserResponse> userResponseList = userPage.getContent().stream().map(userMapper::toUserResponse)
                .collect(Collectors.toList());
        ListUsersWithPaginateResponse listUsersWithPaginateResponse =
                ListUsersWithPaginateResponse.builder()
                        .totalRows(userPage.getTotalElements())
                        .totalPages(userPage.getTotalPages())
                        .listUsers(userResponseList).build();
        return new SuccessResponse<>(listUsersWithPaginateResponse, "Get users list success.");
    }

    @Override
    public SuccessResponse<NoData> blockUserByUserId(long userId) throws NotFoundException, BadRequestException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User with this id doesn't exist.");
        }
        User user = userOptional.get();
        if (user.getStatus().equals(EAccountStatus.BLOCK)) {
            throw new BadRequestException("You cannot block as this user is already blocked.");
        }
        user.setStatus(EAccountStatus.BLOCK);
        userRepository.save(user);
        return new SuccessResponse<>(NoData.builder().build(), "Block user success!");
    }

    @Override
    public SuccessResponse<NoData> unBlockUserByUserId(long userId) throws NotFoundException, BadRequestException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User with this id doesn't exist.");
        }
        User user = userOptional.get();
        if (user.getStatus().equals(EAccountStatus.ACTIVE)) {
            throw new BadRequestException("You cannot un-block user has been activated.");
        }
        user.setStatus(EAccountStatus.ACTIVE);
        userRepository.save(user);
        return new SuccessResponse<>(NoData.builder().build(), "Un block user success!");
    }

    @Override
    public SuccessResponse<NoData> createNewAdminUser(CreateNewAdminUserRequest createNewAdminUserRequest) throws BadRequestException {
        if (!createNewAdminUserRequest.getPassword().equals(createNewAdminUserRequest.getConfirmPassword())) {
            throw new BadRequestException("Confirm password not match new password.");
        }
        Optional<User> userOptional = userRepository.findByEmail(createNewAdminUserRequest.getEmail());
        if (userOptional.isPresent()) {
            throw new BadRequestException("This email already exists.");
        }
        userOptional = userRepository.findByPhone(createNewAdminUserRequest.getPhone());
        if (userOptional.isPresent()) {
            throw new BadRequestException("This phone number already exists.");
        }
        User user = userMapper.toUser(createNewAdminUserRequest);
        userRepository.save(user);
        return new SuccessResponse<>(NoData.builder().build(), "Created new admin user successfully.");
    }
}

package com.ecommercebackend.service;

import com.ecommercebackend.enums.EAccountStatus;
import com.ecommercebackend.enums.ERoles;
import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.request.user.CreateNewAdminUserRequest;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.user.ListUsersWithPaginateResponse;

public interface UserAdminService {

    public SuccessResponse<ListUsersWithPaginateResponse> getListUsersByRoleAndStatusWithPaginate(ERoles role, EAccountStatus status, Integer limit, Integer offset);

    public SuccessResponse<NoData> blockUserByUserId(long userId) throws NotFoundException, BadRequestException;

    public SuccessResponse<NoData> unBlockUserByUserId(long userId) throws NotFoundException, BadRequestException;

    public SuccessResponse<NoData> createNewAdminUser(CreateNewAdminUserRequest createNewAdminUserRequest) throws BadRequestException;
}

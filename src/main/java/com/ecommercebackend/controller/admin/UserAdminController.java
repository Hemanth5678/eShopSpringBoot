package com.ecommercebackend.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercebackend.enums.EAccountStatus;
import com.ecommercebackend.enums.ERoles;
import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.request.user.CreateNewAdminUserRequest;
import com.ecommercebackend.payload.response.ExceptionResponse;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.user.ListUsersWithPaginateResponse;
import com.ecommercebackend.service.UserAdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/v1/admin/user")
public class UserAdminController {

    @Autowired
    private UserAdminService userAdminService;

    @Operation(summary = "Get list users by role and status with paginate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list users success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))})
    })
    @GetMapping
    public SuccessResponse<ListUsersWithPaginateResponse> getListUsersByRoleAndStatusWithPaginate(
            @RequestParam(name = "role") ERoles role,
            @RequestParam(name = "status") EAccountStatus status,
            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset
    ) {
        return userAdminService.getListUsersByRoleAndStatusWithPaginate(role, status, limit, offset);
    }

    @Operation(summary = "Block user by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Block user success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Cannot block user has been blocked",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Don't exist user with this id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @PutMapping("/{userId}/block")
    public SuccessResponse<NoData> blockUserByUserId(@PathVariable Long userId) throws NotFoundException, BadRequestException {
        return userAdminService.blockUserByUserId(userId);
    }

    @Operation(summary = "Un block user by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Un block user success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Cannot un block user has been activated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Don't exist user with this id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @PutMapping("/{userId}/un-block")
    public SuccessResponse<NoData> unBlockUserByUserId(@PathVariable Long userId) throws NotFoundException, BadRequestException {
        return userAdminService.unBlockUserByUserId(userId);
    }

    @Operation(summary = "Create new admin user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create new admin user successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Confirm password not match new password | Email already existed | Phone number already existed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @PostMapping
    public SuccessResponse<NoData> createNewAdminUser(@Valid @RequestBody CreateNewAdminUserRequest createNewAdminUserRequest)
            throws BadRequestException {
        return userAdminService.createNewAdminUser(createNewAdminUserRequest);
    }
}

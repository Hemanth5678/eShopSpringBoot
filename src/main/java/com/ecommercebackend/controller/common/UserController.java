package com.ecommercebackend.controller.common;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.ForbiddenException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.request.user.UserChangePasswordRequest;
import com.ecommercebackend.payload.request.user.UserUpdateRequest;
import com.ecommercebackend.payload.response.ExceptionResponse;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.user.UserResponse;
import com.ecommercebackend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get current user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get current user information success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Don't have permission",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @GetMapping("/{userId}")
    public SuccessResponse<UserResponse> getCurrentUserInformation(@PathVariable Long userId)
            throws NotFoundException, ForbiddenException {
        return userService.getCurrentUserInformation(userId);
    }

    @Operation(summary = "Update user's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update user's information success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Duplicate phone number",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Don't have permission",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @PutMapping("/{userId}")
    public SuccessResponse<UserResponse> updateCurrentUserInformation(@PathVariable Long userId, @Valid @RequestBody UserUpdateRequest userUpdateRequest)
            throws BadRequestException, NotFoundException, ForbiddenException {
        return userService.updateCurrentUserInformation(userId, userUpdateRequest);
    }

    @Operation(summary = "Change user's password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Change password success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Change password fail",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Don't have permission",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @PutMapping("/{userId}/change-password")
    public SuccessResponse<NoData> changePasswordOfCurrentUser(@PathVariable Long userId, @Valid @RequestBody UserChangePasswordRequest userChangePasswordRequest)
            throws BadRequestException, NotFoundException, ForbiddenException {
        return userService.changePasswordOfCurrentUser(userId, userChangePasswordRequest);
    }

}

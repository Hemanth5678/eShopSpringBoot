package com.ecommercebackend.controller.common;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.payload.request.user.UserLoginRequest;
import com.ecommercebackend.payload.request.user.UserRegisterRequest;
import com.ecommercebackend.payload.response.ExceptionResponse;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.user.UserLoginResponse;
import com.ecommercebackend.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Email or password is incorrect. | Account has not been activated. | Account has been blocked.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @PostMapping("/login")
    public SuccessResponse<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest)
            throws BadRequestException {
        return authService.loginHandler(userLoginRequest);
    }

    @Operation(summary = "Register new user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "400", description = "This email already existed. | This phone number already existed.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @PostMapping("/register")
    public SuccessResponse<NoData> userRegister(@Valid @RequestBody UserRegisterRequest userRegisterRequest)
            throws BadRequestException {
        return authService.userRegister(userRegisterRequest);
    }
}

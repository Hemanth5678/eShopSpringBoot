package com.ecommercebackend.payload.request.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ecommercebackend.constants.Constants;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@EqualsAndHashCode
public class UserLoginRequest {
    @NotBlank(message = "Email is required")
    @Email(regexp = Constants.EMAIL_REGEX, message = "Invalid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = Constants.MIN_LENGTH_PASSWORD, max = Constants.MAX_LENGTH_PASSWORD, message = "The password must contain at least 6 characters and be up to 24 characters")
    private String password;
}

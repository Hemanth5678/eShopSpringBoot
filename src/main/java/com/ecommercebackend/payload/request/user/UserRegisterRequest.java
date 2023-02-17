package com.ecommercebackend.payload.request.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
public class UserRegisterRequest {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Email(regexp = Constants.EMAIL_REGEX, message = "Invalid email")
    private String email;
    @Pattern(regexp = Constants.PHONE_REGEX, message = "Invalid phone number")
    private String phone;
    @Size(min = Constants.MIN_LENGTH_PASSWORD, max = Constants.MAX_LENGTH_PASSWORD, message = "The password must contain at least 6 characters and be up to 24 characters")
    private String password;
}

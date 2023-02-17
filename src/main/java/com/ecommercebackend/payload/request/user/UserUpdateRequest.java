package com.ecommercebackend.payload.request.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.ecommercebackend.constants.Constants;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class UserUpdateRequest {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = Constants.PHONE_REGEX, message = "Invalid phone number")
    private String phone;
    @NotBlank(message = "Address is required")
    private String address;
}

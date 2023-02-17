package com.ecommercebackend.payload.response.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRatingResponse {
    private long id;
    private String firstName;
    private String lastName;
}

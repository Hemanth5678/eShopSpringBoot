package com.ecommercebackend.payload.response.rating;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import com.ecommercebackend.payload.response.user.UserRatingResponse;

@Getter
@Setter
@Builder
public class RatingResponse {
    private UserRatingResponse user;
    private int stars;
    private String title;
    private String comment;
    private Date createdDate;
    private boolean isShow;
}

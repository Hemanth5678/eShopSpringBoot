package com.ecommercebackend.mappers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommercebackend.dto.Product;
import com.ecommercebackend.dto.Rating;
import com.ecommercebackend.dto.RatingKey;
import com.ecommercebackend.dto.User;
import com.ecommercebackend.payload.request.rating.CreateNewRatingRequest;
import com.ecommercebackend.payload.response.rating.RatingResponse;

@Component
public class RatingMapper {

    @Autowired
    private UserMapper userMapper;

    public Rating toRating(CreateNewRatingRequest createNewRatingRequest, User user, Product product) {
        return Rating.builder()
                .ratingId(RatingKey.builder().userId(user.getId()).productId(product.getId()).build())
                .user(user)
                .product(product)
                .stars(createNewRatingRequest.getStar())
                .title(createNewRatingRequest.getTitle())
                .comment(createNewRatingRequest.getContent())
                .isShow(true)
                .createdDate(new Date()).build();
    }

    public RatingResponse toRatingResponse(Rating rating) {
        return RatingResponse.builder()
                .user(userMapper.toUserRatingResponse(rating.getUser()))
                .stars(rating.getStars())
                .title(rating.getTitle())
                .comment(rating.getComment())
                .createdDate(rating.getCreatedDate())
                .isShow(rating.isShow()).build();
    }
}

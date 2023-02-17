package com.ecommercebackend.controller.common;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.request.rating.CreateNewRatingRequest;
import com.ecommercebackend.payload.response.ExceptionResponse;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.rating.ListRatingsWithPaginateResponse;
import com.ecommercebackend.service.RatingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Operation(summary = "Create new rating by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create new rating successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "400", description = "User already rated this product.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Don't exist product with this id.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @PostMapping
    public SuccessResponse<NoData> createNewRatingProductOfUser(
            @Valid @RequestBody CreateNewRatingRequest createNewRatingRequest) throws NotFoundException, BadRequestException {
        return ratingService.createNewRatingProductOfUser(createNewRatingRequest);
    }

    @Operation(summary = "Get all ratings of product with paginate and sort")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list ratings successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Don't exist product with this id.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @GetMapping
    public SuccessResponse<ListRatingsWithPaginateResponse> getAllRatingsOfProductWithPaginateAndSort(
            @RequestParam(name = "product-id") Integer productId,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
            @RequestParam(name = "sort-type", defaultValue = "DESC") String sortType
    ) throws BadRequestException, NotFoundException {
        return ratingService.getAllRatingsOfProductWithPaginateAndSort(productId, offset, limit, sortType);
    }
}

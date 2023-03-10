package com.ecommercebackend.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.response.ExceptionResponse;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.productdetail.ProductDetailResponse;
import com.ecommercebackend.service.ProductDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/product-detail")
public class ProductDetailController {

    @Autowired
    private ProductDetailService productDetailService;

    @Operation(summary = "Get product detail by slug")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get product detail successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Don't exist product with this slug.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @GetMapping
    public SuccessResponse<ProductDetailResponse> getProductDetailBySlug(@RequestParam(name = "slug") String slug)
            throws NotFoundException {
        return productDetailService.getProductDetailBySlug(slug);
    }

}

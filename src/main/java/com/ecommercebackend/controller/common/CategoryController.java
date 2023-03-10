package com.ecommercebackend.controller.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.category.ListCategoriesResponse;
import com.ecommercebackend.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list categories successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))})
    })
    @GetMapping
    public SuccessResponse<ListCategoriesResponse> getAllCategory() {
        return categoryService.getAllCategory();
    }
}

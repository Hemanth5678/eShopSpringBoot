package com.ecommercebackend.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.request.category.CreateNewCategoryRequest;
import com.ecommercebackend.payload.request.category.UpdateNameCategoryRequest;
import com.ecommercebackend.payload.response.ExceptionResponse;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.category.CategoryResponse;
import com.ecommercebackend.payload.response.category.ListCategoriesResponse;
import com.ecommercebackend.service.CategoryAdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/admin/category")
public class CategoryAdminController {

    @Autowired
    private CategoryAdminService categoryAdminService;

    @Operation(summary = "Create new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create new category successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Category already existed.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @PostMapping
    public SuccessResponse<CategoryResponse> createNewCategory(@Valid @RequestBody CreateNewCategoryRequest createNewCategoryRequest)
            throws BadRequestException {
        return categoryAdminService.createNewCategory(createNewCategoryRequest);
    }

    @Operation(summary = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list categories successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))})
    })
    @GetMapping
    public SuccessResponse<ListCategoriesResponse> getAllCategory() {
        return categoryAdminService.getAllCategory();
    }

    @Operation(summary = "Update category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update category successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Id of category is not existed. | New category is the same with old category. Nothing update. | This category already existed.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @PutMapping
    public SuccessResponse<CategoryResponse> updateNameCategory(@Valid @RequestBody UpdateNameCategoryRequest updateNameCategoryRequest)
            throws BadRequestException {
        return categoryAdminService.updateNameCategory(updateNameCategoryRequest);
    }

    @Operation(summary = "Delete category by category id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete category successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "400", description = "This category had products. Cannot delete.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Don't exist category with this id.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @DeleteMapping("/{categoryId}")
    public SuccessResponse<NoData> deleteCategory(@PathVariable Integer categoryId) throws NotFoundException, BadRequestException {
        return categoryAdminService.deleteCategory(categoryId);
    }

}

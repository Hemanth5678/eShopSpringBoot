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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.request.product.CreateNewProductRequest;
import com.ecommercebackend.payload.request.product.UpdateProductRequest;
import com.ecommercebackend.payload.response.ExceptionResponse;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.product.ListProductAdminWithPaginateResponse;
import com.ecommercebackend.payload.response.productdetail.ProductDetailAdminResponse;
import com.ecommercebackend.service.ProductAdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/admin/product")
public class ProductAdminController {

    @Autowired
    private ProductAdminService productAdminService;

    @Operation(summary = "Create new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create new product successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Product name already existed. | Sku already existed.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @PostMapping
    public SuccessResponse<NoData> createNewProduct(@Valid @RequestBody CreateNewProductRequest createNewProductRequest)
            throws BadRequestException {
        return productAdminService.createNewProduct(createNewProductRequest);
    }

    @Operation(summary = "Get all product with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list products successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))})
    })
    @GetMapping
    public SuccessResponse<ListProductAdminWithPaginateResponse> getAllProductWithPaginate(
            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset
    ) {
        return productAdminService.getAllProductWithPaginate(limit, offset);
    }

    @Operation(summary = "Get product detail by product id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get product detail successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Don't exist product with this id.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @GetMapping("/{productId}")
    public SuccessResponse<ProductDetailAdminResponse> getProductDetailByProductId(@PathVariable Integer productId)
            throws NotFoundException {
        return productAdminService.getProductDetailByProductId(productId);
    }

    @Operation(summary = "Delete product detail by product id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete product successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Product with this id does not exist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @DeleteMapping("/{productId}")
    public SuccessResponse<NoData> deleteProductByProductId(@PathVariable Integer productId) throws NotFoundException {
        return productAdminService.deleteProductByProductId(productId);
    }

    @Operation(summary = "Update product detail by product id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update product successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Discount price must be lower than price. | Start date discount is required when discount price greater than 0. | Start date discount must after today. | End date discount is required when discount price greater than 0. | Start date discount must before end date discount. | Don't exist product with this id. | Product name already exist. | Don't exist category with this id.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @PutMapping("/{productId}")
    public SuccessResponse<NoData> updateProductByProductId(
            @PathVariable Integer productId,
            @Valid @RequestBody UpdateProductRequest updateProductRequest) throws NotFoundException, BadRequestException {
        return productAdminService.updateProductByProductId(productId, updateProductRequest);
    }
}

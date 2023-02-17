package com.ecommercebackend.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.payload.request.color.UpdateColorNameRequest;
import com.ecommercebackend.payload.response.ExceptionResponse;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.color.ColorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/admin/order")
public class OrderAdminController {

	
//    @Operation(summary = "Update order")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Update order successfully.",
//                    content = {@Content(mediaType = "application/json",
//                            schema = @Schema(implementation = SuccessResponse.class))}),
//            @ApiResponse(responseCode = "404", description = "Order doesn't exist",
//                    content = {@Content(mediaType = "application/json",
//                            schema = @Schema(implementation = ExceptionResponse.class))})
//    })
//    @PutMapping
//    private SuccessResponse<ColorResponse> updateColorName(@Valid @RequestBody UpdateColorNameRequest updateColorNameRequest)
//            throws BadRequestException {
//        return colorAdminService.updateColorName(updateColorNameRequest);
//    }
}

package com.ecommercebackend.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
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
import com.ecommercebackend.payload.request.color.CreateNewColorRequest;
import com.ecommercebackend.payload.request.color.UpdateColorNameRequest;
import com.ecommercebackend.payload.response.ExceptionResponse;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.color.ColorResponse;
import com.ecommercebackend.payload.response.color.ListColorsResponse;
import com.ecommercebackend.service.ColorAdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/admin/color")
public class ColorAdminController {

    @Autowired
    private ColorAdminService colorAdminService;

    @Operation(summary = "Create new color")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create new color successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Color already existed.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @PostMapping
    public SuccessResponse<ColorResponse> createNewColor(@Valid @RequestBody CreateNewColorRequest createNewColorRequest)
            throws BadRequestException {
        return colorAdminService.createNewColor(createNewColorRequest);
    }

    @Operation(summary = "Get all colors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list colors successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))})
    })
    @GetMapping
    private SuccessResponse<ListColorsResponse> getAllColors() {
        return colorAdminService.getAllColors();
    }

    @Operation(summary = "Update color")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update color successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Id of color is not existed. | New color is the same with old color. Nothing update. | This color already existed.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @PutMapping
    private SuccessResponse<ColorResponse> updateColorName(@Valid @RequestBody UpdateColorNameRequest updateColorNameRequest)
            throws BadRequestException {
        return colorAdminService.updateColorName(updateColorNameRequest);
    }

    @Operation(summary = "Delete color by color id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete color successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "400", description = "This color had products. Cannot delete.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Don't exist color with this id.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @DeleteMapping("/{colorId}")
    public SuccessResponse<NoData> deleteColor(@PathVariable Integer colorId) throws NotFoundException, BadRequestException {
        return colorAdminService.deleteColor(colorId);
    }
}

package com.ecommercebackend.payload.request.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class DeleteCategoryRequest {
    @NotBlank(message = "Id of category need to delete is required")
    private int id;
}

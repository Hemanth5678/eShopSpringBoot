package com.ecommercebackend.payload.request.size;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class UpdateSizeRequest {
    @Min(value = 0, message = "Id must be equal or greater than 0")
    private Integer id;
    @NotEmpty(message = "Size is required")
    private String newSize;
}

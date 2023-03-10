package com.ecommercebackend.payload.request.size;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreateNewSizeRequest {
    @NotEmpty(message = "Size is required")
    private String size;
}

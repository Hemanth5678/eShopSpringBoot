package com.ecommercebackend.payload.request.color;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreateNewColorRequest {
    @NotEmpty(message = "Color name is required")
    private String name;
}

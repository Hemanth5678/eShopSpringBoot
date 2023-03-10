package com.ecommercebackend.payload.request.size;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SizeRequest {
    private Integer id;
    private String size;
}

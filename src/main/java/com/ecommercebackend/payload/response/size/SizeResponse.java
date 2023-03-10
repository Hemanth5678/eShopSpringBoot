package com.ecommercebackend.payload.response.size;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SizeResponse {
    private int id;
    private String size;
}

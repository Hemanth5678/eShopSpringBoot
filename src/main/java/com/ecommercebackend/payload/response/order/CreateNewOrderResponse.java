package com.ecommercebackend.payload.response.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateNewOrderResponse {
    private String code;
}

package com.ecommercebackend.payload.response.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ListOrdersResponse {
    private List<OrderResponse> orders;
}

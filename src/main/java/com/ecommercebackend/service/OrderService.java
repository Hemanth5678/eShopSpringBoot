package com.ecommercebackend.service;

import com.ecommercebackend.exception.ForbiddenException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.payload.request.order.CreateNewOrderRequest;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.order.CreateNewOrderResponse;
import com.ecommercebackend.payload.response.order.ListOrdersResponse;
import com.ecommercebackend.payload.response.order.OrderWithDetailResponse;

public interface OrderService {
    public SuccessResponse<CreateNewOrderResponse> createNewOrder(CreateNewOrderRequest createNewOrderRequest) throws NotFoundException;

    public SuccessResponse<OrderWithDetailResponse> getOrderByCode(String code) throws NotFoundException, ForbiddenException;

    public SuccessResponse<ListOrdersResponse> getAllOrdersOfUser(long userId) throws NotFoundException, ForbiddenException;

    public SuccessResponse<OrderWithDetailResponse> getOrderWithDetail(Long orderId) throws NotFoundException, ForbiddenException;
}

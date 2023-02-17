package com.ecommercebackend.mappers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommercebackend.dto.Order;
import com.ecommercebackend.enums.EDeliveryStatus;
import com.ecommercebackend.enums.EOrderStatus;
import com.ecommercebackend.enums.EPaymentStatus;
import com.ecommercebackend.payload.request.order.CreateNewOrderRequest;
import com.ecommercebackend.payload.response.order.OrderResponse;
import com.ecommercebackend.payload.response.order.OrderWithDetailResponse;

@Component
public class OrderMapper {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    public Order toOrder(CreateNewOrderRequest createNewOrderRequest) {
        return Order.builder()
                .code(createNewOrderRequest.getCode())
                .fullName(createNewOrderRequest.getFullName())
                .phone(createNewOrderRequest.getPhone())
                .address(createNewOrderRequest.getAddress())
                .note(createNewOrderRequest.getNote())
                .totalPrice(createNewOrderRequest.getTotalPrice())
                .deliveryFee(createNewOrderRequest.getDeliveryFee())
                .status(EOrderStatus.WAITING)
                .orderDate(new Date())
                .deliveryMethod(createNewOrderRequest.getDeliveryMethod())
                .deliveryStatus(EDeliveryStatus.UNDELIVERED)
                .paymentMethod(createNewOrderRequest.getPaymentMethod())
                .paymentStatus(EPaymentStatus.UNPAID).build();
        		
    }
    public Order toSuccessfulOrder(CreateNewOrderRequest createNewOrderRequest) {
        return Order.builder()
                .code(createNewOrderRequest.getCode())
                .fullName(createNewOrderRequest.getFullName())
                .phone(createNewOrderRequest.getPhone())
                .address(createNewOrderRequest.getAddress())
                .note(createNewOrderRequest.getNote())
                .totalPrice(createNewOrderRequest.getTotalPrice())
                .deliveryFee(createNewOrderRequest.getDeliveryFee())
                .status(EOrderStatus.WAITING)
                .orderDate(new Date())
                .deliveryMethod(createNewOrderRequest.getDeliveryMethod())
                .deliveryStatus(EDeliveryStatus.UNDELIVERED)
                .paymentMethod(createNewOrderRequest.getPaymentMethod())
                .paymentStatus(EPaymentStatus.PAID).build();
        		
    }

    public OrderWithDetailResponse toOrderWithDetailResponse(Order order) {
        return OrderWithDetailResponse.builder()
                .id(order.getId())
                .code(order.getCode())
                .fullName(order.getFullName())
                .phone(order.getPhone())
                .address(order.getAddress())
                .note(order.getNote())
                .totalPrice(order.getTotalPrice())
                .deliveryFee(order.getDeliveryFee())
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .finishDate(order.getFinishDate())
                .deliveryMethod(order.getDeliveryMethod())
                .paymentMethod(order.getPaymentMethod())
                .deliveryStatus(order.getDeliveryStatus())
                .paymentStatus(order.getPaymentStatus())
                .orderDetails(orderDetailMapper.toListOrderDetailResponse(order.getOrderDetails())).build();
    }

    public OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .code(order.getCode())
                .fullName(order.getFullName())
                .phone(order.getPhone())
                .address(order.getAddress())
                .note(order.getNote())
                .totalPrice(order.getTotalPrice())
                .deliveryFee(order.getDeliveryFee())
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .finishDate(order.getFinishDate())
                .deliveryMethod(order.getDeliveryMethod())
                .paymentMethod(order.getPaymentMethod())
                .deliveryStatus(order.getDeliveryStatus())
                .paymentStatus(order.getPaymentStatus()).build();
    }
}

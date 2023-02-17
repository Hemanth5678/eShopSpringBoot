package com.ecommercebackend.payload.response.order;

import java.util.Date;
import java.util.List;

import com.ecommercebackend.enums.EDeliveryMethod;
import com.ecommercebackend.enums.EDeliveryStatus;
import com.ecommercebackend.enums.EOrderStatus;
import com.ecommercebackend.enums.EPaymentMethod;
import com.ecommercebackend.enums.EPaymentStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderWithDetailResponse {
    private long id;
    private String code;
    private String fullName;
    private String phone;
    private String address;
    private String note;
    private long totalPrice;
    private long deliveryFee;
    private EOrderStatus status;
    private Date orderDate;
    private Date finishDate;
    private EDeliveryMethod deliveryMethod;
    private EPaymentMethod paymentMethod;
    private EDeliveryStatus deliveryStatus;
    private EPaymentStatus paymentStatus;
    private List<OrderDetailResponse> orderDetails;
}

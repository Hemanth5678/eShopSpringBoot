package com.ecommercebackend.payload.request.order;

import java.util.List;

import com.ecommercebackend.enums.EDeliveryMethod;
import com.ecommercebackend.enums.EPaymentMethod;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateNewOrderRequest {
    List<VariantSizeRequest> products;
    private String code;
    private String fullName;
    private String phone;
    private String address;
    private String note;
    private long totalPrice;
    private long deliveryFee;
    private EDeliveryMethod deliveryMethod;
    private EPaymentMethod paymentMethod;
    private String paymentId;
}

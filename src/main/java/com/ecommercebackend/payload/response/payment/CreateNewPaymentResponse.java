package com.ecommercebackend.payload.response.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ecommercebackend.dto.Order;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateNewPaymentResponse {

    private Long id;
    private Order orderId;
    private String email;
    private BigDecimal amount;
    private String razorpayPaymentId;
    private String razorpayOrderId;
    private String razorpaySignature;
    private LocalDateTime paymentDateTime;
    private String status;
}

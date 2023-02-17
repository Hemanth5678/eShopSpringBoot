package com.ecommercebackend.mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.ecommercebackend.dto.Order;
import com.ecommercebackend.dto.Payment;
import com.ecommercebackend.dto.User;
import com.ecommercebackend.enums.EDeliveryStatus;
import com.ecommercebackend.enums.EOrderStatus;
import com.ecommercebackend.enums.EPaymentStatus;
import com.ecommercebackend.payload.request.order.CreateNewOrderRequest;
import com.ecommercebackend.payload.request.payment.CreateNewPayment;
import com.ecommercebackend.payload.response.payment.CreateNewPaymentResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

@Component
public class PaymentMapper {

    public Payment toPayment(CreateNewPayment createNewOrderRequest) {
        return Payment.builder()
                .amount(createNewOrderRequest.getAmount())
                .razorpayPaymentId(createNewOrderRequest.getRazorpayPaymentId())
                .razorpayOrderId(createNewOrderRequest.getRazorpayOrderId())
                .razorpaySignature(createNewOrderRequest.getRazorpaySignature())
                .status(createNewOrderRequest.getStatus()).build();
        

    }
    
    public CreateNewPaymentResponse toCreateNewPaymentResponse(Payment payment) {
    	return CreateNewPaymentResponse.builder()
    			.amount(payment.getAmount())
                .razorpayPaymentId(payment.getRazorpayPaymentId())
                .razorpayOrderId(payment.getRazorpayOrderId())
                .razorpaySignature(payment.getRazorpaySignature())
                .status(payment.getStatus())
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .build();
    }
}

package com.ecommercebackend.payload.request.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.ecommercebackend.dto.User;
import com.ecommercebackend.enums.EDeliveryMethod;
import com.ecommercebackend.enums.EPaymentMethod;
import com.ecommercebackend.payload.request.order.CreateNewOrderRequest;
import com.ecommercebackend.payload.request.order.VariantSizeRequest;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateNewPayment {

  private BigDecimal amount;
  private String razorpayPaymentId;
  private String razorpayOrderId;
  private String razorpaySignature;
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private LocalDateTime paymentDateTime;
  private String status;
  private String emailId;
//  private String mobileNumber;
	
}

package com.ecommercebackend.controller.common;

import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercebackend.dto.Payment;
import com.ecommercebackend.dto.User;
import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.mappers.PaymentMapper;
import com.ecommercebackend.payload.request.payment.CreateNewPayment;
import com.ecommercebackend.payload.response.ExceptionResponse;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.payment.CreateNewPaymentResponse;
import com.ecommercebackend.repository.PaymentRepository;
import com.ecommercebackend.service.AuthService;
import com.ecommercebackend.service.PaymentService;
import com.ecommercebackend.utils.Signature;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Value("${ecommerce.razorpay.key}")
    private static String RAZORPAY_KEY = "rzp_test_6i2bBxwtVUpKdh";
    
    @Value("${ecommerce.razorpay.secret}")
    private static String RAZORPAY_SECRET = "u94sCZLfWUPuBYb7gkPbdOb2";


    @Autowired
    AuthService authService;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentMapper paymentMapper;
    @Autowired
    PaymentService paymentService;

    @Operation(summary = "Get all Payments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments fetched successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Email or password is incorrect. | Account has not been activated. | Account has been blocked.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @GetMapping
    public List<Payment> getPayments(){
        return paymentRepository.findAll();
    }

    @PostMapping("/create_order")
    public String createOrder(@RequestBody Map<String, Object> data) throws RazorpayException {
        int amount = Integer.parseInt(data.get("amount").toString());
        RazorpayClient razorpayClient = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);

        JSONObject ob = new JSONObject();
        ob.put("amount", amount*100);
        ob.put("currency", "INR");
        ob.put("receipt", "rec_1234");

        Order order = razorpayClient.orders.create(ob);
        System.out.println(order);
        return order.toString();
    }

    @Operation(summary = "Create new payment by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment made successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))})
    })
    @PostMapping("/create_payment")
    @ResponseBody
    public SuccessResponse<?> createPayment(@RequestBody CreateNewPayment payment1) throws SignatureException, NotFoundException, BadRequestException {
        User user = authService.getUserLoggedIn();
        Payment payment = paymentMapper.toPayment(payment1);
        payment.setEmail(user.getEmail());
        String generatedSignature = Signature.calculateRFC2104HMAC(payment.getRazorpayOrderId() + "|" +payment.getRazorpayPaymentId(), "u94sCZLfWUPuBYb7gkPbdOb2");
        System.out.println(generatedSignature + " " + payment.getRazorpaySignature());
        if(payment.getRazorpaySignature().equals(generatedSignature)) {
            payment.setPaymentDateTime(LocalDateTime.now());
            paymentRepository.save(payment);
            CreateNewPaymentResponse createNewPaymentResponse = paymentMapper.toCreateNewPaymentResponse(payment);
            return  new SuccessResponse<>(createNewPaymentResponse, "Payment created successfully");
        }
        throw new BadRequestException("Invalid Transaction, Signature not verified");
    }

}
package com.ecommercebackend.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.ecommercebackend.dto.Order;
import com.ecommercebackend.dto.OrderDetail;
import com.ecommercebackend.dto.Payment;
import com.ecommercebackend.dto.User;
import com.ecommercebackend.dto.VariantSize;
import com.ecommercebackend.enums.EPaymentStatus;
import com.ecommercebackend.exception.ForbiddenException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.mappers.OrderDetailMapper;
import com.ecommercebackend.mappers.OrderMapper;
import com.ecommercebackend.payload.request.order.CreateNewOrderRequest;
import com.ecommercebackend.payload.request.order.VariantSizeRequest;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.order.CreateNewOrderResponse;
import com.ecommercebackend.payload.response.order.ListOrdersResponse;
import com.ecommercebackend.payload.response.order.OrderResponse;
import com.ecommercebackend.payload.response.order.OrderWithDetailResponse;
import com.ecommercebackend.repository.OrderDetailRepository;
import com.ecommercebackend.repository.OrderRepository;
import com.ecommercebackend.repository.PaymentRepository;
import com.ecommercebackend.repository.VariantSizeRepository;
import com.ecommercebackend.service.AuthService;
import com.ecommercebackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AuthService authService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private VariantSizeRepository variantSizeRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public SuccessResponse<OrderWithDetailResponse> getOrderByCode(String code) throws NotFoundException, ForbiddenException {
        Optional<Order> orderOptional = orderRepository.findByCode(code);
        if (orderOptional.isEmpty()) {
            throw new NotFoundException("Order with this code doesn't exist");
        }
        User user = authService.getUserLoggedIn();
        User userOfOrder = orderOptional.get().getUser();
        if (userOfOrder.getId() != user.getId()) {
            throw new ForbiddenException("You do not have permission.");
        }
        OrderWithDetailResponse orderWithDetailResponse = orderMapper.toOrderWithDetailResponse(orderOptional.get());
        return new SuccessResponse<>(orderWithDetailResponse, "Order retrieved successfully.");
    }

    @Override
    public SuccessResponse<ListOrdersResponse> getAllOrdersOfUser(long userId) throws NotFoundException, ForbiddenException {
        User user = authService.getUserLoggedIn();
        if (user.getId() != userId) {
            throw new ForbiddenException("You don't have permission.");
        }
        List<Order> orders = orderRepository.findAllByUserId(userId);
        List<OrderResponse> orderResponses = orders.stream()
                .map(orderMapper::toOrderResponse).collect(Collectors.toList());
        ListOrdersResponse listOrdersResponse = ListOrdersResponse.builder().orders(orderResponses).build();
        return new SuccessResponse<>(listOrdersResponse, "Got list orders successfully.");
    }

    @Override
    public SuccessResponse<OrderWithDetailResponse> getOrderWithDetail(Long orderId) throws NotFoundException, ForbiddenException {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new NotFoundException("Order with this id doesn't exist");
        }
        User userLoggedIn = authService.getUserLoggedIn();
        User user = orderOptional.get().getUser();
        if (user.getId() != userLoggedIn.getId()) {
            throw new ForbiddenException("You do not have permission.");
        }
        OrderWithDetailResponse orderWithDetailResponse = orderMapper.toOrderWithDetailResponse(orderOptional.get());
        return new SuccessResponse<>(orderWithDetailResponse, "Get order with detail success.");
    }
    
    @Override
    public SuccessResponse<CreateNewOrderResponse> createNewOrder(CreateNewOrderRequest createNewOrderRequest)
            throws NotFoundException {
        User user = authService.getUserLoggedIn();
        Payment payment = paymentRepository.findByRazorpayOrderId(createNewOrderRequest.getPaymentId());
        Order order = orderMapper.toOrder(createNewOrderRequest);

        if(payment.getStatus().equals("Paid")) 
        	order.setPaymentStatus(EPaymentStatus.PAID);
        order.setUser(user);
        order.setPayment(payment);

        Order orderDB = orderRepository.save(order);

        		
        List<Long> listVariantSizeIds = createNewOrderRequest.getProducts().stream()
                .map(VariantSizeRequest::getVariantSizeId).collect(Collectors.toList());
        List<VariantSize> listVariantSizes = variantSizeRepository.findAllByIdIn(listVariantSizeIds);
        Map<Long, VariantSize> variantSizeMap = new HashMap<>();
        for (VariantSize variantSize : listVariantSizes) {
            variantSizeMap.put(variantSize.getId(), variantSize);
        }
        List<OrderDetail> orderDetails = createNewOrderRequest.getProducts().stream()
                .map(item -> orderDetailMapper.toOrderDetail(item, variantSizeMap.get(item.getVariantSizeId()), orderDB))
                .collect(Collectors.toList());
        orderDetailRepository.saveAll(orderDetails);
        return new SuccessResponse<>(CreateNewOrderResponse.builder().code(orderDB.getCode()).build(), "Order successfully.");
    }

}

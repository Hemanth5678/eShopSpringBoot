package com.ecommercebackend.payload.response;

import java.io.Serializable;

import com.ecommercebackend.payload.response.category.CategoryResponse;
import com.ecommercebackend.payload.response.category.ListCategoriesResponse;
import com.ecommercebackend.payload.response.color.ColorResponse;
import com.ecommercebackend.payload.response.color.ListColorsResponse;
import com.ecommercebackend.payload.response.image.UploadImageResponse;
import com.ecommercebackend.payload.response.order.CreateNewOrderResponse;
import com.ecommercebackend.payload.response.order.ListOrdersResponse;
import com.ecommercebackend.payload.response.order.OrderWithDetailResponse;
import com.ecommercebackend.payload.response.payment.CreateNewPaymentResponse;
import com.ecommercebackend.payload.response.product.ListProductAdminWithPaginateResponse;
import com.ecommercebackend.payload.response.product.ListProductResponse;
import com.ecommercebackend.payload.response.product.ListProductWithPaginateResponse;
import com.ecommercebackend.payload.response.productdetail.ProductDetailAdminResponse;
import com.ecommercebackend.payload.response.productdetail.ProductDetailResponse;
import com.ecommercebackend.payload.response.rating.ListRatingsWithPaginateResponse;
import com.ecommercebackend.payload.response.size.ListSizesResponse;
import com.ecommercebackend.payload.response.size.SizeResponse;
import com.ecommercebackend.payload.response.user.ListUsersWithPaginateResponse;
import com.ecommercebackend.payload.response.user.UserLoginResponse;
import com.ecommercebackend.payload.response.user.UserResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse<T> implements Serializable{
    private final int code = 0;
    @Schema(anyOf = {
            UserResponse.class,
            NoData.class,
            ListUsersWithPaginateResponse.class,
            SizeResponse.class,
            ListSizesResponse.class,
            ListProductAdminWithPaginateResponse.class,
            ProductDetailAdminResponse.class,
            ColorResponse.class,
            ListColorsResponse.class,
            CategoryResponse.class,
            ListCategoriesResponse.class,
            UserLoginResponse.class,
            ListRatingsWithPaginateResponse.class,
            CreateNewOrderResponse.class,
            OrderWithDetailResponse.class,
            ListOrdersResponse.class,
            UploadImageResponse.class,
            ListProductWithPaginateResponse.class,
            ListProductResponse.class,
            ProductDetailResponse.class,
            ListCategoriesResponse.class,
            CreateNewPaymentResponse.class
    })
    private T data;
    private String message;
}

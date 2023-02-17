package com.ecommercebackend.payload.response.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import com.ecommercebackend.payload.response.category.CategoryResponse;

@Getter
@Setter
@Builder
public class ProductResponse implements Serializable{
    private int productId;
    private String name;
    private String description;
    private long price;
    private long discountPrice;
    private Date startDateDiscount;
    private Date endDateDiscount;
    private String slug;
    private String primaryImageName;
    private String primaryImageUrl;
    private String secondaryImageName;
    private String secondaryImageUrl;
    private boolean isVisible;
    private boolean isPromotion;
    private CategoryResponse category;
}

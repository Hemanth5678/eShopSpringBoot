package com.ecommercebackend.payload.request.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.ecommercebackend.payload.request.size.SizeRequest;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class UpdateProductRequest {
    private Integer id;
    @NotBlank
    private String name;
    @Min(1)
    private Long price;
    @Min(0)
    private Long discountPrice;
    private Date startDateDiscount;
    private Date endDateDiscount;
    @NotBlank
    private String description;
    @NotBlank
    private String primaryImageName;
    @NotBlank
    private String primaryImageUrl;
    @NotBlank
    private String secondaryImageName;
    @NotBlank
    private String secondaryImageUrl;
    private boolean isVisible;
    @NotBlank
    private String sku;
    private Integer categoryId;
    private List<SizeRequest> sizes;
}

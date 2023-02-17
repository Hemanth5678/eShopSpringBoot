package com.ecommercebackend.payload.request.image;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageOfCreateNewProduct {
    private String imageName;
    private String imageUrl;
}

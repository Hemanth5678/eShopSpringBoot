package com.ecommercebackend.mappers;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.ecommercebackend.dto.Color;
import com.ecommercebackend.payload.request.color.ColorOfCreateNewProduct;
import com.ecommercebackend.payload.request.color.CreateNewColorRequest;
import com.ecommercebackend.payload.response.color.ColorResponse;

@Component
public class ColorMapper implements Serializable{
    public Color toColor(CreateNewColorRequest createNewColorRequest) {
        return Color.builder().name(createNewColorRequest.getName()).isDeleted(false).build();
    }

    public Color toColor(ColorOfCreateNewProduct colorOfCreateNewProduct) {
        return Color.builder()
                .id(colorOfCreateNewProduct.getId())
                .name(colorOfCreateNewProduct.getName()).build();
    }

    public ColorResponse toColorResponse(Color color) {
        return ColorResponse.builder().id(color.getId()).name(color.getName()).build();
    }
}

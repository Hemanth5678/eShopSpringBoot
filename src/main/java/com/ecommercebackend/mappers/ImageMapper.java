package com.ecommercebackend.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ecommercebackend.dto.Image;
import com.ecommercebackend.dto.ProductVariant;
import com.ecommercebackend.payload.request.image.ImageOfCreateNewProduct;
import com.ecommercebackend.payload.response.image.ImageResponse;

@Component
public class ImageMapper {
    public List<Image> toListImagesWithProductVariant(List<ImageOfCreateNewProduct> images, ProductVariant productVariant) {
        return images.stream().map((image) -> Image.builder()
                .name(image.getImageName())
                .imageUrl(image.getImageUrl())
                .productVariant(productVariant).build()).collect(Collectors.toList());
    }

    public ImageResponse toImageResponse(Image image) {
        return ImageResponse.builder()
                .imageId(image.getId())
                .imageName(image.getName())
                .imageUrl(image.getImageUrl()).build();
    }

    public List<ImageResponse> toListImageResponse(Set<Image> images) {
        return images.stream().map(this::toImageResponse).collect(Collectors.toList());
    }
}

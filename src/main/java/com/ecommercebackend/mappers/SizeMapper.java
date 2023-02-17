package com.ecommercebackend.mappers;

import org.springframework.stereotype.Component;

import com.ecommercebackend.dto.Dimensions;
import com.ecommercebackend.payload.request.size.CreateNewSizeRequest;
import com.ecommercebackend.payload.request.size.SizeRequest;
import com.ecommercebackend.payload.response.size.SizeResponse;

@Component
public class SizeMapper {

    public Dimensions toSize(CreateNewSizeRequest createNewSizeRequest) {
        return Dimensions.builder().size(createNewSizeRequest.getSize()).isDeleted(false).build();
    }

    public Dimensions toSize(SizeRequest sizeRequest) {
        return Dimensions.builder().id(sizeRequest.getId()).size(sizeRequest.getSize()).build();
    }


    public SizeResponse toSizeResponse(Dimensions size) {
        return SizeResponse.builder().id(size.getId()).size(size.getSize()).build();
    }
}

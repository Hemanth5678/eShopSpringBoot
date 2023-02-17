package com.ecommercebackend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommercebackend.dto.Dimensions;
import com.ecommercebackend.dto.VariantSize;
import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.mappers.SizeMapper;
import com.ecommercebackend.payload.request.size.CreateNewSizeRequest;
import com.ecommercebackend.payload.request.size.UpdateSizeRequest;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.size.ListSizesResponse;
import com.ecommercebackend.payload.response.size.SizeResponse;
import com.ecommercebackend.repository.SizeRepository;
import com.ecommercebackend.service.SizeAdminService;

import lombok.Builder;

@Service
@Builder
public class SizeAdminServiceImpl implements SizeAdminService {

    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private SizeMapper sizeMapper;

    @Override
    public SuccessResponse<ListSizesResponse> getAllSizes() {
    	System.out.println("here2");
        List<Dimensions> sizes = sizeRepository.findAllByIsDeletedFalse();
        List<SizeResponse> sizeResponseList = sizes.stream().map(sizeMapper::toSizeResponse).collect(Collectors.toList());
        ListSizesResponse listSizesResponse =
                ListSizesResponse.builder().sizes(sizeResponseList).build();
        return new SuccessResponse<>(listSizesResponse, "Get list size success.");
    }
    
    @Override
    public SuccessResponse<SizeResponse> createNewSize(CreateNewSizeRequest createNewSizeRequest) throws BadRequestException {
        Optional<Dimensions> sizeOptional = sizeRepository.findBySizeAndIsDeletedFalse(createNewSizeRequest.getSize());
        if (sizeOptional.isPresent()) {
            throw new BadRequestException("This size already exists.");
        }
        Dimensions size = sizeMapper.toSize(createNewSizeRequest);
        Dimensions result = sizeRepository.save(size);
        return new SuccessResponse<>(sizeMapper.toSizeResponse(result), "Created new size successfully.");
    }


    @Override
    public SuccessResponse<SizeResponse> updateSize(UpdateSizeRequest updateSizeRequest) throws BadRequestException {
        Optional<Dimensions> sizeOptionalFindById = sizeRepository.findById(updateSizeRequest.getId());
        if (sizeOptionalFindById.isEmpty()) {
            throw new BadRequestException("Id of size is not exist.");
        }
        if (sizeOptionalFindById.get().getSize().equals(updateSizeRequest.getNewSize())) {
            throw new BadRequestException("New size is the same with old size. Nothing to update.");
        }
        Optional<Dimensions> sizeOptionalFindBySize = sizeRepository.findBySizeAndIsDeletedFalse(updateSizeRequest.getNewSize());
        if (sizeOptionalFindBySize.isPresent()) {
            throw new BadRequestException("This size already exist.");
        }
        Dimensions size = sizeOptionalFindById.get();
        size.setSize(updateSizeRequest.getNewSize());
        sizeRepository.save(size);
        return new SuccessResponse<>(sizeMapper.toSizeResponse(size), "Update size success.");
    }

    @Override
    public SuccessResponse<NoData> deleteSize(int sizeId) throws NotFoundException, BadRequestException {
        Optional<Dimensions> sizeOptional = sizeRepository.findById(sizeId);
        if (sizeOptional.isEmpty()) {
            throw new NotFoundException("Size with this id doesn't exist.");
        }
        Set<VariantSize> variantSizes = sizeOptional.get().getVariantSizes();
        if (variantSizes.size() > 0) {
            throw new BadRequestException("This size had products. Cannot delete!");
        }
        Dimensions size = sizeOptional.get();
        size.setDeleted(true);
        sizeRepository.save(size);
        return new SuccessResponse<>(NoData.builder().build(), "Deleted size successfully.");
    }
}

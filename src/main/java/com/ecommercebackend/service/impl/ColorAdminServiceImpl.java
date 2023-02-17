package com.ecommercebackend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.ecommercebackend.dto.Color;
import com.ecommercebackend.dto.ProductVariant;
import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.mappers.ColorMapper;
import com.ecommercebackend.payload.request.color.CreateNewColorRequest;
import com.ecommercebackend.payload.request.color.UpdateColorNameRequest;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.color.ColorResponse;
import com.ecommercebackend.payload.response.color.ListColorsResponse;
import com.ecommercebackend.repository.ColorRepository;
import com.ecommercebackend.service.ColorAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;


import lombok.Builder;

@Service
@Configurable
@Builder
@EnableCaching
public class ColorAdminServiceImpl implements ColorAdminService {

    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private ColorMapper colorMapper;

    @Override
    @Cacheable(value = "color")
    public SuccessResponse<ListColorsResponse> getAllColors() {
        List<Color> colorList = colorRepository.findAllByIsDeletedFalse();
        List<ColorResponse> sizeResponseList = colorList.stream().map(colorMapper::toColorResponse)
                .collect(Collectors.toList());
        ListColorsResponse listColorsResponse =
                ListColorsResponse.builder().colors(sizeResponseList).build();
        return new SuccessResponse<>(listColorsResponse, "Get list color success.");
    }
    
    @Override
    @CacheEvict(value = "color", allEntries = true)
    public SuccessResponse<ColorResponse> createNewColor(CreateNewColorRequest createNewColorRequest) throws BadRequestException {
        Optional<Color> colorOptional = colorRepository.findByNameAndIsDeletedFalse(createNewColorRequest.getName());
        if (colorOptional.isPresent()) {
            throw new BadRequestException("This color already existed.");
        }
        Color color = colorMapper.toColor(createNewColorRequest);
        Color result = colorRepository.save(color);
        return new SuccessResponse<>(colorMapper.toColorResponse(result), "Create new color successfully.");
    }

    @Override
    @CacheEvict(value = "color", allEntries = true)
    public SuccessResponse<ColorResponse> updateColorName(UpdateColorNameRequest updateColorNameRequest) throws BadRequestException {
        Optional<Color> colorOptionalFindById = colorRepository.findById(updateColorNameRequest.getId());
        if (colorOptionalFindById.isEmpty()) {
            throw new BadRequestException("Id of color is not exist.");
        }
        if (colorOptionalFindById.get().getName().equals(updateColorNameRequest.getNewName())) {
            throw new BadRequestException("New color is the same with old color. Nothing to update.");
        }
        Optional<Color> colorOptionalFindByName = colorRepository.findByNameAndIsDeletedFalse(updateColorNameRequest.getNewName());
        if (colorOptionalFindByName.isPresent()) {
            throw new BadRequestException("This color already exist.");
        }
        Color color = colorOptionalFindById.get();
        color.setName(updateColorNameRequest.getNewName());
        colorRepository.save(color);
        return new SuccessResponse<>(colorMapper.toColorResponse(color), "Update name of color success.");
    }

    @Override
    @CacheEvict(value = "color", allEntries = true)
    public SuccessResponse<NoData> deleteColor(int colorId) throws BadRequestException, NotFoundException {
        Optional<Color> colorOptional = colorRepository.findById(colorId);
        if (colorOptional.isEmpty()) {
            throw new NotFoundException("Don't exist color with this id.");
        }
        Set<ProductVariant> productVariantSet = colorOptional.get().getProductVariants();
        if (productVariantSet.size() > 0) {
            throw new BadRequestException("This color had products. Cannot delete!");
        }
        Color color = colorOptional.get();
        color.setDeleted(true);
        colorRepository.save(color);
        return new SuccessResponse<>(NoData.builder().build(), "Delete color successful.");
    }
}

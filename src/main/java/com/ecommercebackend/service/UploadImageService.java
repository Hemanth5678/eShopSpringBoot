package com.ecommercebackend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.image.UploadImageResponse;

public interface UploadImageService {
    public SuccessResponse<UploadImageResponse> uploadSingleImage(MultipartFile multipartFile) throws IOException;

    public SuccessResponse<List<UploadImageResponse>> uploadMultiImages(List<MultipartFile> multipartFiles) throws IOException;
}

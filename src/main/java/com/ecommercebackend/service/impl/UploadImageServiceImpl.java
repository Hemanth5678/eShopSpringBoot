package com.ecommercebackend.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.image.UploadImageResponse;
import com.ecommercebackend.service.UploadImageService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class UploadImageServiceImpl implements UploadImageService {

//    @Value("${firebase.bucket-name}")
    private String FIREBASE_BUCKET_NAME = "sample-131a0.appspot.com";
//    @Value("${firebase.generate-private-key-url}")
    private String FIREBASE_GENERATED_PRIVATE_KEY_URL = "C:\\Users\\Hemanthav\\Downloads\\firebaseprivateKey.json";
//    @Value("${firebase.image-url}")
    private String FIREBASE_IMAGE_URL = "https://firebasestorage.googleapis.com/v0/b/sample-131a0.appspot.com/o/%s?alt=media";

    public SuccessResponse<UploadImageResponse> uploadSingleImage(MultipartFile multipartFile) throws IOException {
        UploadImageResponse uploadImageResponse = uploadImage(multipartFile);                                                         // to delete the copy of uploaded file stored in the project folder
        return new SuccessResponse<>(uploadImageResponse, "Successfully uploaded.");
    }

    @Override
    public SuccessResponse<List<UploadImageResponse>> uploadMultiImages(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadImageResponse> uploadImageResponseList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            UploadImageResponse uploadImageResponse = uploadImage(multipartFile);
            uploadImageResponseList.add(uploadImageResponse);
        }
        return new SuccessResponse<>(uploadImageResponseList, "Successfully uploaded.");
    }

    private UploadImageResponse uploadImage(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));    // to generated random string values for file name.
        File file = convertToFile(multipartFile, fileName);                             // to convert multipartFile to File
        String tempUrl = uploadFile(file, fileName);                                    // to get uploaded file link
        file.delete();
        return UploadImageResponse.builder().imageName(fileName).imageUrl(tempUrl).build();
    }


    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of(FIREBASE_BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(FIREBASE_GENERATED_PRIVATE_KEY_URL));

        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        Blob blob =  storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        return String.format(FIREBASE_IMAGE_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

}

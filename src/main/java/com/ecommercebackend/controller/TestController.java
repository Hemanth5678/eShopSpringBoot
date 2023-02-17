//package com.ecommercebackend.controller;
//
//import java.io.IOException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.ecommercebackend.service.FileService;
//
//@RestController
//@RequestMapping("api/v1/admin/images")
//public class TestController {
//
//	@Autowired
//	private FileService fileService;
//
//	  @PostMapping("/profile/pic")
//	    public Object upload(@RequestParam("file") MultipartFile multipartFile) {
//	        return fileService.upload(multipartFile);
//	    }
//
//	    @PostMapping("/profile/pic/{fileName}")
//	    public Object download(@PathVariable String fileName) throws IOException {
//	        return fileService.download(fileName);
//	    }
//}

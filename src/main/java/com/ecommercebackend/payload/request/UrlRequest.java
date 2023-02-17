package com.ecommercebackend.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UrlRequest {
	
	@NotBlank
	@NotNull
	private String url;

}

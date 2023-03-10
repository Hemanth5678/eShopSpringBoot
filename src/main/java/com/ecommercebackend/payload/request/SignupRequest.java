package com.ecommercebackend.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequest {

	@NotBlank
	@Size(min = 3, max = 20)
	private String userName;
	
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	private Set<String> role;

	@NotBlank
	@Size(min = 5, max = 40)
	private String password;
	
	@NotBlank
	@Size(min = 3, max = 100)
	private String address;
	
	
}

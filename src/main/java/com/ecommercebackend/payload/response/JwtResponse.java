package com.ecommercebackend.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

	  private String token; 
	  private String type = "Bearer";
	  private Integer id;
	  private String userName;
	  private String email;
	  private List<String> roles;
	  
	  public JwtResponse(String accessToken, Integer id, String userName, String email, List<String> roles) {
		  this.token = accessToken;
		  this.id = id;
		  this.userName = userName;
		  this.email = email;
		  this.roles = roles;
	  }
}

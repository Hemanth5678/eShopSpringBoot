package com.ecommercebackend.payload.response.user;

import java.io.Serializable;
import java.util.Date;

import com.ecommercebackend.enums.EAccountStatus;
import com.ecommercebackend.enums.ERoles;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse implements Serializable{
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private ERoles role;
    private EAccountStatus status;
    private Date createdAt;
    private Date updatedAt;
}

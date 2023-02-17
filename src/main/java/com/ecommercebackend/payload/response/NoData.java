package com.ecommercebackend.payload.response;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NoData implements Serializable{
    private final String NoData = "No Data";
}

package com.ecommercebackend.payload.response.color;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ColorResponse implements Serializable{
    private int id;
    private String name;
}

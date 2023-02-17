package com.ecommercebackend.payload.response.color;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class ListColorsResponse implements Serializable{
    List<ColorResponse> colors;
}

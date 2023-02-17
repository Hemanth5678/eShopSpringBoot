package com.ecommercebackend.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RatingKey implements Serializable {
    @Column(name = "user_id")
    private long userId;
    @Column(name = "product_id")
    private int productId;
}

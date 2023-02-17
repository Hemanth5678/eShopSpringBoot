package com.ecommercebackend.dto;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "size_tbl")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dimensions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id")
    private int id;

    @Column(name = "size")
    private String size;
    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "size", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<VariantSize> variantSizes;
}

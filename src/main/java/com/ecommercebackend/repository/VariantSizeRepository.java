package com.ecommercebackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommercebackend.dto.VariantSize;

@Repository
public interface VariantSizeRepository extends JpaRepository<VariantSize, Long> {
    public List<VariantSize> findBySkuStartsWith(String sku);

    public List<VariantSize> findAllByIdIn(List<Long> idList);
}

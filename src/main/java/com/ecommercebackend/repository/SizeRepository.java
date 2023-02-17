package com.ecommercebackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommercebackend.dto.Dimensions;

@Repository
public interface SizeRepository extends JpaRepository<Dimensions, Integer> {

    public Optional<Dimensions> findBySizeAndIsDeletedFalse(String size);

    public List<Dimensions> findAllByIsDeletedFalse();
}

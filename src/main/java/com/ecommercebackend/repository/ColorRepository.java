package com.ecommercebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommercebackend.dto.Color;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    public Optional<Color> findByNameAndIsDeletedFalse(String name);

    public List<Color> findAllByIsDeletedFalse();
}

package com.ecommercebackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommercebackend.dto.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public Optional<Category> findByNameAndIsDeletedFalse(String name);

    public List<Category> findAllByIsDeletedFalse();

    public Optional<Category> findByIdAndIsDeletedFalse(int id);

}

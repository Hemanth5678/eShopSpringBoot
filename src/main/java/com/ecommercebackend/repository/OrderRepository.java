package com.ecommercebackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommercebackend.dto.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public Optional<Order> findByCode(String code);

    public List<Order> findAllByUserId(Long userId);
}

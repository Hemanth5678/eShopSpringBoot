package com.ecommercebackend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import com.ecommercebackend.dto.User;
import com.ecommercebackend.enums.EAccountStatus;
import com.ecommercebackend.enums.ERoles;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    public Optional<User> findByPhone(String phone);

    public Page<User> findAllByRoleAndStatus(ERoles role, EAccountStatus status, Pageable pageable);
}

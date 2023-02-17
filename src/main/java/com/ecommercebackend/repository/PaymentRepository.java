package com.ecommercebackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommercebackend.dto.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	//public Payment findByOrderId(long id);
//	public Payment findById(Long id);
	public Payment findByRazorpayOrderId(String razorpayOrderId);
}

package com.example.VRMPaymentService.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.VRMPaymentService.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}

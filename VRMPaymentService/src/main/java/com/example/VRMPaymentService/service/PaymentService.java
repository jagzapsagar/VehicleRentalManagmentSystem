package com.example.VRMPaymentService.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.VRMPaymentService.entity.Payment;
import com.example.VRMPaymentService.repo.PaymentRepository;


@Service
public class PaymentService {
	
	@Autowired
    private PaymentRepository paymentRepository;
	
	public Payment savePayment(Payment pay) {
		/*
		 * Payment payment = new Payment(); payment.setBookingId(bookingId);
		 * payment.setAmount(amount); payment.setStatus(status);
		 * payment.setPaymentDate(LocalDateTime.now());
		 */
        return paymentRepository.save(pay);
    }

}

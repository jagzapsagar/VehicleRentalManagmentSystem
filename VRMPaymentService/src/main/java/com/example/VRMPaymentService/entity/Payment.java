package com.example.VRMPaymentService.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Long bookingId;

    private int amount;

    private LocalDateTime paymentDate;

    private String status;

	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Payment(Long paymentId, Long bookingId, int amount, LocalDateTime paymentDate, String status) {
		super();
		this.paymentId = paymentId;
		this.bookingId = bookingId;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.status = status;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int i) {
		this.amount = i;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
	/*
	 * @PrePersist public void prePersist() { this.paymentDate =
	 * LocalDateTime.now(); }
	 */

}

package com.example.VRMPaymentService.dto;

public class PaymentResponse {
	private String id;
    private String currency;
    private double amount;
    private String status;
	public PaymentResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PaymentResponse(String id, String currency, double amount, String status) {
		super();
		this.id = id;
		this.currency = currency;
		this.amount = amount;
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    

}

package com.example.VRMPaymentService.dto;

public class PaymentRequest {
	
	private int amount;
    private String currency = "INR";
    private String receipt;
	public PaymentRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PaymentRequest(int amount, String currency, String receipt) {
		super();
		this.amount = amount;
		this.currency = currency;
		this.receipt = receipt;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	@Override
	public String toString() {
		return "PaymentRequest [amount=" + amount + ", currency=" + currency + ", receipt=" + receipt + "]";
	}
    
    

}

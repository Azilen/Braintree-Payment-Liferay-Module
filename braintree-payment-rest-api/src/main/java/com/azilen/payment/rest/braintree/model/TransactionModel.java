package com.azilen.payment.rest.braintree.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TransactionModel {

	private String payment_method_nonce;
	private String merchant;
	private String amount;
	private boolean escrowcheck;
	private String userId;
	private BigInteger serviceFee;

	public BigInteger getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(BigInteger serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPayment_method_nonce() {
		return payment_method_nonce;
	}
	public void setPayment_method_nonce(String payment_method_nonce) {
		this.payment_method_nonce = payment_method_nonce;
	}
	public String getMerchant() {
		return merchant;
	}
	public boolean isEscrowcheck() {
		return escrowcheck;
	}
	public void setEscrowcheck(boolean escrowcheck) {
		this.escrowcheck = escrowcheck;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}

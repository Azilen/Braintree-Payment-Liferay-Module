package com.azilen.payment.rest.braintree.model;
/* 
 * @author nirali
 * Merchant model containing required details for creating merchant in braintree
 */
public class MerchantModel {
	private String subMerchantId;
	private String firstName;
	private String lastname;
	private String emailAddress;
	private String phoneNo;
	private String dateOfBirth;
	private String ssn;
	private String locality;
	private String postalCode;
	private String region;
	private String bankDetail;
	private String streetAddress;
	private String masterMerchantAccountId;
	private String accountNumber;
	private String routingNumber;
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getRoutingNumber() {
		return routingNumber;
	}
	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}
	public String getSubMerchantId() {
		return subMerchantId;
	}
	public void setSubMerchantId(String subMerchantId) {
		this.subMerchantId = subMerchantId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getBankDetail() {
		return bankDetail;
	}
	public void setBankDetail(String bankDetail) {
		this.bankDetail = bankDetail;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getMasterMerchantAccountId() {
		return masterMerchantAccountId;
	}
	public void setMasterMerchantAccountId(String masterMerchantAccountId) {
		this.masterMerchantAccountId = masterMerchantAccountId;
	}
}

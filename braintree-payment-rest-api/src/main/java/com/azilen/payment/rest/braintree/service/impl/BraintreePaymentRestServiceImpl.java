package com.azilen.payment.rest.braintree.service.impl;

import com.azilen.payment.rest.braintree.constant.RestConstant;
import com.azilen.payment.rest.braintree.model.CustomerModel;
import com.azilen.payment.rest.braintree.model.MerchantModel;
import com.azilen.payment.rest.braintree.model.TransactionModel;
import com.braintreegateway.Address;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.CustomerRequest;
import com.braintreegateway.CustomerSearchRequest;
import com.braintreegateway.IndividualDetails;
import com.braintreegateway.MerchantAccount;
import com.braintreegateway.MerchantAccount.Status;
import com.braintreegateway.MerchantAccountRequest;
import com.braintreegateway.PaginatedCollection;
import com.braintreegateway.ResourceCollection;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.TransactionSearchRequest;
import com.braintreegateway.exceptions.NotFoundException;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

public class BraintreePaymentRestServiceImpl {
	
	private static Log LOG = LogFactoryUtil.getLog(BraintreePaymentRestServiceImpl.class);
	
	/**
	 * @author nirali
	 * This method is used to get keys required for integration with braintree 
	 * @return
	 */
	public static BraintreeGateway getBraintreeGateway(){
		final Configuration portletProperties = ConfigurationFactoryUtil
			    .getConfiguration(BraintreeGateway.class.getClassLoader(), "portlet");
		String environment = portletProperties.get("BT_ENVIRONMENT");
		String merchantId=portletProperties.get("BT_MERCHANT_ID");
		String publicKey=portletProperties.get("BT_PUBLIC_KEY");
		String privateKey=portletProperties.get("BT_PRIVATE_KEY");
		BraintreeGateway brainTree= new BraintreeGateway(environment, merchantId, publicKey, privateKey);
		return brainTree;
	}
	
	/**
	 * @author nirali
	 * @param BrainTreeGateway object
	 * This method is used to generate clienttoken for loading dropin UI
	 * @return
	 */
	public static String generateTokenForDropInUI(BraintreeGateway brainTree){
		LOG.info("Entry:generateTokenForDropInUI");
		LOG.debug("token"+brainTree.clientToken().generate());
		return brainTree.clientToken().generate();
		
	}
	
	/**
	 * @author nirali
	 * @param brainTree
	 * @param transaction model 
	 * This method is used to submit the create transaction form
	 * @return
	 */
	public static String submitForm(BraintreeGateway brainTree,TransactionModel parameter){
		LOG.info("Entry:Submit transaction create form method");
		TransactionRequest transactionRequest=createTransaction(parameter);
		Result<Transaction> result= brainTree.transaction().sale(transactionRequest);
		String message="";
		if(result.isSuccess()){
			message=RestConstant.TRANSACTION_CREATED+result.getTarget().getId();
		}
		else{
			message=result.getMessage()+RestConstant.TRANSACTION_NOT_CREATED;
		}
		LOG.info("Exit:Submit transaction create form method");
		return message;
	}
	
	/**
	 * @author nirali
	 * @param transaction model
	 * This method is used to create transaction
	 * @return
	 */
	public static TransactionRequest createTransaction(TransactionModel parameter){
		LOG.info("Entry:Create transaction method");
		BigDecimal decimalAmount=new BigDecimal(parameter.getAmount());
		TransactionRequest transaction=new TransactionRequest().amount(decimalAmount).paymentMethodNonce(parameter.getPayment_method_nonce()).customerId(parameter.getUserId()).merchantAccountId(parameter.getMerchant())
				.options().submitForSettlement(true).done()
				.serviceFeeAmount(new BigDecimal(parameter.getServiceFee()));
		LOG.info("Exit:Create transaction method");
		return transaction;
		
	}
	
	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param customerId
	 * This method is used to serach transaction by customer
	 * @return
	 */
	public static String searchTransactionByCustomer(BraintreeGateway gateway,String customerId){
		LOG.info("Entry:Search  transaction By Customer method");
		TransactionSearchRequest request = new TransactionSearchRequest()
			    .customerId().is(customerId);
		JSONArray transactionJsonArray=JSONFactoryUtil.createJSONArray();
			ResourceCollection<Transaction> collection = gateway.transaction().search(request);
			
			for (Transaction transaction : collection) {
				JSONObject transactionJsonObject=JSONFactoryUtil.createJSONObject();
				transactionJsonObject.put("transactionId", transaction.getId());
				Calendar createdAt = transaction.getCreatedAt();
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
				String formattedDate = format.format(createdAt.getTime());
				transactionJsonObject.put("transactionCreated", formattedDate);
				if (transaction.getStatus() != null) {
					transactionJsonObject.put("status", transaction.getStatus().name());
				}
				if (transaction.getCustomer() != null) {
					transactionJsonObject.put("customerName",transaction.getCustomer().getFirstName());
				}
				transactionJsonObject.put("transactionType", transaction.getType().name());
				transactionJsonObject.put("amount", transaction.getAmount().toPlainString());
				transactionJsonArray.put(transactionJsonObject);
			}
			LOG.info("Exit:Search  transaction By Customer method");
			return transactionJsonArray.toJSONString();
	}
	
	/**
	 * @author nirali
	 * @param Braintreegateway Object
	 * @param transactionId
	 * This method is used to search transaction by specified transaction id
	 * @return
	 * @throws NotFoundException
	 */
	public static String searchTransaction(BraintreeGateway gateway,String transactionId)throws NotFoundException{
		LOG.info("Entry:Search  transaction By Transaction ID method");
		Transaction transaction = gateway.transaction().find(transactionId);
		JSONObject jsonObject= JSONFactoryUtil.createJSONObject();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		if(transaction != null){
			String date=dateFormat.format(transaction.getCreatedAt().getTime());
			jsonObject.put("customerEmail", transaction.getCustomer().getEmail());
			jsonObject.put("amount", transaction.getAmount().toString());
			jsonObject.put("status", transaction.getStatus());
			jsonObject.put("createdDate",date);
		}
		LOG.info("Exit:Search  transaction By Transaction ID method");
		return jsonObject.toString();
	}
	
	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param transactionId
	 * This method is used to cancel transaction
	 * @return
	 */
	public static String cancelTransaction(BraintreeGateway gateway,String transactionId) {
		LOG.info("Entry:  cancelTransaction method");
		Transaction transaction = gateway.transaction().find(transactionId);
		CreditCard creditCard = null;
		Customer customer = null;
		JSONObject transactionJson=JSONFactoryUtil.createJSONObject();
		try {
			if (transaction.getStatus() != null) {
				
				if (transaction.getStatus().toString().equals(RestConstant.SETTLED)) {
					LOG.info("Entered in refund");
					Result<Transaction> result=gateway.transaction().refund(transactionId, transaction.getAmount());
					transactionJson.put("refundTransactionId",result.getTarget().getId());
				} else {
					LOG.info("Entered in cancel");
					 gateway.transaction().voidTransaction(transactionId);
				}
			}
			transaction = gateway.transaction().find(transactionId);
			creditCard = transaction.getCreditCard();
			customer = transaction.getCustomer();
		} catch (Exception e) {
			LOG.info("Exception: " + e);
		}
		transactionJson.put("transaction", transaction.getStatus());
		transactionJson.put("creditCardDetails", creditCard.getExpirationMonth()+"/"+creditCard.getExpirationYear());
		transactionJson.put("customerDetails", customer.getEmail());
		LOG.info("Exit: cancelTransaction method");
		return transactionJson.toString();
	}
	
	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param merchant model
	 * This method is used to create sub merchant
	 * @return
	 */
	public static String createMerchant(BraintreeGateway gateway,MerchantModel merchant){
		LOG.info("Entry: Create Submerchant method");
		JSONArray resultJsonArray=JSONFactoryUtil.createJSONArray();
		JSONObject resultJson= JSONFactoryUtil.createJSONObject();
		MerchantAccountRequest request = new MerchantAccountRequest().
			    individual().
			        firstName(merchant.getFirstName()).
			        lastName(merchant.getLastname()).
			        email(merchant.getEmailAddress()).
			        phone(merchant.getPhoneNo()).
			        dateOfBirth(merchant.getDateOfBirth()).
			        ssn(merchant.getSsn()).
			        address().
			            streetAddress(merchant.getStreetAddress()).
			            locality(merchant.getLocality()).
			            region(merchant.getRegion()).
			            postalCode(merchant.getPostalCode()).
			            done().
			        done().
			    funding().
			        destination(MerchantAccount.FundingDestination.BANK).
			        routingNumber(merchant.getRoutingNumber()).
			        accountNumber(merchant.getAccountNumber()).
			        done().
			    tosAccepted(true).
			    masterMerchantAccountId(merchant.getMasterMerchantAccountId()).
			    id(merchant.getSubMerchantId());
			Result<MerchantAccount> result = gateway.merchantAccount().create(request);
			Status status=null;
			String message=result.getMessage();
			if(result.getTarget() != null){
				status=result.getTarget().getStatus();
			}
			
			if(status != null){
				if(status.name().equalsIgnoreCase(RestConstant.MERCHANT_PENDING_STATUS) || status.name().equalsIgnoreCase(RestConstant.MERCHANT_ACTIVE_STATUS)){
					resultJson.put("id",result.getTarget().getId());
					resultJson.put("message", RestConstant.MERCHANT_CREATED);
				}
			}
			else{
				resultJson.put("id","");
				resultJson.put("message", message+" "+RestConstant.MERCHANT_NOT_CREATED);
			}
			resultJsonArray.put(resultJson);
			LOG.info("Exit: Create Submerchant method");
			return resultJsonArray.toString();
	}
	
	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param merchant model
	 * This methos is used to update braintree submerchant 
	 * @return
	 */
	public static String updateMerchant(BraintreeGateway gateway,MerchantModel merchant){
		LOG.info("Entry: Update Submerchant method");
		JSONArray resultJsonArray=JSONFactoryUtil.createJSONArray();
		JSONObject resultJson= JSONFactoryUtil.createJSONObject();
		try{
		MerchantAccountRequest request = new MerchantAccountRequest().
			    individual().
			        firstName(merchant.getFirstName()).
			        lastName(merchant.getLastname()).
			        email(merchant.getEmailAddress()).
			        phone(merchant.getPhoneNo()).
			        dateOfBirth(merchant.getDateOfBirth()).
			        ssn(merchant.getSsn()).
			        address().
			            streetAddress(merchant.getStreetAddress()).
			            locality(merchant.getLocality()).
			            region(merchant.getRegion()).
			            postalCode(merchant.getPostalCode()).
			            done().
			        done().
			    funding().
			        destination(MerchantAccount.FundingDestination.BANK).
			        routingNumber(merchant.getRoutingNumber()).
			        accountNumber(merchant.getAccountNumber()).
			        done().
			    tosAccepted(true).
			    masterMerchantAccountId(merchant.getMasterMerchantAccountId()).
			    id(merchant.getSubMerchantId());
			Result<MerchantAccount> result = gateway.merchantAccount().update(merchant.getSubMerchantId(), request);
			Status status=null;
			String message=result.getMessage();
			if(result.getTarget() != null){
				status=result.getTarget().getStatus();
			}
			
			if(status != null){
				if(status.name().equalsIgnoreCase(RestConstant.MERCHANT_ACTIVE_STATUS)){
					resultJson.put("id",result.getTarget().getId());
					resultJson.put("message", RestConstant.MERCHANT_UPDATED);
				}
			}
			else{
				resultJson.put("id","");
				resultJson.put("message", message+" "+RestConstant.MERCHANT_NOT_UPDATED);
			}
			resultJsonArray.put(resultJson);
			LOG.info("Exit: Update Submerchant method");
			return resultJsonArray.toString();
		}
		catch(NotFoundException e){
			return RestConstant.MERCHANT_NOT_FOUND;
		}
	}

	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param user model
	 * @param merchant model
	 * This method is used to create liferay user as submerchant
	 * @return
	 * @throws PortalException
	 */
	public static String createLiferayUserAsMerchant(BraintreeGateway gateway,User user,MerchantModel merchant) throws PortalException{
		LOG.info("Entry: Create Liferay User As Submerchant method");
		JSONArray resultJsonArray=JSONFactoryUtil.createJSONArray();
		JSONObject resultJson= JSONFactoryUtil.createJSONObject();
		MerchantAccountRequest merchantAccountRequest = new MerchantAccountRequest();
		merchantAccountRequest.individual().firstName(user.getFirstName()).
				        lastName(user.getLastName()).
				        email(user.getEmailAddress()).
				        dateOfBirth(merchant.getDateOfBirth()).
				        phone(merchant.getPhoneNo()).
				        ssn(merchant.getSsn()).
				        address().
				            streetAddress(merchant.getStreetAddress()).
				            locality(merchant.getLocality()).
				            region(merchant.getRegion()).
				            postalCode(merchant.getPostalCode()).
				            done().
				        done().
				    funding().
				        destination(MerchantAccount.FundingDestination.BANK).
				        accountNumber(merchant.getAccountNumber()).
				        routingNumber(merchant.getRoutingNumber()).
				        done().
				    tosAccepted(true).
				    masterMerchantAccountId(merchant.getMasterMerchantAccountId()).
				    id(String.valueOf(user.getUserId()));
			
		Result<MerchantAccount> result = gateway.merchantAccount().create(merchantAccountRequest);
		Status status=null;
		String message=result.getMessage();
		if(result.getTarget() != null){
			status=result.getTarget().getStatus();
		}
		
		if(status != null){
			if(status.name().equalsIgnoreCase(RestConstant.MERCHANT_PENDING_STATUS) || status.name().equalsIgnoreCase(RestConstant.MERCHANT_ACTIVE_STATUS)){
				resultJson.put("id",result.getTarget().getId());
				resultJson.put("message", RestConstant.MERCHANT_CREATED);
			}
		}
		else{
			resultJson.put("id","");
			resultJson.put("message", message+" "+RestConstant.MERCHANT_NOT_CREATED);
		}
		resultJsonArray.put(resultJson);
		LOG.info("Exit: Create Liferay User As Submerchant method");
		return resultJsonArray.toString();
	}
	
	
	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * This methd is used to get list of braintree submerchants
	 * @return
	 */
	public static String getMerchants(BraintreeGateway gateway){
		LOG.info("Entry: get merchants method");
		PaginatedCollection<MerchantAccount> merchantAccountsIterable = gateway.merchantAccount().all();
		JSONArray merchantsList=JSONFactoryUtil.createJSONArray();
		Iterator<MerchantAccount> merchants = merchantAccountsIterable.iterator();
		for (MerchantAccount merchantAccount : merchantAccountsIterable){ 
			if(merchantAccount.isSubMerchant()){
				JSONObject jsonObject= JSONFactoryUtil.createJSONObject();
				jsonObject.put("id", merchantAccount.getId());
				if(merchantAccount.getIndividualDetails() != null){
					IndividualDetails individualDetails= merchantAccount.getIndividualDetails();
					if(individualDetails.getFirstName() != "" || individualDetails.getLastName() != "" || individualDetails.getDateOfBirth() != "" ||
							individualDetails.getEmail() != ""){
						jsonObject.put("firstName", individualDetails.getFirstName());
						jsonObject.put("lastName", individualDetails.getLastName());
						jsonObject.put("dateOfBirth", individualDetails.getDateOfBirth());
						jsonObject.put("emailAddress", individualDetails.getEmail());
					}
					if(individualDetails.getAddress() != null){
						Address merchantAddress=individualDetails.getAddress();
						if(merchantAddress.getLocality() != "" || merchantAddress.getPostalCode() != "" || merchantAddress.getPostalCode() != "" || merchantAddress.getStreetAddress() != ""){
							jsonObject.put("locality", merchantAddress.getLocality());
							jsonObject.put("postalcode", merchantAddress.getPostalCode());
							jsonObject.put("streetAddress", merchantAddress.getStreetAddress());
							jsonObject.put("region", merchantAddress.getRegion());
						}
					}
				}
				else{
					jsonObject.put("name",merchantAccount.getId());
					jsonObject.put("dateOfBirth", "");
					jsonObject.put("emailAddress", "");
				}
				if(merchantAccount.getFundingDetails() != null){
					jsonObject.put("accountNumber", merchantAccount.getFundingDetails().getAccountNumberLast4()); 
				}
				else{
					jsonObject.put("accountNumber", "");
				}
				if(merchantAccount.getMasterMerchantAccount() != null){
					jsonObject.put("master_merchant_account", merchantAccount.getMasterMerchantAccount().getId());
				}
				else{
					jsonObject.put("master_merchant_account", "");
				}
				 merchantsList.put(jsonObject);
			}
		   
			LOG.debug(merchantsList.toString());
			LOG.info("Exit: get merchants method");
		}
		return merchantsList.toString();
	}
	
	/**
	 * @author nirali
	 * @param submerchant emailaddress
	 * @param Briantreegateway object
	 * This method is used to search submerchant with specified submerchant email address
	 * @return
	 */
	public static String searchMerchant(BraintreeGateway gateway,String emaiAddress){
		LOG.info("Entry: Search merchant method");
		PaginatedCollection<MerchantAccount> merchantAccountsIterable = gateway.merchantAccount().all();
		Iterator<MerchantAccount> merchants = merchantAccountsIterable.iterator();
		JSONObject jsonObject=JSONFactoryUtil.createJSONObject();
		while (merchants.hasNext()) {
			MerchantAccount merchantAccount = merchants.next();
			if(merchantAccount.isSubMerchant()){
					if(merchantAccount.getIndividualDetails() != null){
						if(merchantAccount.getIndividualDetails().getEmail().equalsIgnoreCase(emaiAddress)){
							IndividualDetails individualDetails=merchantAccount.getIndividualDetails();
							jsonObject.put("id", merchantAccount.getId());
							jsonObject.put("firstName", individualDetails.getFirstName());
							jsonObject.put("lastName", individualDetails.getLastName());
							jsonObject.put("dateOfBirth", individualDetails.getDateOfBirth());
							if(individualDetails.getAddress() != null){
								Address merchantAddress=individualDetails.getAddress();
								jsonObject.put("locality", merchantAddress.getLocality());
								jsonObject.put("postalcode", merchantAddress.getPostalCode());
								jsonObject.put("streetAddress", merchantAddress.getStreetAddress());
								jsonObject.put("region", merchantAddress.getRegion());
							}
							if(merchantAccount.getFundingDetails() != null){
								jsonObject.put("accountNumber", merchantAccount.getFundingDetails().getAccountNumberLast4());
							}
							jsonObject.put("master_merchant_account", merchantAccount.getMasterMerchantAccount().getId());
						}
				}
				
			}
		}
		LOG.info("Exit: Search merchant method");
		return jsonObject.toString();
	}
	
	/**
	 * @author nirali
	 * @param merchantId
	 * @param Briantreegateway object
	 * This method is used to get submerchant with specified submerchant id
	 * @return
	 */
	public static String getSubMerchant(String merchantId,BraintreeGateway gateway){
		LOG.info("Entry: get submerchant method");
		MerchantAccount merchant= gateway.merchantAccount().find(merchantId);
		JSONObject merchantJson=JSONFactoryUtil.createJSONObject();
		merchantJson.put("id", merchantId);
		merchantJson.put("firstName", merchant.getIndividualDetails().getFirstName());
		merchantJson.put("lastName", merchant.getIndividualDetails().getLastName());
		merchantJson.put("dateOfBirth",merchant.getIndividualDetails().getDateOfBirth());
		merchantJson.put("emailAddress", merchant.getIndividualDetails().getEmail());
		merchantJson.put("locality", merchant.getIndividualDetails().getAddress().getLocality());
		merchantJson.put("postalCode", merchant.getIndividualDetails().getAddress().getPostalCode());
		merchantJson.put("streetAddress", merchant.getIndividualDetails().getAddress().getStreetAddress());
		merchantJson.put("region", merchant.getIndividualDetails().getAddress().getRegion());
		if(merchant.getIndividualDetails().getPhone() != null){
			merchantJson.put("phoneNumber", merchant.getIndividualDetails().getPhone());
		}
		else{
			merchantJson.put("phoneNumber", " ");
		}
		
		if(merchant.getIndividualDetails().getSsnLast4() != null){
			merchantJson.put("ssn",merchant.getIndividualDetails().getSsnLast4());
		}
		else{
			merchantJson.put("ssn"," ");
		}
		
		merchantJson.put("accountNumber", merchant.getFundingDetails().getAccountNumberLast4());
		merchantJson.put("routingNumber", merchant.getFundingDetails().getRoutingNumber());
		merchantJson.put("master_merchant_account", merchant.getMasterMerchantAccount().getId());
		LOG.info("Exit: get submerchant method");
		return merchantJson.toString();
		
	}
	
	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param customer
	 * This method is used to create customer
	 * @return
	 */
	public static String createCustomer(BraintreeGateway gateway,CustomerModel customer){
		LOG.info("Entry: Create customer method");
		JSONArray resultJsonArray=JSONFactoryUtil.createJSONArray();
		JSONObject resultJson= JSONFactoryUtil.createJSONObject();
		CustomerRequest request = new CustomerRequest()
			    .firstName(customer.getFirstName())
			    .lastName(customer.getLastName())
			     .email(customer.getEmailAddress())
			    .id(String.valueOf(customer.getCustomerId()));
		
		Result<Customer> customerResult =
		          gateway.customer().create(request);
		String message=customerResult.getMessage();
		if(customerResult.isSuccess()){
			LOG.debug(customerResult.isSuccess()+"success");
			resultJson.put("id",customerResult.getTarget().getId() );
			resultJson.put("message", RestConstant.CUSTOMER_CREATED);
		}
		else{
			LOG.debug(customerResult.isSuccess()+"failure");
			resultJson.put("id", "");
			resultJson.put("message",message+RestConstant.CUSTOMER_NOT_CREATED);
		}
		resultJsonArray.put(resultJson);
		LOG.info("Exit: Create customer method");
		return resultJsonArray.toString();
		
	}
	
	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param customer model
	 * This method is used to update the customer
	 * @return
	 */
	public static String updateCustomer(BraintreeGateway gateway,CustomerModel customer){
		LOG.info("Entry: Update customer method");
		JSONArray resultJsonArray=JSONFactoryUtil.createJSONArray();
		JSONObject resultJson= JSONFactoryUtil.createJSONObject();
		try{
			CustomerRequest customerRequest = new CustomerRequest()
				    .firstName(customer.getFirstName())
				    .lastName(customer.getLastName())
				     .email(customer.getEmailAddress());
			Result<Customer>  customerResult=gateway.customer().update(customer.getCustomerId(), customerRequest);
			String message=customerResult.getMessage();
			if(customerResult.isSuccess()){
				LOG.debug(customerResult.isSuccess()+"success");
				resultJson.put("id",customerResult.getTarget().getId() );
				resultJson.put("message", RestConstant.CUSTOMER_UPDATED);
			}
			else{
				LOG.debug(customerResult.isSuccess()+"failure");
				resultJson.put("id", "");
				resultJson.put("message",message+RestConstant.CUSTOMER_NOT_UPDATED);
			}
			resultJsonArray.put(resultJson);
			LOG.info("Exit: Update customer method");
			return resultJsonArray.toString();
		}
		catch(NotFoundException e){
			return RestConstant.CUSTOMER_NOT_FOUND;
		}
		
	}
	
	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param user model
	 * This methos is used to create liferay users as customer
	 * @return
	 * @throws PortalException
	 */
	public static String createLiferayUserAsCustomer(BraintreeGateway gateway,User user) throws PortalException{
		LOG.info("Entry: Create liferay user as customer method");
		JSONArray resultJsonArray=JSONFactoryUtil.createJSONArray();
		JSONObject resultJson= JSONFactoryUtil.createJSONObject();
		CustomerRequest request = new CustomerRequest()
			    .firstName(user.getFirstName())
			    .lastName(user.getLastName())
			     .email(user.getEmailAddress())
			    .id(String.valueOf(user.getUserId()));
			Result<Customer> customerResult = gateway.customer().create(request);
			String message=customerResult.getMessage();
			if(customerResult.isSuccess()){
				LOG.debug(customerResult.isSuccess()+"success");
				resultJson.put("id",customerResult.getTarget().getId() );
				resultJson.put("message", RestConstant.CUSTOMER_CREATED);
			}
			else{
				LOG.debug(customerResult.isSuccess()+"failure");
				resultJson.put("id", "");
				resultJson.put("message",message+RestConstant.CUSTOMER_NOT_CREATED);
			}
			resultJsonArray.put(resultJson);
			LOG.info("Exit: Create liferay user as customer method");
			return resultJsonArray.toString();
		}
	
	/**
	 * @author nirali
	 * @param Briantreegateway object
	 * This method is used to get braintree customers list
	 * @return
	 */
	public static String getCustomers(BraintreeGateway gateway){
		LOG.info("Entry: get customers method");
		ResourceCollection<Customer> customers =gateway.customer().all();
		JSONArray customerJsonArray=JSONFactoryUtil.createJSONArray();
		
		for(Customer customer:customers){
			JSONObject customerJsonObject=JSONFactoryUtil.createJSONObject();
			LOG.debug(customer.getFirstName()+""+customer.getId());
			customerJsonObject.put("firstName", customer.getFirstName());
			customerJsonObject.put("lastName", customer.getLastName());
			customerJsonObject.put("customerId", customer.getId());
			customerJsonObject.put("emailAddress", customer.getEmail());
			customerJsonArray.put(customerJsonObject);
		}
		LOG.info("Exit: get customers method");
		return customerJsonArray.toString();
		
	}
	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param customer email
	 * @param customer firstName
	 * This method is used to search customer by emailaddress , firstname
	 * Email Address is required.Firstname is optional 
	 * @return
	 */
	public static String searchCustomer(BraintreeGateway gateway,String email,String firstName){
		LOG.info("Entry: Search customer method");
		CustomerSearchRequest searchRequest=null;
		if(firstName == null){
			searchRequest=new CustomerSearchRequest().email().is(email);
		}
		else{
			searchRequest=new CustomerSearchRequest().email().is(email).firstName().is(firstName);
		}
		ResourceCollection<Customer> customers = gateway.customer().search(searchRequest);
		JSONArray customerJsonArray=JSONFactoryUtil.createJSONArray();
		for (Customer customer : customers) {
			JSONObject jsonObject=JSONFactoryUtil.createJSONObject();
			jsonObject.put("customerId", customer.getId());
			jsonObject.put("firstName", customer.getFirstName());
			jsonObject.put("lastName", customer.getLastName());
			jsonObject.put("emailAddress", email);
			customerJsonArray.put(jsonObject);
		}
		LOG.info("Exit: Search customer method");
		return customerJsonArray.toString();
	}
	/**
	 * @author nirali
	 * @param Briantreegateway object
	 * @param customerId
	 * This method is used to delete braintree customer
	 * @return
	 */
	public static String deleteCustomer(BraintreeGateway gateway,String customerId){
		LOG.info("Entry: Delete customer method");
		try{
			gateway.customer().delete(customerId);
			return RestConstant.CUSTOMER_DELETED;
		}
		catch(NotFoundException e){
			return RestConstant.CUSTOMER_NOT_FOUND;
		}
	}


	
	/**
	 * @author nirali
	 * @param customerId
	 * @param Braintreegateway object
	 * This methodis used to get customer with specified customerid
	 * @return
	 */
	public static String getCustomer(String customerId,BraintreeGateway gateway){
		LOG.info("Entry: Get customer method");
		Customer customer= gateway.customer().find(customerId);
		JSONObject customerJson=JSONFactoryUtil.createJSONObject();
		customerJson.put("id", customerId);
		customerJson.put("firstName", customer.getFirstName());
		customerJson.put("lastName", customer.getLastName());
		customerJson.put("emailAddress", customer.getEmail());
		LOG.info("Exit: Get customer method");
		return customerJson.toString();
		
	}
	

	
	/**
	 * @author nirali
	 * This method is used to get liferay users
	 * @return
	 * @throws PortalException
	 */
	public static String getLiferayUsers() throws PortalException{
		LOG.info("Entry: Get liferay users method");
		java.util.List<User> userList=UserLocalServiceUtil.getUsers(-1, -1);
		JSONArray userJsonArray=JSONFactoryUtil.createJSONArray();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		for(User user:userList){
			if(user.isActive() && !user.getEmailAddress().equalsIgnoreCase(RestConstant.DEFAULT_LIFERAY_USER)){
				JSONObject userJsonObject=new JSONFactoryUtil().createJSONObject();
				userJsonObject.put("userId", user.getUserId());
				userJsonObject.put("firstname", user.getFirstName());
				userJsonObject.put("lastName", user.getLastName());
				userJsonObject.put("dateOfBirth", dateFormat.format(user.getBirthday()));
				userJsonObject.put("emailAddress", user.getEmailAddress());
				userJsonArray.put(userJsonObject);
			}
		}
		LOG.info("Exit: Get liferay users method");
		return userJsonArray.toString();
	}
	
}

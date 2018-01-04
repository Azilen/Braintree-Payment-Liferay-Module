package com.azilen.payment.rest.braintree.service;

import com.azilen.payment.rest.braintree.model.CustomerModel;
import com.azilen.payment.rest.braintree.model.MerchantModel;
import com.azilen.payment.rest.braintree.model.TransactionModel;
import com.azilen.payment.rest.braintree.service.impl.BraintreePaymentRestServiceImpl;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.exceptions.NotFoundException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.xml.ws.RequestWrapper;

@Path("braintree")
public class BraintreePaymentService {
	
	/**
	 * Braintreegateway object
	 */
	public static BraintreeGateway braintreeGateway;
	/**
	 * Logger for BraintreePaymentService class 
	 */
	private static Log LOG = LogFactoryUtil.getLog(BraintreePaymentService.class);
	
	/**
	 * @author nirali
	 * This method is used to get keys required for integration with braintree 
	 * @return
	 */
	public  BraintreePaymentService(){
		LOG.debug("BraintreePaymentServiceImpl Constructor");
		braintreeGateway =BraintreePaymentRestServiceImpl.getBraintreeGateway();
	}
	
	/**
	 * @author nirali
	 * This method is used to get braintreegateway object
	 * @return
	 * 
	 */
	@GET
    @Path("/getBraintreeGatewayObject")
    @Produces("text/text")
    public BraintreeGateway getBraintreeObject() {
        return braintreeGateway;
    }
	
	/**
	 * @author nirali
	 * @param BrainTreeGateway object
	 * This method is used to generate clienttoken for loading dropin UI
	 * @return
	 */
	@GET
    @Path("/checkout")
    @Produces("text/text")
    public String generateToken() {
        return BraintreePaymentRestServiceImpl.generateTokenForDropInUI(braintreeGateway);
    }
    
	/**
	 * @author nirali
	 * @param transaction model
	 * This method is used to create transaction
	 * @return
	 */
    @POST
    @Path("/transaction/create")
    @Produces("text/text")
    public String createBraintreeTransaction(@FormParam("amount") String amount, @FormParam("subMerchant") String merchant,
			@FormParam("payment_method_nonce") String nonce,@FormParam("customerId") String customerId,@FormParam("serviceFee") String serviceFee) { 	
    	TransactionModel requestParameter=new TransactionModel();
    	requestParameter.setAmount(amount);
    	requestParameter.setMerchant(merchant);
    	requestParameter.setPayment_method_nonce(nonce);
    	requestParameter.setEscrowcheck(false);
    	requestParameter.setUserId(customerId);
    	requestParameter.setServiceFee(new BigInteger(serviceFee));
       String result=BraintreePaymentRestServiceImpl.submitForm(braintreeGateway,requestParameter);
    	return result;
    }
    
	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param customerId
	 * This method is used to serach transaction by customer
	 * @return
	 */
    @GET
    @Path("/transaction/searchByCustomer/{customerId}")
    @Produces("text/text")
    public String searchBraintreeTransactionByCustomerId(@PathParam("customerId") String customerId) {
        return BraintreePaymentRestServiceImpl.searchTransactionByCustomer(braintreeGateway,customerId);
    }
	
	/**
	 * @author nirali
	 * @param Braintreegateway Object
	 * @param transactionId
	 * This method is used to search transaction by specified transaction id
	 * @return
	 */
	@GET
    @Path("/transaction/search/{transactionId}")
    @Produces("text/text")
    public String searchBraintreeTransaction(@PathParam("transactionId") String transactionId) {
        return BraintreePaymentRestServiceImpl.searchTransaction(braintreeGateway,transactionId);
    }
	
	
	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param transactionId
	 * This method is used to cancel transaction
	 * @return
	 */
	@GET
    @Path("/transaction/cancel/{transactionId}")
    @Produces("text/text")
    public String cancelBraintreeTransaction(@PathParam("transactionId") String transactionId) { 
        return BraintreePaymentRestServiceImpl.cancelTransaction(braintreeGateway,transactionId );
    }
    
	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param merchant model
	 * This method is used to create sub merchant
	 * @return
	 */
    @POST
    @Path("/merchant/create")
    @Produces("text/text")
    public String createBraintreeSubMerchant(@FormParam("subMerchantId") String subMerchantId, @FormParam("firstName") String firstName,@FormParam("lastName") String lastName,
    		@FormParam("emailAddress") String emailAddress,@FormParam("phoneno") String phoneno,
    		@FormParam("dateOfBirth") String dateOfBirth,@FormParam("ssn") String ssn,@FormParam("locality") String locality,
    		@FormParam("postalCode") String postalCode,@FormParam("region") String region,
    		@FormParam("streetAddress") String streetAddress,@FormParam("masterMerchantAccountId") String masterMerchantAccountId,
    		@FormParam("routingNumber") String routingNumber,@FormParam("accountNumber") String accountNumber) {
    	
    	MerchantModel merchant=new MerchantModel();
    	merchant.setFirstName(firstName);
    	merchant.setLastname(lastName);
    	merchant.setDateOfBirth(dateOfBirth);
    	merchant.setEmailAddress(emailAddress);
    	merchant.setPhoneNo(phoneno);
    	merchant.setSsn(ssn);
    	merchant.setLocality(locality);
    	merchant.setPostalCode(postalCode);
    	merchant.setRegion(region);
    	merchant.setStreetAddress(streetAddress);
    	merchant.setMasterMerchantAccountId(masterMerchantAccountId);
    	merchant.setRoutingNumber(routingNumber);
    	merchant.setAccountNumber(accountNumber);
    	merchant.setSubMerchantId(subMerchantId);
        return BraintreePaymentRestServiceImpl.createMerchant(braintreeGateway,merchant);
    }
    
    /**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param merchant model
	 * This methos is used to update braintree submerchant 
	 * @return
	 */
	@POST
	@Path("/merchant/update/{subMerchantId}")
	@Produces("text/text")
	public String updateBraintreeSubMerchant(@PathParam("subMerchantId") String subMerchantId, @FormParam("firstName") String firstName,@FormParam("lastName") String lastName,
    		@FormParam("emailAddress") String emailAddress,@FormParam("phoneno") String phoneno,
    		@FormParam("dateOfBirth") String dateOfBirth,@FormParam("ssn") String ssn,@FormParam("locality") String locality,
    		@FormParam("postalCode") String postalCode,@FormParam("region") String region,
    		@FormParam("streetAddress") String streetAddress,@FormParam("masterMerchantAccountId") String masterMerchantAccountId,
    		@FormParam("routingNumber") String routingNumber,@FormParam("accountNumber") String accountNumber) {
		MerchantModel merchant=new MerchantModel();
		merchant.setSubMerchantId(subMerchantId);
		merchant.setFirstName(firstName);
		merchant.setLastname(lastName);
		merchant.setEmailAddress(emailAddress);
		merchant.setPhoneNo(phoneno);
		merchant.setDateOfBirth(dateOfBirth);
		merchant.setSsn(ssn);
		merchant.setLocality(locality);
		merchant.setPostalCode(postalCode);
		merchant.setRegion(region);
		merchant.setStreetAddress(streetAddress);
		merchant.setAccountNumber(accountNumber);
		merchant.setRoutingNumber(routingNumber);
		merchant.setMasterMerchantAccountId(masterMerchantAccountId);
	    return BraintreePaymentRestServiceImpl.updateMerchant(braintreeGateway, merchant);
	}
    
	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param user model
	 * @param merchant model
	 * This method is used to create liferay user as submerchant
	 * @return
	 */
    @POST
	@Path("/merchant/liferayuser/{merchantId}")
	@Produces("text/text")
	public String createLiferayUserAsBraintreeSubMerchant(@PathParam("merchantId") String merchantId,@FormParam("phoneno") String phoneno,
    		@FormParam("dateOfBirth") String dateOfBirth,@FormParam("ssn") String ssn,@FormParam("locality") String locality,
    		@FormParam("postalCode") String postalCode,@FormParam("region") String region,
    		@FormParam("streetAddress") String streetAddress,@FormParam("masterMerchantAccountId") String masterMerchantAccountId,
    		@FormParam("routingNumber") String routingNumber,@FormParam("accountNumber") String accountNumber) throws PortalException {
	    MerchantModel merchant=new MerchantModel();
	    merchant.setDateOfBirth(dateOfBirth);
	    merchant.setPhoneNo(phoneno);
    	merchant.setSsn(ssn);
    	merchant.setLocality(locality);
    	merchant.setPostalCode(postalCode);
    	merchant.setRegion(region);
    	merchant.setStreetAddress(streetAddress);
    	merchant.setMasterMerchantAccountId(masterMerchantAccountId);
    	merchant.setRoutingNumber(routingNumber);
    	merchant.setAccountNumber(accountNumber);
    	User user=UserLocalServiceUtil.getUser(Long.parseLong(merchantId));
    	return BraintreePaymentRestServiceImpl.createLiferayUserAsMerchant(braintreeGateway,user,merchant);
	}
    
    /**
	 * @author nirali
	 * @param Braintreegateway object
	 * This methd is used to get list of braintree submerchants
	 * @return
	 */
    @GET
	@Path("/merchant/list")
	@Produces("text/text")
	public String getBraintreeSubMerchants() {
	    return BraintreePaymentRestServiceImpl.getMerchants(braintreeGateway);
	}
    
    /**
	 * @author nirali
	 * @param Braintreegateway object
	 * This methd is used to search braintree submerchant by email address
	 * @return
	 */
    @GET
	@Path("/merchant/search/{emailAddress}")
	@Produces("text/text")
	public String searchBriaintreeSubMerchant(@PathParam("emailAddress") String email) {
	    return BraintreePaymentRestServiceImpl.searchMerchant(braintreeGateway,email);
	}
    
    /**
	 * @author nirali
	 * @param merchantId
	 * @param Braintreegateway object
	 * This method is used to get submerchant with specified submerchant id
	 * @return
	 */
	@GET
	@Path("/merchant/find/{merchantId}")
	public String getBraintreeMerchant(@PathParam("merchantId") String merchantId){
		return BraintreePaymentRestServiceImpl.getSubMerchant(merchantId, braintreeGateway);
	}
	
	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param customer
	 * This method is used to create customer
	 * @return
	 */
    @POST
    @Path("/customer/create")
    @Produces("text/text")
    public String createBraintreeCustomer(@FormParam("customerId") String customerId,@FormParam("firstName") String firstName,
    		@FormParam("lastName") String lastName,@FormParam("emailAddress") String emailAddress) {
		CustomerModel customer=new CustomerModel();
		customer.setCustomerId(customerId);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setEmailAddress(emailAddress);
        return BraintreePaymentRestServiceImpl.createCustomer(braintreeGateway,customer);
    }
    
	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param customer model
	 * This method is used to update the customer
	 * @return
	 */
	@POST
	@Path("/customer/update/{customerId}")
	@Produces("text/text")
	public String updateBraintreeCustomer(@FormParam("firstName") String firstName,
    		@FormParam("lastName") String lastName,@FormParam("emailAddress") String emailAddress,@PathParam("customerId") String customerId) {
		CustomerModel customer=new CustomerModel();
		customer.setCustomerId(customerId);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setEmailAddress(emailAddress);
	    return BraintreePaymentRestServiceImpl.updateCustomer(braintreeGateway, customer);
	}
	
	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param user model
	 * This methos is used to create liferay users as customer
	 * @return
	 * @throws PortalException
	 */
	@POST
	@Path("/customer/liferayuser/{customerId}")
	@Produces("text/text")
	public String createLiferayUserAsBraintreeCustomer(@PathParam("customerId") String customerId) throws PortalException {
		User user=UserLocalServiceUtil.getUser(Long.parseLong(customerId));
		return BraintreePaymentRestServiceImpl.createLiferayUserAsCustomer(braintreeGateway,user);
	}
	
	/**
	 * @author nirali
	 * @param Briantreegateway object
	 * This method is used to get braintree customers list
	 * @return
	 */
	@GET
	@Path("/customer/list")
	@Produces("text/text")
	public String getBraintreeCustomers() {
	    return BraintreePaymentRestServiceImpl.getCustomers(braintreeGateway);
	}
	
	/**
	 * @author nirali
	 * @param Briantreegateway object
	 * @param customerId
	 * This method is used to delete braintree customer
	 * @return
	 */
	@GET
	@Path("/customer/delete/{customerId}")
	@Produces("text/text")
	public String deleteBraintreeCustomer(@PathParam("customerId") String customerId) {
	    return BraintreePaymentRestServiceImpl.deleteCustomer(braintreeGateway,customerId);
	}
	
	/**
	 * @author nirali
	 * @param customerId
	 * @param Braintreegateway object
	 * This methodis used to get customer with specified customerid
	 * @return
	 */
	@GET
	@Path("/customer/find/{customerId}")
	public String getBraintreeCustomer(@PathParam("customerId") String customerId){
		return BraintreePaymentRestServiceImpl.getCustomer(customerId, braintreeGateway);
	}

	/**
	 * @author nirali
	 * @param Braintreegateway object
	 * @param customer email
	 * @param customer firstName
	 * This method is used to search customre by emailaddress , firstname
	 * Email Address is required.Firstname is optional 
	 * @return
	 */
	@GET
	@Path("/customer/search/{email}{p:/*}{firstName:([0-9_-]*)}")
	@Produces("text/text")
	public String searchBraintreeCustomer(@PathParam("email") String email,@PathParam("firstName") String firstName) {
	    return BraintreePaymentRestServiceImpl.searchCustomer(braintreeGateway,email,firstName);
	}

	/**
	 * @author nirali
	 * This method is used to get liferay users
	 * @return
	 * @throws PortalException
	 */
	@GET
	@Path("/liferay/users/list")
	public String getLiferayUsers(){
		try {
			return BraintreePaymentRestServiceImpl.getLiferayUsers();
		} catch (PortalException e) {
			LOG.info("Exception"+e);
		}
		return null;
	}
	 
}

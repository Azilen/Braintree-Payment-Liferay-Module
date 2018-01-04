package com.azilen.payment.rest.controller;

import com.azilen.payment.rest.braintree.service.BraintreePaymentService;
import com.braintreegateway.BraintreeGateway;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.osgi.service.component.annotations.Component;

@ApplicationPath("payment")
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=braintree-payment-rest-api Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Application.class
)
public class PaymentRestController extends Application {
	
	//public static BraintreeGateway gateway;
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<Object>();
		// add our REST endpoints (resources)
		singletons.add(new BraintreePaymentService());
		return singletons;
    }
	
}
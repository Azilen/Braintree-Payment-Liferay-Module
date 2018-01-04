package com.azilen.payment.integration.controller;

import com.azilen.payment.integration.action.command.PaymentActionCommand;
import com.braintreegateway.BraintreeGateway;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=BrainTree_Payment_Integration Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		 "javax.portlet.init-param.config-template=/config.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"com.liferay.portlet.header-portlet-css=/css/payment.css"
	},
	service = Portlet.class
)
public class PaymentMethodController extends MVCPortlet {
	private static Log LOG = LogFactoryUtil.getLog(PaymentMethodController.class);
	@Override
	public void doView(RenderRequest renderRequest,RenderResponse renderResponse){
		LOG.info("Entry:render method of PaymentMethodController");
		try {
			super.doView(renderRequest, renderResponse);
		} catch (Exception e) {
			e.printStackTrace();
	}
		LOG.info("Exit:render method of PaymentMethodController");
}
}
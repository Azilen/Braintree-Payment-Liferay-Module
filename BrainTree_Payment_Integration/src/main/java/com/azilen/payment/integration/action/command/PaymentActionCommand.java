package com.azilen.payment.integration.action.command;

import com.azilen.payment.integration.util.Constant;
import com.azilen.payment.rest.braintree.service.impl.BraintreePaymentRestServiceImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
@Component(
	    immediate = true,
	    property = {
	       "javax.portlet.name=" + Constant.PORTLET_NAME,
	       "mvc.command.name=actionCommand1",
	    },
	    service = MVCActionCommand.class
)
public class PaymentActionCommand implements MVCActionCommand{

	private static Log LOG = LogFactoryUtil.getLog(PaymentActionCommand.class);
	/* 
	 * @author nirali
	 * process action method for redirect to payment page
	 */
	@Override
	public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {
		LOG.info("Entry:Inside processAction method for PaymentActionCommand");
		String amount = (String) actionRequest.getParameter("amount");
		String clientToken=actionRequest.getParameter("clientToken");
		LOG.debug("amount"+amount);
		actionRequest.setAttribute("amount", amount);
		actionRequest.setAttribute("clientToken", clientToken);
		actionResponse.setRenderParameter("jspPage", "/payment.jsp");
		LOG.info("Exit:Inside processAction method for PaymentActionCommand");
		return false;
	}

}

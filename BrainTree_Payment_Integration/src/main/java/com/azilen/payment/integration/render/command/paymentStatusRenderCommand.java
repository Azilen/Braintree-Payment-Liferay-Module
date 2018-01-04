package com.azilen.payment.integration.render.command;

import com.azilen.payment.integration.util.Constant;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

@Component(
		  immediate = true,
				    property = {
				       "javax.portlet.name=" + Constant.PORTLET_NAME,
				       "mvc.command.name=paymentStatus"
				    },
				    service =MVCRenderCommand.class
	)
public class PaymentStatusRenderCommand implements MVCRenderCommand{

	private static Log LOG = LogFactoryUtil.getLog(PaymentStatusRenderCommand.class);
	/* 
	 * @author nirali
	 * render method for redirecting to payment status page
	 */
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		LOG.info("Entry:payment status render method");
		String message= renderRequest.getParameter("message");
		renderRequest.setAttribute("message", message);
		LOG.info("Exit:payment status render method");
		return "/paymentStatus.jsp";
	}

}

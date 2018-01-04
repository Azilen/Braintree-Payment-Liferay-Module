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
			       "mvc.command.name=createOrder",
			       "mvc.command.name=createCustomer",
			       "mvc.command.name=customerList",
			       "mvc.command.name=createLRCustomer",
			       "mvc.command.name=createLRSubMerchant",
			       "mvc.command.name=merchantList",
			       "mvc.command.name=view"
			    },
			    service =MVCRenderCommand.class
)
public class paymentRenderCommand implements MVCRenderCommand {

	private static Log LOG = LogFactoryUtil.getLog(paymentRenderCommand.class);
	/* 
	 * @author nirali
	 * render method for redirecting to different page based on condition
	 */
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		// TODO Auto-generated method stub
		LOG.info("Entry:create order render command method ");
		String page= (String)renderRequest.getParameter("page");
		if(page==null || page.isEmpty()){
			return "/order.jsp";
		}
		else if(page.equals("customerList")){
			return "/customerList.jsp";
		}
		else if(page.equals("createLRUserAsCustomer")){
			return "/createLiferayUserAsCustomer.jsp";
		}
		else if(page.equals("createLRUserAsSubMerchant")){
			return "/createLiferayUserAsSubmerchant.jsp";
		}
		else if(page.equals("merchantList")){
			return "/merchantList.jsp";
		}
		else{
			return "/view.jsp";
		}
	}
}

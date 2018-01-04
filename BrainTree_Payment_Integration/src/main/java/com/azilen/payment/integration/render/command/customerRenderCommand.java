package com.azilen.payment.integration.render.command;

import com.azilen.payment.integration.model.Customer;
import com.azilen.payment.integration.util.Constant;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.osgi.service.component.annotations.Component;

@Component(
		  immediate = true,
				    property = {
				       "javax.portlet.name=" + Constant.PORTLET_NAME,
				       "mvc.command.name=createCustomer",
				       "mvc.command.name=updateCustomer",
				       "mvc.command.name=createLRUserAsCustomer"
				    },
				    service =MVCRenderCommand.class
	)
public class CustomerRenderCommand implements MVCRenderCommand {

	private static Log LOG = LogFactoryUtil.getLog(CustomerRenderCommand.class);
	/* 
	 * @author nirali
	 * render method for redirecting to create customer page
	 */
	public String render(RenderRequest renderRequest,RenderResponse renderResponse){
		LOG.info("Entry:customer render command method");
		String renderCommandName=renderRequest.getParameter("mvcRenderCommandName");
			String details= renderRequest.getParameter("customerDetails");
			if(details != null){
				org.json.JSONObject obj=new org.json.JSONObject(details);
				String customerId= obj.getString("id");
				String firstName= obj.getString("firstName");
				String lastName= obj.getString("lastName");
				String email=obj.getString("emailAddress");
				Customer customer=new Customer();
				customer.setCustomerId(customerId);
				customer.setEmailAddress(email);
				customer.setFirstName(firstName);
				customer.setLastName(lastName);
				LOG.info(customerId+"customerId");
				renderRequest.setAttribute("customerDetails", customer);
				renderRequest.setAttribute("renderCommand", renderCommandName);
			}
			else{
				renderRequest.setAttribute("customerDetails", null);
				renderRequest.setAttribute("renderCommand", renderCommandName);
			}
			LOG.info("Exit:customer render command method");
			return "/createCustomer.jsp";
	}
}

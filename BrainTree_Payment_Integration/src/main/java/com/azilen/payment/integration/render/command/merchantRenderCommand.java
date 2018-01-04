package com.azilen.payment.integration.render.command;

import com.azilen.payment.integration.action.command.PaymentActionCommand;
import com.azilen.payment.integration.util.Constant;
import com.braintreegateway.Merchant;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.StringPool;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
@Component(
		immediate = true,
	    property = {
	       "javax.portlet.name=" + Constant.PORTLET_NAME,
	       "mvc.command.name=createSubMerchant",
	       "mvc.command.name=updateMerchant",
	       "mvc.command.name=createLRUserAsSubMerchant"
	    },
	    service =MVCRenderCommand.class
)
public class merchantRenderCommand implements MVCRenderCommand{

	private static Log LOG = LogFactoryUtil.getLog(PaymentActionCommand.class);
	/* 
	 * @author nirali
	 * render method for redirecting to create merchant page
	 */
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		LOG.info("Entry: merchantRenderCommand method");
		String renderCommandName=renderRequest.getParameter("mvcRenderCommandName");
		com.azilen.payment.integration.model.Merchant merchant=new com.azilen.payment.integration.model.Merchant(); 
		String details= renderRequest.getParameter("merchantDetails");
		if(details != null){
				if(renderCommandName.equalsIgnoreCase("updateMerchant")){
					org.json.JSONObject obj=new org.json.JSONObject(details);
					merchant.setSubMerchantId(obj.getString("id"));
					merchant.setFirstName(obj.getString("firstName"));
					merchant.setLastname(obj.getString("lastName"));
					merchant.setEmailAddress(obj.getString("emailAddress"));
					merchant.setDateOfBirth(obj.getString("dateOfBirth"));
					merchant.setLocality(obj.getString("locality"));
					merchant.setPostalCode(obj.getString("postalCode"));
					merchant.setStreetAddress(obj.getString("streetAddress"));
					merchant.setRegion(obj.getString("region"));
					merchant.setPhoneNo(obj.getString("phoneNumber"));
					merchant.setSsn(obj.getString("ssn"));
					merchant.setAccountNumber(obj.getString("accountNumber"));
					merchant.setRoutingNumber(obj.getString("routingNumber"));
					merchant.setMasterMerchantAccountId(obj.getString("master_merchant_account"));
					renderRequest.setAttribute("merchantDetails", merchant);
					renderRequest.setAttribute("renderCommand", renderCommandName);
				}   
				else if(renderCommandName.equalsIgnoreCase("createLRUserAsSubMerchant")){
					org.json.JSONObject obj=new org.json.JSONObject(details);
					merchant.setSubMerchantId(obj.getString("id"));
					merchant.setFirstName(obj.getString("firstName"));
					merchant.setLastname(obj.getString("lastName"));
					merchant.setEmailAddress(obj.getString("emailAddress"));
					merchant.setDateOfBirth(obj.getString("dateOfBirth"));
					merchant.setLocality(StringPool.BLANK);
					merchant.setPostalCode(StringPool.BLANK);
					merchant.setStreetAddress(StringPool.BLANK);
					merchant.setRegion(StringPool.BLANK);
					merchant.setPhoneNo(StringPool.BLANK);
					merchant.setSsn(StringPool.BLANK);
					merchant.setAccountNumber(StringPool.BLANK);
					merchant.setRoutingNumber(StringPool.BLANK);
					merchant.setMasterMerchantAccountId(StringPool.BLANK);
					renderRequest.setAttribute("merchantDetails", merchant);
					renderRequest.setAttribute("renderCommand", renderCommandName);
				}
		}
		else{
			renderRequest.setAttribute("merchantDetails", null);
			renderRequest.setAttribute("renderCommand", renderCommandName);
		}
		LOG.info("Exit: merchantRenderCommand method");
		return "/createSubMerchant.jsp";
	}
	
}

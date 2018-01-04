package com.azilen.payment.integration.configuration;

import com.azilen.payment.integration.util.Constant;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import java.util.Map;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

import aQute.bnd.annotation.metatype.Configurable;

@Component(
        configurationPid = Constant.CONFIGURATION_PID,
        configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
        property = {
                "javax.portlet.name="+Constant.PORTLET_ID
        },
        service = ConfigurationAction.class
    )
public class PaymentConfigurationAction extends DefaultConfigurationAction {
private volatile PaymentConfiguration  _paymentConfig;
	
	private static final Log _log = LogFactoryUtil.getLog(PaymentConfigurationAction.class);
	
	/**
	 * @author nirali
	 * Configuration process action method for store service fee in preference
	 */
	@Override
	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest,
							  ActionResponse actionResponse) throws Exception {
		String serviceFee=ParamUtil.getString(actionRequest, Constant.SERVICE_FEE);
		if(!serviceFee.isEmpty()){
			_log.debug("Environment is:"+serviceFee);
			javax.portlet.PortletPreferences preferences = actionRequest.getPreferences();
			preferences.setValue(PaymentConfiguration.SERVICE_FEE,serviceFee);
			preferences.store();
		}
		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		httpServletRequest.setAttribute(PaymentConfiguration.class.getName(),_paymentConfig);
		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<Object, Object> properties) {
		_paymentConfig = Configurable.createConfigurable(PaymentConfiguration.class, properties);
	}
}

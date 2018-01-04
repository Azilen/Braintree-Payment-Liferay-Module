package com.azilen.payment.integration.configuration;
import com.azilen.payment.integration.util.Constant;
import aQute.bnd.annotation.metatype.Meta;

/**
 * @author nirali
 * Configuration interface for set service fee in preference
 */
@Meta.OCD(id = Constant.CONFIGURATION_PID)
public interface PaymentConfiguration {
	public static final String SERVICE_FEE="serviceFee";
    
    @Meta.AD(	required = true)
    public String getServiceFee();
}	

<%@ include file="/init.jsp" %>
<liferay-portlet:renderURL var="createOrderURL" >
	<liferay-portlet:param name="mvcRenderCommandName" value="createOrder"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="createCustomerURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="createCustomer"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="createSubMerchantURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="createSubMerchant"/>
	<liferay-portlet:param name="page" value="createSubMerchant"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="customerListURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="customerList"/>
	<liferay-portlet:param name="page" value="customerList"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="createLRCustomerURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="createLRCustomer"/>
	<liferay-portlet:param name="page" value="createLRUserAsCustomer"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="createLRSubMerchantURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="createLRSubMerchant"/>
	<liferay-portlet:param name="page" value="createLRUserAsSubMerchant"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="merchantListURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="merchantList"/>
	<liferay-portlet:param name="page" value="merchantList"/>
</liferay-portlet:renderURL>

<a href ="${createOrderURL}">Shop Now</a><br/>
<a href ="${createCustomerURL}">Create Customer</a><br/>
<a href ="${createSubMerchantURL}">Create SubMerchant</a><br/>
<a href="${customerListURL}">Customers</a><br/>
<a href="${merchantListURL}">Merchants</a><br/>
<a href="${createLRCustomerURL}">Create LR User As Customer</a><br/>
<a href="${createLRSubMerchantURL}">Create LR User As SubMerchant</a><br/>
<%@ include file="/init.jsp" %>

<%
	String preferenceDefaultValue="-1";
	String environment=portletPreferences.getValue("environment", preferenceDefaultValue);
	String merchantId=portletPreferences.getValue("merchantId", preferenceDefaultValue);
	String publicKey= portletPreferences.getValue("publicKey", preferenceDefaultValue);
	String privateKey=portletPreferences.getValue("privateKey", preferenceDefaultValue);
	String serviceFee=portletPreferences.getValue("serviceFee", preferenceDefaultValue);
%>
<liferay-portlet:actionURL portletConfiguration="<%=true %>"
	var="configActionURL" />
	<aui:form  action="<%=configActionURL%>" method="post" name="fm" id="categoryForm">
   		<div class="form-group" >
		  <label for="serviceFee">Service Fee</label>
		 	 <input name="<portlet:namespace/>serviceFee" type="text" value="<%=serviceFee%>" id="serviceFee" class="serviceFee"/>
		 </div> 
   		<div>
   		<button type="submit" class='btn  submitButton  btn-default braintree-btn'>Save</button>
   		</div>
	 </aui:form>
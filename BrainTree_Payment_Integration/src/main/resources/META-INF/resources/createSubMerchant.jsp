<%@ include file="/init.jsp" %>
<h2>Create SubMerchant</h2>
<liferay-portlet:renderURL var="viewURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="view"/>
	<liferay-portlet:param name="page" value="view"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="merchantFormURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="createSubMerchant"/>
	<liferay-portlet:param name="page" value="createSubMerchant"/>
</liferay-portlet:renderURL>
<%
Merchant merchantDetails=null;
String formTask=StringPool.BLANK;
String url=StringPool.BLANK;
String urlFlag="false";
String renderCommand=(String)request.getAttribute("renderCommand");
	if(request.getAttribute("merchantDetails") != null){
		merchantDetails=(Merchant)request.getAttribute("merchantDetails");
	}
	if(renderCommand.equalsIgnoreCase("createSubMerchant")){
		formTask="Create Merchant";
		url="/o/rest/payment/braintree/merchant/create";
		urlFlag="true";
	}
	else if(renderCommand.equalsIgnoreCase("updateMerchant")){
		formTask="Update Merchant";
		url="/o/rest/payment/braintree/merchant/update/";
	}
	else{
		formTask="Create Liferay User As Merchant";
		url="/o/rest/payment/braintree/merchant/liferayuser/";
	}
%>
<div class="row">
<div class="col-sm-6">
<form id="subMerchantInfo"  method="post">
	<div class="form-group">
		 <div class="merchant-label-div">
		 	<label for="subMerchantId">Sub Merchant ID</label>
		 <span class="required-icon">*</span>
		 </div>
		  <%if(urlFlag.equalsIgnoreCase("true")) {%>
			<input type="text" name="subMerchantId" id="subMerchantId" value="<%=merchantDetails != null ? merchantDetails.getSubMerchantId() : ""%>"></input>
		<%}else{ %>
			<input type="text" name="subMerchantId" disabled="disabled" id="subMerchantId" value="<%=merchantDetails != null ? merchantDetails.getSubMerchantId() : ""%>"></input>
		<%} %><small  class="help-block form-text text-muted helpMerchantText">Use only letters, numbers, '-', and '_' for customer ID</small>
		<div class="error-msg required-message-subMerchantId required-message hidden">This field is required</div>
		<div class="error-msg subMerchantId-validation hidden">Merchant ID is invalid</div>
	</div>
	<div class="form-group">
		 <div class="merchant-label-div">
		 	<label for="firstname">FirstName</label>
		 	<span class="required-icon">*</span>
		 </div>
		<input type="text" name="firstName" value="<%=merchantDetails != null ? merchantDetails.getFirstName() : ""%>"></input>
		<div class="error-msg required-message-fname required-message hidden">This field is required</div>
	</div>
	<div class="form-group">
		<div class="merchant-label-div">
			<label for="lastName">LastName</label>
		<span class="required-icon">*</span>
		</div>
		<input type="text" name="lastName" value="<%=merchantDetails != null ? merchantDetails.getLastname() : ""%>"></input>
		<div class="error-msg required-message-lname required-message hidden">This field is required</div>
	</div>
	<div class="form-group">
		<div class="merchant-label-div">
			<label for="email">Email Address</label>
		<span class="required-icon">*</span>
		</div>
		<input type="text" name="emailAddress" value="<%=merchantDetails != null ? merchantDetails.getEmailAddress() : ""%>"></input>
		<div class="error-msg required-message-emailAddress required-message hidden">This field is required</div>
		<div class="error-msg email-validation hidden">Email Address is invalid</div>
	</div>
	<div class="form-group">
		<div class="merchant-label-div">
				<label for="phoneno">Phone Number</label>
		</div>
		<input type="text" name="phoneno" value="<%=merchantDetails != null ? merchantDetails.getPhoneNo() : ""%>"></input>
	</div>
	<div class="form-group">
		<div class="merchant-label-div">
			<label for="dateOfBirth">Date Of Birth</label>
		<span class="required-icon">*</span>
		</div>
		<input type="text" name="dateOfBirth" id="birthDate" value="<%=merchantDetails != null ? merchantDetails.getDateOfBirth() : ""%>"></input>
		<div id="datePickerdiv"></div>
		<div class="error-msg required-message-dateOfBirth required-message hidden">This field is required</div>
	</div>
	<div class="form-group">
		<div class="merchant-label-div">
			<label for="ssn">SSN</label>
		</div>
		<input type="text" name="ssn" value="<%=merchantDetails != null ? merchantDetails.getSsn() : ""%>"></input>
		<small  class="help-block form-text text-muted helpMerchantText">SSN must be blank, last 4 digits, or full 9 digits</small>
	</div>
	<div class="form-group">
		<div class="merchant-label-div">
			<label for="locality">Locality</label>
		<span class="required-icon">*</span>
		</div>
		<input type="text" name="locality" value="<%=merchantDetails != null ? merchantDetails.getLocality() : ""%>"></input>
		<small  class="help-block form-text text-muted helpMerchantText">Locality must be city, town, or municipality</small>
		<div class="error-msg required-message-locality required-message hidden">This field is required</div>
	</div>
	<div class="form-group">
		<div class="merchant-label-div">
			<label for="postalCode">Postal Code</label>
		<span class="required-icon">*</span>
		</div>
		<input type="text" name="postalCode" value="<%=merchantDetails != null ? merchantDetails.getPostalCode() : ""%>"></input>
		<div class="error-msg required-message-postalCode required-message hidden">This field is required</div>
		<div class="error-msg postalCode-validation hidden">Postal Code is invalid</div>
	</div>
	<div class="form-group">
		<div class="merchant-label-div">
			<label for="region">Region</label>
			<span class="required-icon">*</span>
		</div>
		<input type="text" name="region" value="<%=merchantDetails != null ? merchantDetails.getRegion() : ""%>"></input>
		<small class="help-block form-text text-muted helpMerchantText">Add state for region,Use Only two-letter abbreviations, e.g. 'CA' for 'California.'</small>
		<div class="error-msg required-message-region required-message hidden">This field is required</div>
	</div>
	<div class="form-group">
		<div class="merchant-label-div">
			<label for="streetAddress">Street Address</label>
		<span class="required-icon">*</span>
		</div>
		<input type="text" name="streetAddress" value="<%=merchantDetails != null ? merchantDetails.getStreetAddress() : ""%>"></input>
		<div class="error-msg required-message-streetAddress required-message hidden">This field is required</div>
	</div>
	<div class="form-group">
		<div class="merchant-label-div">
			<label for="masterMerchantAccountId">Master Merchant Account ID</label>
		<span class="required-icon">*</span>
		</div>
		<input type="text" name="masterMerchantAccountId" value="<%=merchantDetails != null ? merchantDetails.getMasterMerchantAccountId() : ""%>"></input>
		<div class="error-msg required-message-masterMerchantAccountId required-message hidden">This field is required</div>
	</div>
	<div class="form-group">
		<div class="merchant-label-div">
			<label for="routingNumber">Routing Number</label>
		<span class="required-icon">*</span>
		</div>
		<input type="text" name="routingNumber" value="<%=merchantDetails != null ? merchantDetails.getRoutingNumber() : ""%>"></input>
		<small  class="help-block form-text text-muted helpMerchantText">Routing Number must be numeric value representing specific bank</small>
		<div class="error-msg required-message-routingNumber required-message hidden">This field is required</div>
	</div>
	<div class="form-group">
		<div class="merchant-label-div">
			<label for="accountNumber">Account Number</label>
		<span class="required-icon">*</span>
		</div>
		<input type="text" name="accountNumber" value="<%=merchantDetails != null ? merchantDetails.getAccountNumber() : ""%>"></input>
		<div class="error-msg required-message-accountNumber required-message hidden">This field is required</div>
	</div>
	<button class="btn btn-default braintree-btn" id="merchant"><%=formTask %></button>
	<a  class="btn btn-default braintree-btn" id="cancel" href="${viewURL}">Cancel</a>
</form>
</div>
<div id="image" class="col-sm-6">
	<img class="merchant-info-img" src="<%=request.getContextPath()%>/img/information.jpg"/>
	<%if(formTask.equalsIgnoreCase("Create Merchant")){%>
	 <span class="form-info">
 	->Use to create submerchant in braintree<br/> 
	->API:-/o/rest/payment/braintree/merchant/create<br/>
				Parameter:-Form fields<br/>
				Response:-Json object containing message with SubMerchant ID
 	</span>
 	<span class="form-example">
    Example:-<br>
    Request:-SubMerchantID-122976,<br>
 			 &emsp;&emsp;&emsp;&emsp;&nbsp;FirstName-Joe,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;LastName-Doe,<br>
    	     &emsp;&emsp;&emsp;&emsp;&nbsp;EmailAddress-joe.doe@gmail.com,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;DateOfBirth-1995-09-17,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;Locality-San Fransisco,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;PostalCode-94016,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;Region-CA,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;Street Address-111 Main Street,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;MasterMerchantAccountID-master_merchant_account_id,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;RoutingNumber-071101307,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;AccountNumber-1123581321,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;SSN- 456-45-4567,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;Phone Number-5553334444<br>
    Response:-Submerchant Created successfully with ID:-122976<br>
    		&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;OR<br>
    		  &emsp;&emsp;&emsp;&emsp;&emsp;Submerchant can not be created with error message
</span>
<%}else if(formTask.equalsIgnoreCase("Update Merchant")){ %>
	 <span class="form-info">
 	->Use to create submerchant in braintree<br/> 
	->API:-/o/rest/payment/braintree/merchant/update<br/>
				Parameter:-Form fields<br/>
				Response:-Json object containing message with SubMerchant ID
 	</span>
 	<span class="form-example">
    Example:-<br>
    Request:-SubMerchantID-122976,<br>
 			 &emsp;&emsp;&emsp;&emsp;&nbsp;FirstName-Joe,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;LastName-Doe,<br>
    	     &emsp;&emsp;&emsp;&emsp;&nbsp;EmailAddress-joe.doe@gmail.com,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;DateOfBirth-1995-09-17,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;Locality-San Fransisco,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;PostalCode-94016,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;Region-CA,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;Street Address-111 Main Street,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;MasterMerchantAccountID-master_merchant_account_id,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;RoutingNumber-071101307,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;AccountNumber-1123581321,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;SSN- 456-45-4567,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;Phone Number-5553334444<br>
    Response:-Submerchant Updated successfully with ID:-122976<br>
    		&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;OR<br>
    		  &emsp;&emsp;&emsp;&emsp;&emsp;Submerchant can not be Updated with error message
</span>
<%}else{ %>
	 <span class="form-info">
 	->Use to create submerchant in braintree<br/> 
	->API:-/o/rest/payment/braintree/merchant/liferayuser<br/>
				Parameter:-Form fields<br/>
				Response:-Json object containing message with SubMerchant ID
 	</span>
 	<span class="form-example">
    Example:-<br>
    Request:-SubMerchantID-122976,<br>
 			 &emsp;&emsp;&emsp;&emsp;&nbsp;FirstName-Joe,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;LastName-Doe,<br>
    	     &emsp;&emsp;&emsp;&emsp;&nbsp;EmailAddress-joe.doe@gmail.com,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;DateOfBirth-1995-09-17,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;Locality-San Fransisco,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;PostalCode-94016,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;Region-CA,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;Street Address-111 Main Street,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;MasterMerchantAccountID-master_merchant_account_id,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;RoutingNumber-071101307,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;AccountNumber-1123581321,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;SSN- 456-45-4567,<br>
    		 &emsp;&emsp;&emsp;&emsp;&nbsp;Phone Number-5553334444<br>
    Response:-Submerchant Created successfully with ID:-122976<br>
    		&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;OR<br>
    		  &emsp;&emsp;&emsp;&emsp;&emsp;Submerchant can not be created with error message
</span>
<%} %>
</div>
</div>
<script>
                                  define._amd = define.amd;
                                  define.amd = false;
                          </script>
     <script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script> 
    <link href="http://code.jquery.com/ui/1.9.1/themes/smoothness/jquery-ui.css" rel="stylesheet">  
       
     <script>
                          define.amd = define._amd;
                          </script>
<script>
		
			$(function(){
			$("#birthDate").datepicker( {
				dateFormat:"yy-mm-dd",
					changeMonth: true,
			        changeYear: true	
			});
			});
			$(document).ready(function(){
var form=$("#subMerchantInfo");
form.submit(function(e){
		console.log("create submerchant");
		console.log(form.serialize());
		e.preventDefault();
		var flag=false;
		var subMerchantId=jQuery('input[name="subMerchantId"]').val();
		var firstName=jQuery('input[name="firstName"]').val();
		var lastName=jQuery('input[name="lastName"]').val();
		var emailAddress=jQuery('input[name="emailAddress"]').val();
		var phoneno=jQuery('input[name="phoneno"]').val();
		var dateOfBirth=jQuery('input[name="dateOfBirth"]').val();
		var ssn=jQuery('input[name="ssn"]').val();
		var locality=jQuery('input[name="locality"]').val();
		var postalCode=jQuery('input[name="postalCode"]').val();
		var region=jQuery('input[name="region"]').val();
		var streetAddress=jQuery('input[name="streetAddress"]').val();
		var masterMerchantAccountId=jQuery('input[name="masterMerchantAccountId"]').val();
		var routingNumber=jQuery('input[name="routingNumber"]').val();
		var accountNumber=jQuery('input[name="accountNumber"]').val();
		var merchantURL='<%=url%>';
		var merchantFormURL='${merchantFormURL}';
		if(<%=urlFlag.equalsIgnoreCase("false")%>){
			var subMerchantId=jQuery('input[name="subMerchantId"]').val();
			console.log(subMerchantId);
			merchantURL=merchantURL+subMerchantId;
		}
		flag=validateFields(subMerchantId,firstName,lastName,emailAddress,phoneno,dateOfBirth,ssn,locality,postalCode,region,streetAddress,masterMerchantAccountId,routingNumber,accountNumber);
		if(!flag){ 
				$.ajax({
				  		url: merchantURL,
				  		type: form.attr('method'),
				  		data:form.serialize(),
				  		success: function(data){
				  			console.log(data);
				  			  var list = jQuery.parseJSON(data);
				  			 console.log(list+"success");
				            $.each(list, function (index, item) {
				            		console.log(index+""+item.id+""+item.message);
				            		
				            		if(item.id != "0"){
				            			console.log("inside if");
				            			form.html(item.message+item.id+'<a class="back" href='+merchantFormURL+'>Back</a>');
				            		}
				            		else{
				            			console.log("inside else");
				            			form.html(item.message+item.id+'<a class="back" href='+merchantFormURL+'>Back</a>');
				            		}
				                 })  
						}
					}); 
			}		
	})
	function validateFields(subMerchantId,firstName,lastName,emailAddress,phoneno,dateOfBirth,ssn,locality,postalCode,region,streetAddress,masterMerchantAccountId,routingNumber,accountNumber){
			if(subMerchantId == ''){
				console.log("here");
				$(".required-message-subMerchantId").removeClass('hidden');
				$(".required-message-subMerchantId").addClass('show');
				$(".subMerchantId-validation").removeClass('show');
				$(".subMerchantId-validation").addClass('hidden');
				flag=true;
			}
			else{
				console.log("not here");
				$(".required-message-subMerchantId").removeClass('show');
				$(".required-message-subMerchantId").addClass('hidden');
				flag=false;
				//alert(flag);
				flag=validateMerchantId(subMerchantId,flag);
			}
			if(firstName == ''){
				 $(".required-message-fname").removeClass('hidden');
					$(".required-message-fname").addClass('show');
				flag=true;
			}
			else{
				$(".required-message-fname").removeClass('show');
				$(".required-message-fname").addClass('hidden');
				flag=false;
			}
			if(lastName == ''){
				$(".required-message-lname").removeClass('hidden');
				$(".required-message-lname").addClass('show');
				flag=true;
			}
			else{
				$(".required-message-lname").removeClass('show');
				$(".required-message-lname").addClass('hidden');
				flag=false;
			}
			 if(emailAddress == ''){
				$(".required-message-emailAddress").removeClass('hidden');
				$(".required-message-emailAddress").addClass('show');
				$(".email-validation").removeClass('show');
		        $(".email-validation").addClass('hidden');
				flag=true;
			}
			else{
				$(".required-message-emailAddress").removeClass('show');
				$(".required-message-emailAddress").addClass('hidden');
				flag=false;
				flag=validateEmail(flag,emailAddress);
			}  
			 if(phoneno == ''){
					$(".required-message-phoneno").removeClass('hidden');
					$(".required-message-phoneno").addClass('show');
					flag=true;
				}
				else{
					$(".required-message-phoneno").removeClass('show');
					$(".required-message-phoneno").addClass('hidden');
					flag=false;
					
				}  
			 if(dateOfBirth == ''){
					$(".required-message-dateOfBirth").removeClass('hidden');
					$(".required-message-dateOfBirth").addClass('show');
					flag=true;
				}
				else{
					$(".required-message-dateOfBirth").removeClass('show');
					$(".required-message-dateOfBirth").addClass('hidden');
					flag=false;
				}
			 if(locality == ''){
					$(".required-message-locality").removeClass('hidden');
					$(".required-message-locality").addClass('show');
					flag=true;
				}
				else{
					$(".required-message-locality").removeClass('show');
					$(".required-message-locality").addClass('hidden');
					flag=false;
				}
			 if(postalCode == ''){
					$(".required-message-postalCode").removeClass('hidden');
					$(".required-message-postalCode").addClass('show');
					$(".postalCode-validation").removeClass('show');
					$(".postalCode-validation").addClass('hidden');
					flag=true;
				}
				else{
					$(".required-message-postalCode").removeClass('show');
					$(".required-message-postalCode").addClass('hidden');
					flag=false;
					//flag=validatePostalCode(postalCode,flag);
				}
			 if(region == ''){
					$(".required-message-region").removeClass('hidden');
					$(".required-message-region").addClass('show');
					flag=true;
				}
				else{
					$(".required-message-region").removeClass('show');
					$(".required-message-region").addClass('hidden');
					flag=false;
				}
			 if(streetAddress == ''){
					$(".required-message-streetAddress").removeClass('hidden');
					$(".required-message-streetAddress").addClass('show');
					flag=true;
				}
				else{
					$(".required-message-streetAddress").removeClass('show');
					$(".required-message-streetAddress").addClass('hidden');
					flag=false;
				}
			 if(masterMerchantAccountId == ''){
					$(".required-message-masterMerchantAccountId").removeClass('hidden');
					$(".required-message-masterMerchantAccountId").addClass('show');
					flag=true;
				}
				else{
					$(".required-message-masterMerchantAccountId").removeClass('show');
					$(".required-message-masterMerchantAccountId").addClass('hidden');
					flag=false;
				}
			 if(routingNumber == ''){
					$(".required-message-routingNumber").removeClass('hidden');
					$(".required-message-routingNumber").addClass('show');
					flag=true;
				}
				else{
					$(".required-message-routingNumber").removeClass('show');
					$(".required-message-routingNumber").addClass('hidden');
					flag=false;
				}
			 if(accountNumber == ''){
					$(".required-message-accountNumber").removeClass('hidden');
					$(".required-message-accountNumber").addClass('show');
					flag=true;
				}
				else{
					$(".required-message-accountNumber").removeClass('show');
					$(".required-message-accountNumber").addClass('hidden');
					flag=false;
				}
			 
			 return flag;
	}
function validateMerchantId(subMerchantId,flag){
	var regex=/^[a-zA-Z0-9_-]*$/;
	if(regex.test(subMerchantId)){
		$(".subMerchantId-validation").removeClass('show');
		$(".subMerchantId-validation").addClass('hidden');
		flag=false;
	}
	else{
		$(".subMerchantId-validation").removeClass('hidden');
		$(".subMerchantId-validation").addClass('show');
		flag=true;
	}
	return flag;
}
function validatePostalCode(postalCode,flag){
	var regex=/^[a-zA-Z0-9_-]*$/;
	if(regex.test(postalCode)){
		$(".postalCode-validation").removeClass('show');
		$(".postalCode-validation").addClass('hidden');
		flag=false;
	}
	else{
		$(".postalCode-validation").removeClass('hidden');
		$(".postalCode-validation").addClass('show');
		flag=true;
	}
	return flag;
}

	function validateEmail(flag,emailAddress){
		 var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		    if (filter.test(emailAddress)) {
		    	$(".email-validation").removeClass('show');
		        $(".email-validation").addClass('hidden');
		        flag=false;
		    }
		    else {
		    	$(".email-validation").removeClass('hidden');
		        $(".email-validation").addClass('show');
		        flag=true;
		     }   
		    return flag;
	}
		});
</script>
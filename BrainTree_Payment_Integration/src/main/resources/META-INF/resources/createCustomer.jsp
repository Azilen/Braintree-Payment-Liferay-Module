<%@ include file="/init.jsp" %>
<h2>Create Customer</h2>
<liferay-portlet:renderURL var="customerFormURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="createCustomer"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="viewURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="view"/>
	<liferay-portlet:param name="page" value="view"/>
</liferay-portlet:renderURL>
<%
Customer customerDetails=null;
String formTask=StringPool.BLANK;
String url=StringPool.BLANK;
String urlFlag="false";
String renderCommand=(String)request.getAttribute("renderCommand");
	if(request.getAttribute("customerDetails") != null){
		customerDetails=(Customer)request.getAttribute("customerDetails");
	}
	if(renderCommand.equalsIgnoreCase("createCustomer")){
		formTask="Create Customer";
		url="/o/rest/payment/braintree/customer/create";
		urlFlag="true";
	}
	else if(renderCommand.equalsIgnoreCase("updateCustomer")){
		formTask="Update Customer";
		url="/o/rest/payment/braintree/customer/update/";
	}
	else{
		formTask="Create Liferay User As Customer";
		url="/o/rest/payment/braintree/customer/liferayuser/";
	}
%>
<div class="row">
<div class="col-sm-6">
<form id="customerInfo"  method="post">
	<div class="form-group" >
		<div class="label-div">
			<label for="Customer ID">Customer ID</label>
			<span class="required-icon">*</span>
		</div> 
		 <%if(urlFlag.equalsIgnoreCase("true")) {%>
		 	<input type="text"  name="customerId" id="customerId" value="<%=customerDetails != null ? customerDetails.getCustomerId() : ""%>"></input>
		 <%}else{ %>
		 	<input type="text"  name="customerId" id="customerId" disabled="disabled" value="<%=customerDetails != null ? customerDetails.getCustomerId() : ""%>"></input>
		 <%} %><small  class="help-block form-text text-muted helpCustomerText">Use only letters, numbers, '-', and '_' for customer ID</small>
		<div class="customer-error-msg required-message-id required-message hidden">This field is required</div>
		<div class="customer-error-msg customerId-validation hidden">Customer ID is invalid</div>
	</div>
	<div class="form-group">
		 <div class="label-div">
		 	<label for="firstName">First Name</label>
		 <span class="required-icon">*</span>
		 </div>
		 <input type="text" name="firstName" id="firstName" value="<%=customerDetails != null ? customerDetails.getFirstName() : ""%>"></input>
		<div class="customer-error-msg required-message-fname required-message hidden">This field is required</div>
	</div>
	<div class="form-group">
		<div class="label-div">
			 <label for="lastName">Last Name</label>
		 <span class="required-icon">*</span>
		</div>
		<input type="text" name="lastName" id="lastName" value="<%=customerDetails != null ? customerDetails.getLastName() : ""%>"></input>
		<div class="customer-error-msg required-message-lname required-message hidden">This field is required</div>
	</div>
	<div class="form-group">
		 <div class="label-div">
		 	<label for="emailAddress">Email Address</label>
		 	<span class="required-icon">*</span>
		 </div>
		<input type="text" name="emailAddress" id="emailAddress" value="<%=customerDetails != null ? customerDetails.getEmailAddress() : ""%>"></input>
		<div class="customer-error-msg required-message-email required-message hidden">This field is required</div>
		<div class="customer-error-msg email-validation hidden">Email Address is not valid</div>
	</div>
	<input class="btn btn-default braintree-btn" type="submit" value="<%=formTask %>"  id="customer"></input>
	<a  class="btn btn-default braintree-btn" id="cancel" href="${viewURL}">Cancel</a>
</form>
</div>
<div id="image" class="col-sm-6" >
<img class="info-img" src="<%=request.getContextPath()%>/img/information.jpg"/>
<%if(formTask.equalsIgnoreCase("Create Customer")){%>
 <span class="form-info">
 	->Use to create customer in braintree<br/>
	->API:- /o/rest/payment/braintree/customer/create<br/>
				Parameter :- Form fields<br/>
				Response :- Json Object Containing message with customer ID
 </span>
<span class="form-example">
    Example:-<br/>
    Request:-CustomerID-20164,<br/>
    			    &emsp;&emsp;&emsp;&emsp;&nbsp;FirstName-Mark,<br/>
    			     &emsp;&emsp;&emsp;&emsp;&nbsp;LastName-Jones,<br/>
    			      &emsp;&emsp;&emsp;&emsp;&nbsp;EmailAddress-mark.jones@gmail.com<br/>
    Response:-Customer Created successfully with ID:-20164<br/>
    		&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;OR<br/>
    		  &emsp;&emsp;&emsp;&emsp;&emsp;Customer can not be created with error message
</span>
 <%}else if(formTask.equalsIgnoreCase("Update Customer")){%>
 <span class="form-info">
 	->Use to update customer in braintree<br/>
	->API:-/o/rest/payment/braintree/customer/update<br/>
				Parameter:-Form fields<br/>
				Response:-Json Object Containing message with customer ID
 </span>
 <span class="form-example">
    Example:-<br/>
    Request:-CustomerID-20164,<br/>
    			    &emsp;&emsp;&emsp;&emsp;&nbsp;FirstName-Mark,<br/>
    			     &emsp;&emsp;&emsp;&emsp;&nbsp;LastName-Jones,<br/>
    			      &emsp;&emsp;&emsp;&emsp;&nbsp;EmailAddress-mark.jones@gmail.com<br/>
    Response:-Customer Updated successfully with ID:-20164<br/>
    		&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;OR<br/>
    		  &emsp;&emsp;&emsp;&emsp;&emsp;Customer can not be updated with error message
</span>
 <%}else{ %>
 <span class="form-info">
 	->Use to create liferay existing users as braintree customer<br/>
	->API:-/o/rest/payment/braintree/customer/liferayuser/<br/>
				Parameter:-Form fields<br/>
				Response:-Json object containing message with customer ID
 </span>
 <span class="form-example">
    Example:-<br/>
    Request:-CustomerID-20164,<br/>
    			    &emsp;&emsp;&emsp;&emsp;&nbsp;FirstName-Mark,<br/>
    			     &emsp;&emsp;&emsp;&emsp;&nbsp;LastName-Jones,<br/>
    			      &emsp;&emsp;&emsp;&emsp;&nbsp;EmailAddress-mark.jones@gmail.com<br/>
    Response:-Customer Created successfully with ID:-20164<br/>
    		&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;OR<br/>
    		  &emsp;&emsp;&emsp;&emsp;&emsp;Customer can not be created with error message
</span>
 <%} %>
</div>
</div>

<script>
var form=$("#customerInfo");
$('#customer').click(function(e){
		console.log("create customer"+<%=urlFlag%>);
		console.log(form.serialize());
		 e.preventDefault();
		var flag=false;
		var emailAddress=$("#emailAddress").val();
		var customerURL='<%=url%>';
		var customerId=jQuery('input[name="customerId"]').val();
		var firstName=jQuery('input[name="firstName"]').val();
		var lastName=jQuery('input[name="lastName"]').val();
		var emailAddress=jQuery('input[name="emailAddress"]').val();
		var customerFormURL='${customerFormURL}';
		if(<%=urlFlag.equalsIgnoreCase("false")%>){
			console.log(customerId);
			customerURL=customerURL+customerId;
		}
		console.log(emailAddress+"emailAddress");
		//console.log($(".required-message-id"));
		 //e.preventDefault();
		flag= validateFields(customerId,firstName,lastName,emailAddress,flag);
		
		 if(!flag){
			e.preventDefault();
			 $.ajax({
			  		url: customerURL ,
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
			            			form.html(item.message+item.id+'<a class="back" href='+customerFormURL+'>Back</a>');
			            		}
			            		else{
			            			console.log("inside else");
			            			form.html(item.message+item.id+'<a class="back" href='+customerFormURL+'>Back</a>');
			            		}
			                 })   
					}
				}); 
		} 
	})
	
	function validateFields(customerId,firstName,lastName,emailAddress){
			if(customerId == ''){
				console.log("here");
				$(".required-message-id").removeClass('hidden');
				$(".required-message-id").addClass('show');
				$(".customerId-validation").removeClass('show');
				$(".customerId-validation").addClass('hidden');
				flag=true;
			}
			else{
				console.log("not here");
				$(".required-message-id").removeClass('show');
				$(".required-message-id").addClass('hidden');
				flag=false;
				flag=validateCustomerId(customerId,flag);
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
				$(".required-message-email").removeClass('hidden');
				$(".required-message-email").addClass('show');
				flag=true;
			}
			else{
				$(".required-message-email").removeClass('show');
				$(".required-message-email").addClass('hidden');
				flag=false;
				flag=validateEmail(flag,emailAddress);
			}  
			 return flag;
	}
function validateCustomerId(customerId,flag){
	var regex=/^[a-zA-Z0-9_-]*$/;
	if(regex.test(customerId)){
		$(".customerId-validation").removeClass('show');
		$(".customerId-validation").addClass('hidden');
		flag=false;
	}
	else{
		$(".customerId-validation").removeClass('hidden');
		$(".customerId-validation").addClass('show');
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
</script>
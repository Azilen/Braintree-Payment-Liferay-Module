<%@ include file="/init.jsp" %>
<h2>Payment</h2>
<portlet:defineObjects />
<liferay-theme:defineObjects />
<liferay-portlet:renderURL var="paymentSuccessURL" >
	<liferay-portlet:param name="mvcRenderCommandName" value="paymentStatus"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="viewURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="view"/>
	<liferay-portlet:param name="page" value="view"/>
</liferay-portlet:renderURL>
<%String amount=(String)request.getAttribute("amount");
String clientToken=(String)request.getAttribute("clientToken");
User portalUser=PortalUtil.getUser(request);
String loggedInUserName=portalUser.getFirstName();
String userId=String.valueOf( portalUser.getUserId());
pageContext.setAttribute("userId",userId); 
pageContext.setAttribute("userName",loggedInUserName); 
%>    

<body>
<div>
<div id="transaction">
  <div id="dropin-container"></div>
  	<div id="paymentInfo">
	  		<div class="form-group">
	  			 <label for="amount">Amount</label>
		  		<input type="text" name="<portlet:namespace/>amount" value="${amount}" disabled="disabled"></input>
		  </div>
		  		<input type="hidden" name="<portlet:namespace/>customerId" value="${userId}"></input>
		  <div class="form-group">
		  		 <label for="customer">Card Holder Name</label>
		  		<input type="text" name="customerName" value="${userName}" disabled="disabled"></input>
		  </div>
		  <div class="form-group">
		  		 <label for="subMerchant">Sub Merchant</label>
		  		<select id="subMerchants"></select>
		  </div>
		  <div class="form-group">
		  		 <label for="serviceFee">Service Fee Amount</label>
		  		<input type="text" name="<portlet:namespace/>serviceFee" value="<%=portletPreferences.getValue("serviceFee", "-1")%>" readonly/>
		  </div>
	  </div>
	  <button class="btn btn-default braintree-btn" id="submit-button">Request payment method</button>
  <a  class="btn btn-default braintree-btn" id="cancel" href="${viewURL}">Cancel</a>
</div>
  
  <div id="list-image">
	<img class="list-info-img-users" src="<%=request.getContextPath()%>/img/list-information.jpg"/>
	<span id="list-info-1">
		->Use to create braintree transaction<br/>
		->API:-/o/rest/payment/braintree/transaction/create<br/>
					Parameter:-payment_method_nonce,amount,subMerchant,<br/>
						&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;customerId,serviceFee<br/>
					Response:-Json object containing message with transaction ID
	</span>
	<span id="list-example-users">
		->Example:-<br/>
					Request:-payment_method_nonce - 79b8a20a-2e95-023a-65e5-8751974bd055,<br/>
									&emsp;&emsp;&emsp;&emsp;&nbsp;amount - 200,<br/>
									&emsp;&emsp;&emsp;&emsp;&nbsp;subMerchant ID - 23159,<br/>
									&emsp;&emsp;&emsp;&emsp;&nbsp;customer ID - 20164,<br/>
									&emsp;&emsp;&emsp;&emsp;&nbsp;serviceFee-20<br/>
					Response:-Transaction Created successfully with ID:-rps49knj<br/>
									&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;OR<br/>
									&emsp;&emsp;&emsp;&emsp;&emsp;Transaction can not be created with error message.
																		
	</span>
</div>
</div>
  </body> 
  <div id="message"></div>
  <script>
                                  define._amd = define.amd;
                                  define.amd = false;
                          </script>
                                  <script src="https://js.braintreegateway.com/web/dropin/1.7.0/js/dropin.min.js"></script>
                          <script>
                          define.amd = define._amd;
                          </script>
 <script>
 
 $( document ).ready(function() {
		//alert("paymement");
	    console.log( "get merchants list" );
	    $.ajax({
	  		url: "/o/rest/payment/braintree/merchant/list",
	  		type: 'GET',
	  		success: function(data){
	  			 var list = jQuery.parseJSON(data);
	  			 console.log(list+"success");
	            $.each(list, function (index, item) {
	            		console.log(index+""+item.id+""+item.name);
	                 $('#subMerchants').append($('<option>', {value: item.id, text: item.name }));
	                 }) 
			}
		}); 
	    
	    var button = document.querySelector('#submit-button');
		 braintree.dropin.create({
		      authorization:'${clientToken}', 
		      container: '#dropin-container',
		    }, function (createErr, instance) {
		   	// alert("dropin created");
		    	console.log("load braintree form");
		    	button.addEventListener('click', function (event) {
		      instance.requestPaymentMethod(function (err, payload) {
		        if (err) {
		          console.log('Error', err);
		          return;
		        }
		        // Add the nonce to the form and submit
		        console.log("get nonce");
		        console.log(payload.nonce);
		         var amount=jQuery('input[name="<portlet:namespace/>amount"]').val();
		         var customerId=jQuery('input[name="<portlet:namespace/>customerId"]').val();
		         var subMerchant=jQuery("#subMerchants option:selected").val();
		         var serviceFee=jQuery('input[name="<portlet:namespace/>serviceFee"]').val();
		         console.log(serviceFee);
		        // console.log("nirali");
		       // debugger;
		       var saveData =  $.ajax({
	    	  		url: "/o/rest/payment/braintree/transaction/create",
	    	  		type: 'POST',
	    	  		data:{
	    	  			payment_method_nonce:payload.nonce,
	    	  			amount:amount,
	    	  			subMerchant:subMerchant,
	    	  			customerId:customerId,
	    	  			serviceFee:serviceFee
	    	  		},
	    	  		success: function(response){
	    	  			//debugger;
	    	  			//alert("success dropin"+data);
	    	  			console.log("nirali"+'${paymentSuccessURL}');
	    	  			var url='${paymentSuccessURL}'+"&<portlet:namespace/>message="+response
	    	  			window.location.href =url;
	    	  		}
	 		}); 
		       saveData.error(function(data) { 
		    	   //alert("Something went wrong"); 
		    	   //window.location.href ='${paymentSuccessURL}';   
		    	 console.log("error"+data);
		    	 $("#transaction").html("<div style='font-size:20px'>Transaction created successfully<div> ");
		       });
		        });
		      });
		    });
	});
 
    
  </script>

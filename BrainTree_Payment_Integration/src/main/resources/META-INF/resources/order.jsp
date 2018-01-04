<%@ include file="/init.jsp" %>
<h2>Order</h2>
<liferay-portlet:actionURL var="actionCommand1" name="actionCommand1">
</liferay-portlet:actionURL>
<liferay-portlet:renderURL var="viewURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="view"/>
	<liferay-portlet:param name="page" value="view"/>
</liferay-portlet:renderURL>
<div class="order-form">
<div>
	<a class="back" href="${viewURL}">Back</a>
</div>
<div>
<form action="${actionCommand1}" method="post">
	 <div class="form-group">
		<label>Amount</label>
		<input type="text" name="<portlet:namespace/>amount" value="200"  readonly></input>
	</div>
	 <div class="form-group">
	<img class="order-tshirt"  src="<%=request.getContextPath()%>/img/ECM-Monochrome-T-Shirt.jpg">
	</div>
	 <div class="form-group">
		<label>ECM-Monochrome-T-Shirt</label>
	</div>
	 <div class="form-group">
	 	<p>Keep your team pride on show all season with the EHC Red Bull München Monochrome T-shirt, a statement T-shirt in a classic short-sleeved fit with a big team crest on the front in white.
			Material: 100% Cotton
			EHC Red Bull München Monochrome T-shirt for men
			Printed EHC Red Bull München team crest on the front in white
			Short sleeves
			Round neck
		</p>
	 </div>
	 <input type="hidden" id="clientToken" name="<portlet:namespace/>clientToken"></input>
	 <div class="form-group">
		<input class="btn btn-default braintree-btn submitButton"  value="Buy Now" type="submit" disabled="disabled"></button>
	</div>
	</form>
	</div>
	<div id="list-image">
		<img class="order-info-img" src="<%=request.getContextPath()%>/img/list-information.jpg"/>
		<span id="order-info">
			->Use to generate client token<br/>
			->Client token is used to load dropin UI<br/>
			->API:-/o/rest/payment/braintree/checkout<br/>
						Response:-clientToken<br/><br/>
		</span>
	</div>
	</div>
<script>
$(document ).ready(function() {
    console.log( "ready!" );
    $.ajax({
  		url: "/o/rest/payment/braintree/checkout",
  		type: 'GET',
  		success: function(data){
  			if(data != " "){
  				$("#clientToken").val(data);
  	  			$(".submitButton").removeAttr('disabled');
  	  			console.log(data);
  	  			console.log("here get token");	
  			}
		}
	}); 
});
</script>
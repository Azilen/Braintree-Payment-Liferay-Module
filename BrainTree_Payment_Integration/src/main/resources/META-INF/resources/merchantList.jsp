<%@ include file="/init.jsp" %>
<h2>Merchants</h2>
<liferay-portlet:renderURL var="updateMerchantURL" >
	<liferay-portlet:param name="mvcRenderCommandName" value="updateMerchant"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="viewURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="view"/>
	<liferay-portlet:param name="page" value="view"/>
</liferay-portlet:renderURL>
<c:set var="updateMerchantURL" value="${updateMerchantURL}" />

<div>                          
  <div id="merchantList">
  <a class="back" href="${viewURL}">Back</a>
	<table class="table table-striped table-hover">
		<thead>
			<tr> 
				<th>Merchant ID</th> 
				<th></th> 
				<th></th>
			</tr>
		</thead>
		<tbody id="merchants">
		</tbody>
	</table>
</div>   
<div class="loader">
 	<img src="<%=request.getContextPath() %>/img/loader.gif" height="100" width="100"/>
 </div>
 <div id="list-image" class="hidden">
 	<img class="list-info-img-merchant" src="<%=request.getContextPath()%>/img/list-information.jpg"/>
	<span id="list-info-1">
		->Use to get submerchants available in braintree<br/>
		->API:-/o/rest/payment/braintree/merchant/list<br/>
			Response:-Json Array containing submerchant details<br/>
	</span>
	<span id="list-example-1">
		->Example:-<br/>
		->Response:-<br/>[{"firstName":"Joe","lastName":"Doe","id":"122976"<br/>"emailAddress":"joe.doe@gmail.com",<br/>"dateOfBirth":"1995-09-17",
		"locality":"San Fransisco",<br/>"postalcode":"94016","streetAddress":"111 Main Street",<br/>"region":"CA","accountNumber":"1321",<br/>"master_merchant_account":"master_merchant_account_id"},<br/>
			{"firstName":"John","lastName":"Williams","id":"122359",<br/>"emailAddress":"john.williams@gmail.com",,<br/>"dateOfBirth":"1999-07-27",
		"locality":"San Fransisco",<br/>"postalcode":"94016","streetAddress":"111 Main Street",<br/>"region":"CA","accountNumber":"1321",<br/>"master_merchant_account":"master_merchant_account_id"}]
	</span>
	<span id="list-info-2">
		->Use to find submerchant with particular ID<br/>
		->API:-/o/rest/payment/braintree/merchant/find<br/>
					Parameter:-SubMerchantID<br/>
					Response:-Json object containing merchant details
	</span>
	<span id="list-example-2">
		->Example:-<br/>
		->Request:-122976<br/>
		->Response:-<br/>{"firstName":"Joe","lastName":"Doe","id":"122976",<br/>"emailAddress":"joe.doe@gmail.com","dateOfBirth":"1995-09-17",<br/>
		"locality":"San Fransisco","postalcode":"94016","streetAddress":"111 Main Street",<br/>"region":"CA","accountNumber":"1321",<br/>"master_merchant_account":"master_merchant_account_id"},
	</span>
 </div>
 </div>
<script>
$( document ).ready(function() {
    console.log( "get merchants list" );
    var updateMerchantURL="${updateMerchantURL}";
    console.log(updateMerchantURL+"updateMerchantURL");
    $.ajax({
  		url: "/o/rest/payment/braintree/merchant/list",
  		type: 'GET',
  		success: function(data){
  			 var list = jQuery.parseJSON(data);
  			$('.loader').css({'display': 'none'});
  			$('#list-image').removeClass('hidden');
  			 if(list.length == 0){
  				 console.log("no json array");
  				 $('#merchants').append('<span class="no-results">There are no results</span>');
  			 }
  			 else{
  				 console.log(list+"success");
  	            $.each(list, function (index, item) {
  	            		console.log(index+""+item.id+""+item.name);
  	                 $('#merchants').append('<tr><td>'+item.name+'</td><td><a onclick=getMerchant("'+updateMerchantURL+'","'+item.id+'")>Edit</a></td></tr>');
  	                 }) 
  			 }
  			
		}
	}); 
});

function getMerchant(url,id){
	  console.log(id+"merchant method");
	    $.ajax({
	  		url: "/o/rest/payment/braintree/merchant/find/"+id,
	  		type: 'GET',
	  		success: function(data){
	  			console.log(data); 
	  			merchantURL=url+"&<portlet:namespace/>merchantDetails="+data;
	  			 console.log(merchantURL+"success"); 
	  			window.location = merchantURL;
			}
		});  
}    
</script>

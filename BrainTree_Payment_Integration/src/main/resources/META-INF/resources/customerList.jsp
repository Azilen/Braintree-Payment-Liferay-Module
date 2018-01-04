<%@ include file="/init.jsp" %>
<h2>Customers</h2>
<liferay-portlet:renderURL var="updateCustomerURL" >
	<liferay-portlet:param name="mvcRenderCommandName" value="updateCustomer"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="viewURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="view"/>
	<liferay-portlet:param name="page" value="view"/>
</liferay-portlet:renderURL>
<c:set var="updateCustomerURL" value="${updateCustomerURL}" />
  <div>
 <div id="customerList">
 	<a class="back" href="${viewURL}">Back</a>
	<table class="table table-striped table-hover">
		<thead>
			<tr> 
				<th>Firstname</th> 
				<th>Lastname</th> 
				<th></th> 
				<th></th>
			</tr>
		</thead>
		<tbody id="customers">
		</tbody>
	</table>
</div>
 <div class="loader">
 	<img src="<%=request.getContextPath() %>/img/loader.gif" height="100" width="100"/>
 </div>
<div id="list-image" class="hidden">
	<img class="list-info-img-class" src="<%=request.getContextPath()%>/img/list-information.jpg"/>
	<span id="list-info-1">
		->Use to get customers available in braintree<br/>
		->API:-/o/rest/payment/braintree/customer/list<br/>
					Response:-Json object containing customer details<br/>
	</span>
	<span id="list-example-1">
		->Example:-<br/>
		->Response:-<br/>[{"firstName":"Mark","lastName":"Jones",<br/>"emailAddress":"mark.jones@gmail.com","customerId":"20164"},<br/>
			{"firstName":"Lily","lastName":"Smith",<br/>"emailAddress":"lily.smith@gmail.com","customerId":"191976"},<br/>
								{"firstName":"Henry","lastName":"Johnson",<br/>"emailAddress":"henry.johnson@gmail.com","customerId":"12976"}]
	</span>
	<span id="list-info-2">
		->Use to find customer with particular ID<br/>
		->API:-/o/rest/payment/braintree/customer/find<br/>
					Parameter:-CustomerID<br/>
					Response:-Json object containing customer details
	</span>
	<span id="list-example-2">
		->Example:-<br/>
		->Request:-20164<br/>
		->Response:-<br/>{"firstName":"Mark","lastName":"Jones",<br/>"emailAddress":"mark.jones@gmail.com","id":"20164"}
	</span>
</div>   
</div>
<div id="message"></div>                   

<script>
$( document ).ready(function() {
    console.log( "get customers list" );
    var updateCustomerURL="${updateCustomerURL}";
    console.log(updateCustomerURL+"updateCustomerURL");
    $.ajax({
  		url: "/o/rest/payment/braintree/customer/list",
  		type: 'GET',
  		success: function(data){
  			 var list = jQuery.parseJSON(data);
  			 console.log(list+"success");
  			$('.loader').css({'display': 'none'});
  			$('#list-image').removeClass('hidden');
  			 if(list.length == 0){
  				console.log("no json array");
 				 $('#customers').append('<span class="no-results">There are no results</span>');
  			 }
  			 else{
  				  $.each(list, function (index, item) {
              		console.log(index+""+item.id+""+item.name);
                   //$('#customerList').append($('<a>', {id: item.id, text: item.name }));
                   $('#customers').append('<tr><td>'+item.firstName+'</td><td>'+item.lastName+'</td><td><a onclick=getCustomer("'+updateCustomerURL+'","'+item.customerId+'")>Edit</a></td><td><a onclick=deleteCustomer("'+item.customerId+'")>Delete</a></td></tr>');
                   }) 
  			 }
		}
	}); 
});

function getCustomer(url,id){
	//var customerid =  $(this).attr("id");
	  console.log(id+"customr method");
	   $.ajax({
	  		url: "/o/rest/payment/braintree/customer/find/"+id,
	  		type: 'GET',
	  		success: function(data){
	  			console.log(data); 
	  			 customerURL=url+"&<portlet:namespace/>customerDetails="+data;
	  			 console.log(customerURL+"success"); 
	  			window.location = customerURL;
			}
		}); 
}    

function deleteCustomer(id){
	$.ajax({
  		url: "/o/rest/payment/braintree/customer/delete/"+id,
  		type: 'GET',
  		success: function(data){
  			$("#customerList").html(data);
		}
	}); 
}
</script>

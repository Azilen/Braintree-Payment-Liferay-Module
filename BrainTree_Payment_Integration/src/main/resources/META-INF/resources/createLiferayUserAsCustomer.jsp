<%@ include file="/init.jsp" %>
<h2>Liferay Users</h2>
<liferay-portlet:renderURL var="createLRUserCustomerURL" >
	<liferay-portlet:param name="mvcRenderCommandName" value="createLRUserAsCustomer"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="viewURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="view"/>
	<liferay-portlet:param name="page" value="view"/>
</liferay-portlet:renderURL>
<c:set var="createLRUserCustomerURL" value="${createLRUserCustomerURL}" />
<script>
                                  define._amd = define.amd;
                                  define.amd = false;
                          </script>
    <link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">  
      <script src="http://code.jquery.com/jquery-1.10.2.js"></script>  
      <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>  
     <script>
                          define.amd = define._amd;
                          </script>
<div>                   
<div id="liferayUsersList">
	<a class="back" href="${viewURL}">Back</a>
	<table class="table table-striped table-hover">
		<thead>
			<tr> 
				<th>Firstname</th> 
				<th>Lastname</th> 
				<th></th> 
			</tr>
		</thead>
		<tbody id="usersList">
		</tbody>
	</table>
</div>       
<div id="message"></div>                  
<div class="loader">
 	<img src="<%=request.getContextPath() %>/img/loader.gif" height="100" width="100"/>
 </div> 
  <div id="list-image" class="hidden">
 	<img class="list-info-img-users" src="<%=request.getContextPath()%>/img/list-information.jpg"/>
	<span id="list-info-1">
		->Use to get list of liferay users<br/>
		->API:-/o/rest/payment/braintree/liferay/users/list<br/>
					Response:-JsonArray containing liferay users details
	</span>
	<span id="list-example-users">
		->Example:-<br/>
		->Response:-<br/>
		{"firstname":"Test","lastName":"Test","userId":"20164"<br/>
		"dateOfBirth":"1970-01-01","emailAddress":"test@liferay.com"},<br/>
		{"firstname":"Nirali","lastName":"Joshi","userId":"122976",<br/>
		"dateOfBirth":"1970-01-01","emailAddress":"nirali.joshi@azilen.com"},<br/>{"firstname":"Maharshi","lastName":"Joshi","userId":"123013",<br/>
		"emailAddress":"maharshi123@gmail.com","dateOfBirth":"1970-01-01"},
	</span>
 </div>
 </div>
<script>
$( document ).ready(function() {
    console.log( "get lr list" );
    var createLRUserCustomerURL="${createLRUserCustomerURL}";
    $.ajax({
  		url: "/o/rest/payment/braintree/liferay/users/list",
  		type: 'GET',
  		success: function(data){
  			 var list = jQuery.parseJSON(data);
  			 console.log(list+"success");
  			$('.loader').css({'display': 'none'});
  			$('#list-image').removeClass('hidden');
            $.each(list, function (index, item) {
            		var customerDetails="{id:"+item.userId+",firstName:"+item.firstname+",lastName:"+item.lastName+",emailAddress:"+item.emailAddress+"}";
                 $('#usersList').append('<tr><td>'+item.firstname+'</td><td> '+item.lastName+'</td><td><a  class="customer" href='+createLRUserCustomerURL+'&<portlet:namespace/>customerDetails='+customerDetails+'>Create</a></td></tr>');
                 }) 
		}
	}); 
});
</script>

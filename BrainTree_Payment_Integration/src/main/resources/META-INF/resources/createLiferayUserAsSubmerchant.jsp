<%@ include file="/init.jsp" %>
<h2>Liferay Users</h2>
<liferay-portlet:renderURL var="createLRUserSubMerchantURL" >
	<liferay-portlet:param name="mvcRenderCommandName" value="createLRUserAsSubMerchant"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="viewURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="view"/>
	<liferay-portlet:param name="page" value="view"/>
</liferay-portlet:renderURL>
<c:set var="createLRUserSubMerchantURL" value="${createLRUserSubMerchantURL}" />
<script>
                                  define._amd = define.amd;
                                  define.amd = false;
                          </script>
    
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
		{"firstname":"kiran","lastName":"Joshi","userId":"122976",<br/>
		"dateOfBirth":"1970-01-01","emailAddress":"kiran.joshi@azilen.com"},<br/>{"firstname":"Mahesh","lastName":"Patel","userId":"123013",<br/>
		"emailAddress":"mahesh123@gmail.com","dateOfBirth":"1997-01-01"},
	</span>
 </div>
 </div>
<script>
$( document ).ready(function() {
    console.log( "get lr list" );
    var createLRUserSubMerchantURL="${createLRUserSubMerchantURL}";
    $.ajax({
  		url: "/o/rest/payment/braintree/liferay/users/list",
  		type: 'GET',
  		success: function(data){
  			 var list = jQuery.parseJSON(data);
  			 console.log(list+"success");
  			$('.loader').css({'display': 'none'});
  			$('#list-image').removeClass('hidden');
            $.each(list, function (index, item) {
            	if(jQuery.isEmptyObject(list)){
            		 $('#usersList').html("There are no users");
            		 console.log("no users");
            	}
            	else{
            		var merchantDetails="{id:"+item.userId+",firstName:"+item.firstname+",lastName:"+item.lastName+",emailAddress:"+item.emailAddress+",dateOfBirth:"+item.dateOfBirth+"}";
                    $('#usersList').append('<tr><td>'+item.firstname+'</td><td>'+item.lastName+'</td><td><a  class="customer" href='+createLRUserSubMerchantURL+'&<portlet:namespace/>merchantDetails='+merchantDetails+'>Create</a></td></tr>');
            	}
                 }) 
		}
	}); 
});
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
String basepath = request.getContextPath();
%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看商品 - ${good.title}</title>
<link rel="stylesheet" type="text/css" href="/TradeSystem/css/view_good_page.css">
<script type="text/javascript"	src="/TradeSystem/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#buy").hover(function() {
		$(this).css({"background" : "rgb(23, 86, 255)", "color" : "rgb(182, 202, 255)"});
	}, function() {
		$(this).css({"background" : "rgb(182, 202, 255)", "color" : "rgb(245, 255, 182)"});
	}).click(function() {
		giveOrder();
	});
});

function giveOrder() {
	$.ajax( {
		type: "POST",
		url: "buyGood",
		data: { gid: ${good.id} },
		dataType: "json"
	}).done(function( json ) {
		data = eval("("+json+")");
		switch ( data.status ) {
		case -3:
			alert("Got No Authority");
			break;
		case -2:
			alert("Wrong Item");
			break;
		case -1:
			alert("Order Failed");
			break;
		case 0 :
			$(".login").click();
			break;
		case 1 :
			$("#buyer-amount").html(parseInt($("#buyer-amount").html())+1);
			break;
		default :
			alert("ERROR");
			break;
	}
	}).fail(function() {
		alert("FAIL");
	}).error(function (XMLHttpRequest, textStatus, errorThrown) {
		$("#ajax").html(XMLHttpRequest.responseText);
	});
};
</script>
</head>
<jsp:include page="user-info-box.jsp" />
<body>
${msg}
<h1>${good.title}</h1>
<span id="buyer-amount">${good.buyerCount}</span>
<s:if test="#request.canBuy=='true'">
	<span id="buy">订购</span>
</s:if>
<span class="price">${good.price} RMB</span>

<div id="desc"><img src="<%=basepath%>/${good.pic}" />${good.desc}<div class="date">${good.addTime}</div><div style="clear: both;"></div></div>
<s:if test="#session.USER_SESSION.id!=0">
<jsp:include page="order-history.jsp" />
</s:if>
<jsp:include page="footer.jsp" />
</body>
</html>
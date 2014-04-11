<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
String basepath = request.getContextPath();
%>
<style type="text/css">
#my-order {
	position: fixed;
	left: 10px;
	top: 100px;
	width: 15%;
	background-color: rgb(252, 255, 216);
	padding: 5px;
}
#my-order div {
	margin: 5px;
	padding: 3px;
	color: #000;
}
#my-order .history {
	cursor: pointer;
}
#my-order .content {
	border: 2px dotted #000;
}
#my-order .content a {
	text-decoration: none;
	color: #636363;
	padding: 3px;
}
#my-order .content .title a:hover {
	background-color: #97C7F3;
}
#my-order .content .publisher-info a:hover {
	background-color: #C0F8C9;
}
#my-order .content .title {
	margin-right: 10px;
	display: block;
	font-size: 17px;
}
#my-order .content .publisher-info {
	margin-top: 10px;
	display: block;
	font-size: 13px;
}
#my-order .content .g-price {
	font-size: 15px;
	font-weight: bold;
	display: block;
	margin: 5px;
}
#my-order .content .time {
	display: block;
	font-size: 15px;
}
#my-order .content .oid {
	font-size: 14px;
}
#my-order .content img {
	width: 60%;
	float: right;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	var isLoad = false;
	$("#my-order .history").hover(function() {
		$(this).css("background", "rgb(252, 255, 182)");
	}, function() {
		$(this).css("background", "rgb(252, 255, 216)");
	}).click(function() {
		if (!isLoad) {
			$("#my-order").css("position", "absolute");
			loadOrderList();
			isLoad = true;
		}
		else
			$(".content").toggle();
	});
});

function loadOrderList() {
	$.ajax( {
		type: "POST",
		url: "getOrderList",
		data: { type : "all" },
		dataType: "json"
	}).done(function( json ) {
		data = eval("("+json+")");
		$.each(data, function(i, item) {
			$("#my-order").append("<div class=\"content\"><img src=\"<%=basepath%>/"+item.goodPic+"\" /><span class=\"title\"><a href=\"<%=basepath%>/g/"+item.goodId+"\">"+item.goodTitle+"</a></span><span class=\"publisher-info\"><a href=\"<%=basepath%>/p/"+item.publisherPid+"\">"+item.publisherName+"</a></span><span class=\"g-price\">"+item.goodPrice+" RMB</span><span class=\"time\">"+item.time+"</span><span class=\"oid\">OID: "+item.orderId+"</span>");
		});
	}).fail(function() {
		alert("FAIL");
	}).error(function (XMLHttpRequest, textStatus, errorThrown) {
		$("#ajax").html(XMLHttpRequest.responseText);
	});
}
</script>
<div id="my-order">
	<div class="history">订单历史</div>
</div>
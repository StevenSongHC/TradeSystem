<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<% 
String basepath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>校园小商品交易系统</title>
<link rel="stylesheet" type="text/css" href="css/index.css">
<script type="text/javascript"	src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".item>").hover(function() {
			$(this).parent().find("img").css({"width" : "160px", "height" : "160px"});
			$(this).parent().find("a").css({"text-decoration" : "underline", "color" : "rgb(231, 127, 45)"});
		}, function() {
			$(this).parent().find("img").css({"width" : "150px", "height" : "150px"});
			$(this).parent().find("a").css({"text-decoration" : "none", "color" : "rgb(45, 149, 231)"});
		});
	});
</script>
</head>

<jsp:include page="user-info-box.jsp" />

<body>

<jsp:include page="header.jsp" />

<h4>热门商品</h4>
<div id="hot-good-list">
<s:iterator value="#request.hotGood" id="good">
<div class="item">
	<div class="pic">
		<a href="<%=basepath%>/g/${good.id}" target="_blank"><img alt="${good.title}" src="<%=basepath%>/${good.pic}" title="${good.title}"></a>
	</div>
	<div class="title"  title="${good.title}">
		<a href="<%=basepath%>/g/${good.id}" target="_blank">${good.title}</a>
	</div>
	<div class="price">
		${good.price} RMB
	</div>
</div>
</s:iterator>
<div style="clear: both;"></div>
</div>

<h4>最新商品</h4>
<div id="latest-good-list">
<s:iterator value="#request.latestGood" id="good">
<div class="item">
	<div class="pic">
		<a href="<%=basepath%>/g/${good.id}" target="_blank"><img alt="${good.title}" src="<%=basepath%>/${good.pic}" title="${good.title}"></a>
	</div>
	<div class="title" title="${good.title}">
		<a href="<%=basepath%>/g/${good.id}" target="_blank">${good.title}</a>
	</div>
	<div class="price">
		${good.price} RMB
	</div>
</div>
</s:iterator>
<div style="clear: both;"></div>
</div>

<jsp:include page="footer.jsp" />

</body>
</html>
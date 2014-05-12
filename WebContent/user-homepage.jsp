<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<% 
String basepath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${user.name}</title>
<link rel="stylesheet" type="text/css" href="/TradeSystem/css/user_homepage.css">
<script type="text/javascript"	src="/TradeSystem/js/jquery-1.9.1.min.js"></script>
</head>
<body>
<jsp:include page="user-info-box.jsp" />
<jsp:include page="header.jsp" />
<br><br>
<div class="portrait"><img src="<%=basepath%>/${user.portrait}" title="头像" /></div>

<h1>${user.name}<s:if test="#session.USER_SESSION.name==#request.user.name"><span class="update-profile"><a href="updateProfile">修改资料</a></span></s:if></h1>
<div class="summary"><s:if test="#request.user.summary==null"><span class="default">无描述</span></s:if>${user.summary}</div>
<s:if test="#session.USER_SESSION.name==#request.user.name">
<div id="order-list">
	<div class="header">我的订单记录</div>
	<s:if test="#request.orderList.isEmpty()"><div class="empty">无任何订单记录</div></s:if>
	<s:else>
	<s:iterator value="#request.orderList" id="item">
	<div class="content">
	<img src="<%=basepath%>/${item.goodPic}" title="OID: ${item.orderId}" />
	<div class="good-title"><a href="<%=basepath%>/g/${item.goodId}" target="_blank">${item.goodTitle}</a></div>
	<div class="publisher-name"><a href="<%=basepath%>/p/${item.publisherPid}" target="_blank">${item.publisherName}</a></div>
	<div class="good-price">${item.goodPrice} RMB</div>
	<input type="hidden" value="${item.publisherPid}">
	<div class="time">${item.time}</div>
	<div style="clear: both;"></div>
	</div>
	</s:iterator>
	</s:else>
</div>
</s:if>
<jsp:include page="footer.jsp" />
</body>
</html>
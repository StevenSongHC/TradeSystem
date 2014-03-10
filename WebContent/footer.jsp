<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>All Rights Reserved</title>
<style type="text/css">
.declaration-body {
	position:absolute;
	bottom:0px;
	right:10px;
	width:100%;
	height:20px;
	text-align:center;
	background:#ccc;
	z-index:3;
	overflow:hidden;
}
.become-publisher {
	position:absolute;
	bottom:20px;
	right:10px;
	width:120px;
	height:20px;
	text-align:left;
	background:#CEE6D9;
	z-index:3;
	overflow:hidden;
}
.become-publisher a {
	text-decoration: none;
	color: gray;
}
</style>
</head>
<body>
<div class="declaration-body">All Rights Reserved</div>

<s:if test="#request.user.account==#session.USER_SESSION.account"><s:if test="!#session.USER_SESSION.isPublisher"><span class="become-publisher"><a href="/TradeSystem/become_publisher/try">Become Publisher</a></span></s:if></s:if>

</body>
</html>
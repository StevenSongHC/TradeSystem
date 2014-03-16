<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${publisher.name} - Host</title>
<link rel="stylesheet" type="text/css" href="/TradeSystem/css/publisher_homepage.css">
<script type="text/javascript"	src="/TradeSystem/js/jquery-1.9.1.min.js"></script>
</head>
<jsp:include page="user-info-box.jsp" />
<body>
<h1>${publisher.name}</h1>
<div class="summary">${publisher.summary}</div>
<div class="good-list">
	<span class="add-good"><a href="addGood">添加商品</a></span>
	<hr>
	<div class="good">GOOD</div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>校园小商品交易系统</title>
<link rel="stylesheet" type="text/css" href="css/index.css">
<script type="text/javascript"	src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
	});
</script>
</head>

<jsp:include page="user-info-box.jsp" />

<body>

<jsp:include page="search-box.jsp" />

<jsp:include page="good-list.jsp" />

<jsp:include page="footer.jsp" />

</body>
</html>
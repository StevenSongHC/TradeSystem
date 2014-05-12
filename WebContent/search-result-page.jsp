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
<link rel="stylesheet" type="text/css" href="/TradeSystem/css/search_result.css">
<script type="text/javascript"	src="<%=basepath%>/js/jquery-1.9.1.min.js"></script>
<title>result for - ${keyword}</title>
</head>
<jsp:include page="user-info-box.jsp" />
<body>
<jsp:include page="header.jsp" />
<br><br>
<h1>搜索  "${keyword}"  所放回的结果</h1>
<s:iterator value="#request.resultList" id="result">
<div class="result">
	<div class="link"><a href="<%=basepath%>/g/${result.id}" target="_blank">${result.title}</a></div>
	<div class="desc">${result.desc}</div>
</div>
</s:iterator>

<jsp:include page="footer.jsp" />
</body>
</html>
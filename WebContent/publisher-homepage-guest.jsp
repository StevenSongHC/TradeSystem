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
<title>P Home(guest)</title>
<script type="text/javascript"	src="/TradeSystem/js/jquery-1.9.1.min.js"></script>
</head>
<jsp:include page="user-info-box.jsp" />
<body>
GUEST
<div class="good-list">
	<span class="add-good"><a href="addGood">添加商品</a></span>
	<hr>
	<s:iterator value="#request.pageNavi.result" id="good">
	<hr>
	<div class="item">
		<div class="title"><a href="<%=basepath%>/g/${good.id}">${good.title}</a></div>
		<div class="price">${good.price}<span class="currency">RMB</span></div>
		<div class="pic"><img src="<%=basepath%>/${good.pic}" /></div>
		<div class="desc">${good.desc}</div>
		<div class="date">${good.addTime}</div>
		<div style="clear: both"></div>
	</div>
	</s:iterator>
	<hr>
	<div class="page-navi">共${pageNavi.allRow}条记录,每页显示${pageNavi.pageSize}条,一共${pageNavi.totalPage}页,当前第${pageNavi.currentPage}页</div>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
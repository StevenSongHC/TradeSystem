<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
String basepath = request.getContextPath();
%>
<style type="text/css">
#logo {
	display: inline;
	float: left;
}

</style>
<div id="logo">
	<a href="<%=basepath%>"><img alt="校园小商品交易系统" src="<%=basepath%>/images/logo.png" title="ts"></a>
</div>
<jsp:include page="search-box.jsp" />
<div style="clear: both;"></div>
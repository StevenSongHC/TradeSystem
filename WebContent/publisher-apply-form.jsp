<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>申请成为发布者</title>
<style type="text/css">
.actionMessage {
	margin-top:35px;
	background-color:orange;
}
</style>
<script type="text/javascript"	src="/TradeSystem/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var isGood = false;
		$("#submit").click(function(event) {
			if ($("#name").val() === "")
				alert("名称不能为空!");
			else if ($("#summary").val().trim() === "")
				alert("描述必须写上");
			else if ($("#contact").val().trim() === "")
				alert("联系方式是必须的");
			else
				isGood = true;
			/* prevent the form from submitting if valided fail */
			if (!isGood)
				event.preventDefault();
		});
	});
</script>
</head>
</head>
<body>
<s:actionmessage />
<s:form action="/become_publisher/go">
		<s:textfield id="name" name="name" label="*名称"></s:textfield>
  		<s:textfield id="summary" name="summary" label="*描述"></s:textfield>
  		<s:textfield id="contact" name="contact" label="*联系方式"></s:textfield>
  		<s:submit id="submit" value="提交申请"></s:submit>
</s:form>
<jsp:include page="footer.jsp" />
</body>
</html>
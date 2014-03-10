<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/register.css">
<title>Quick Register</title>
<script type="text/javascript"	src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var isGood = false;
		$("#submit").click(function(event) {
			if ($("#name").val() === "")
				alert("昵称不能为空!");
			else if ($("#account").val() === "")
				alert("学号必须填上");
			else if ($("#password").val() === "")
				alert("密码是必须的!!!");
			else if ($("#password_cfm").val() === "")
				alert("请再输入一次密码");
			else if ($("#password").val().length < 6)
				alert("密码至少为六位数");
			else if ($("#password").val() !== $("#password_cfm").val())
				alert("前后输入的密码不一致");
			else
				isGood = true;
			/* prevent the form from submitting if valided fail */
			if (!isGood)
				event.preventDefault();
		});
	});
</script>
</head>
<body>
<h1>register</h1>
<s:actionerror />
<s:form action="register">
		<s:textfield id="name" name="name" label="*昵称"></s:textfield>
  		<s:textfield id="account" name="account" label="*学号"></s:textfield>
  		<s:password id="password" name="password" label="*密码(>=6位数)"></s:password>
  		<s:password id="password_cfm" name="password_cfm" label="*确认密码"></s:password>
  		<s:submit id="submit" value="Register"></s:submit>
</s:form>

<jsp:include page="footer.jsp" />
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<% 
String basepath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${publisher.name}'s home page</title>
<link rel="stylesheet" type="text/css" href="/TradeSystem/css/publisher_homepage.css">
<script type="text/javascript" src="/TradeSystem/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#update-info").click(function() {
		$(this).hide();
		$("#contact").hide();
		$("#summary").hide();
		$("#do-update-info").show();
		$("#edit-contact").show();
		$("#edit-summary").show();
	});
});

function doUpdateInfo() {
	$.ajax( {
		type: "POST",
		url: "updatePublisherInfo",
		data: { pid : ${publisher.id}, contact : $("#edit-contact").val(),summary : $("#edit-summary").val() },
		dataType: "json"
	}).done(function( json ) {
		var data = eval("("+json+")");
		if (data.status) {
			$("#do-update-info").hide();
			$("#edit-contact").hide();
			$("#edit-summary").hide();
			$("#update-info").show();
			$("#contact").html($("#edit-contact").val()).show();
			$("#summary").html($("#edit-summary").val()).show();
		}
		else
			alert("!!更新出错");
	}).fail(function() {
		alert("FAIL");
	}).error(function (XMLHttpRequest, textStatus, errorThrown) {
		$("#ajax").html(XMLHttpRequest.responseText);
	});
};
</script>
</head>
<jsp:include page="user-info-box.jsp" />
<body>
<h1>${publisher.name} <s:if test="#session.PUBLISHER_SESSION.id==#request.publisher.id"><button id="update-info">update</button><button id="do-update-info" onclick="doUpdateInfo()">save</button></s:if></h1>
<div style="margin: -10px 0px 20px 7px;font-size: 23px;">contact me：<span id="contact">${publisher.contact}</span><input type="text" value="${publisher.contact}" id="edit-contact"></div>
<div id="summary">${publisher.summary}</div><textarea rows="5" cols="29" id="edit-summary">${publisher.summary}</textarea>

<jsp:include page="good-list.jsp" />

<jsp:include page="footer.jsp" />
</body>
</html>
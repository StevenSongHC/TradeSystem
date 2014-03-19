<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${USER_SESSION.name} 的消息列表</title>
<link rel="stylesheet" type="text/css" href="css/list_message.css">
<script type="text/javascript"	src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
	});
	
	function muteMessage(mid,noticeType) {
		$.ajax( {
			type: "POST",
			url: "muteMessage",
			data: { mid: mid, noticeType: noticeType },
			dataType: "json"
		}).done(function( json ) {alert("done");
			var data = eval("("+json+")");
			if (data.flag)
				location.reload();
			else
				alert("标记消息为已读时出错");
		}).fail(function() {
			alert("FAIL");
		}).error(function (XMLHttpRequest, textStatus, errorThrown) {
			$("#ajax").html(XMLHttpRequest.responseText);
		});
	}
</script>
</head>
</head>
<body>
<div id="main">
<div id="unread-list">
	<s:iterator value="#request.unreadList" id="msg">
		<s:if test="noticeType==0">
			<div class="new-message">${senderName}:${word} -- ${time}<button onclick="muteMessage(${mid},${noticeType})">read</button></div>
		</s:if>
		<s:if test="noticeType==7">
			<div class="new-publisher-apply">${senderName} ${word} -- ${time} <a href="upgradePublisher?upgrade=true&uid=${senderUid}">agree</a> <a href="upgradePublisher?upgrade=false&uid=${senderUid}">refuse</a></div>
		</s:if>
	</s:iterator>
</div>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${USER_SESSION.name} 的消息列表</title>
<style type="text/css">
	#main {
	margin-left: 150px;
	margin-right: 50px;
}
a {
	margin-left: 10px;
	text-decoration: none;
	background-color: rgb(218, 218, 218);
}
a:hover {
	background-color: #A8A6A6;
}
#panel {
	position: fixed;
	top: 70px;
	left: 10px;
	height: 50px;
	width: 180px;
	background-color: rgb(255, 221, 148);
	padding: 10px;
}
#panel div {
	cursor: pointer;
}
#panel .urm {
	margin: 0 0 10px 0;
}
#panel .rm {
	margin: 10px 0 0 0;
}
#panel .hover {
	background-color: yellow;
}
#panel .selected {
	background-color: rgb(241, 241, 241);
}
#unread-list {
	margin:30px 0 20px 70px;
	padding: 5px;
	border: 3.5px solid orange;
}
#read-list {
	margin:30px 0 20px 70px;
	padding: 5px;
	border: 3.5px solid grey;
	display: none;
}
.new-message {
	margin-top: 10px;
	padding: 5px;
	background-color: rgba(180, 201, 180, 0.3);
}
.order-give {
	margin-top: 10px;
	padding: 5px;
	background-color: rgba(180, 201, 180, 0.3);
}
.new-publisher-apply {
	margin-top: 10px;
	padding: 5px;
	background-color: rgba(180, 201, 180, 0.3);
}
.good-suspended {
	margin-top: 10px;
	padding: 5px;
	background-color: rgba(180, 201, 180, 0.3);
}
.good-deleted {
	margin-top: 10px;
	padding: 5px;
	background-color: rgba(180, 201, 180, 0.3);
}
</style>
<script type="text/javascript"	src="/TradeSystem/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#panel>div").hover(function() {
			$(this).toggleClass("hover");
		}, function() {
			$(this).toggleClass("hover");
		});
		$("#panel>.urm").click(function() {
			$(this).toggleClass("selected", true);
			$("#panel>.rm").toggleClass("selected", false);
			$("#unread-list").show();
			$("#read-list").hide();
		});
		$("#panel>.rm").click(function() {
			$(this).toggleClass("selected", true);
			$("#panel>.urm").toggleClass("selected", false);
			$("#read-list").show();
			$("#unread-list").hide();
		});
	});

	function muteMessage(mid,noticeType) {
		$.ajax( {
			type: "POST",
			url: "muteMessage",
			data: { mid: mid, noticeType: noticeType },
			dataType: "json"
		}).done(function( json ) {
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
<div id="panel">
	<div class="selected urm">显示未读的信息</div>
	<div class="rm">显示已读的信息</div>
</div>
<div id="unread-list">未读的消息
	<s:iterator value="#request.unreadList" id="msg">
		<s:if test="noticeType==0">
			<div class="new-message">${senderName}:${word} -- ${time}<button onclick="muteMessage(${mid},${noticeType})">read</button></div>
		</s:if>
		<s:if test="noticeType==1">
			<div class="order-give">${word} -- ${time}<button onclick="muteMessage(${mid},${noticeType})">read</button></div>
		</s:if>
		<s:if test="noticeType==2">
			<div class="new-publisher-apply">${senderName} ${word} -- ${time} <button onclick="muteMessage(${mid},${noticeType})">read</button></div>
		</s:if>
		<s:if test="noticeType==3">
			<div class="good-suspended">${senderName} ${word} -- ${time} <button onclick="muteMessage(${mid},${noticeType})">read</button></div>
		</s:if>
		<s:if test="noticeType==4">
			<div class="good-deleted">${senderName} ${word} -- ${time} <a href="upgradePublisher?upgrade=true&uid=${senderUid}">agree</a> <a href="upgradePublisher?upgrade=false&uid=${senderUid}">refuse</a></div>
		</s:if>
	</s:iterator>
</div>
<div id="read-list">已读的消息
	<s:iterator value="#request.readList" id="msg">
		<s:if test="noticeType==0">
			<div class="new-message">${senderName}:${word} -- ${time}</div>
		</s:if>
		<s:if test="noticeType==1">
			<div class="order-give">${word} -- ${time}</div>
		</s:if>
		<s:if test="noticeType==2">
			<div class="new-publisher-apply">${senderName} ${word} -- ${time}</div>
		</s:if>
		<s:if test="noticeType==3">
			<div class="good-suspended">${senderName} ${word} -- ${time}</div>
		</s:if>
		<s:if test="noticeType==4">
			<div class="good-deleted">${senderName} ${word} -- ${time}</div>
		</s:if>
	</s:iterator>
</div>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
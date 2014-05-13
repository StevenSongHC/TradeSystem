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
	top: 175px;
	left: 10px;
	height: 80px;
	width: 180px;
	background-color: rgb(255, 221, 148);
	padding: 10px;
}
#panel div {
	cursor: pointer;
	margin: 0 0 10px 0;
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
#write-message {
	width: 30%;
	margin: 30px 0 0 90px;
	display: none;
}
#message-content {
	width: 100%;
	height: 60px;
	background-color: rgb(251, 255, 236);
	overflow-y: auto;
}
.tip {
	margin: 0 5px 0 10px;
}
.error-message {
	color: red;
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
			$("#panel>.write-messag").toggleClass("selected", false);
			$("#unread-list").show();
			$("#read-list").hide();
			$("#write-message").hide();
		});
		$("#panel>.rm").click(function() {
			$(this).toggleClass("selected", true);
			$("#panel>.urm").toggleClass("selected", false);
			$("#panel>.write-messag").toggleClass("selected", false);
			$("#read-list").show();
			$("#unread-list").hide();
			$("#write-message").hide();
		});
		$("#panel>.write-message").click(function() {
			$(this).toggleClass("selected", true);
			$("#panel>.rm").toggleClass("selected", false);
			$("#panel>.urm").toggleClass("selected", false);
			$("#read-list").hide();
			$("#unread-list").hide();
			$("#write-message").show();
		});
		
		$("#receiver-type").change(function() {
			var rt = $(this).val();
			switch ($(this).val()) {
				case 'user' :
					$(".tip").show();
					$("#receiver-code").show();
					$(".tip").html("请输入用户名称");
					break;
				case 'publisher' :
					$(".tip").show();
					$("#receiver-code").show();
					$(".tip").html("请输入商家的联系方式");
					break;
				case 'admin' :
					$(".tip").hide();
					$("#receiver-code").hide();
					break;
				default:
					$(".tip").show();
					$(".tip").html("切换出错，请重新操作!");
					$("#receiver-code").hide();
			}
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
	
	function sendMessage() {
		$.ajax( {
			type: "POST",
			url: "sendMessage",
			data: { receiverType : $("#receiver-type").children("option:selected").val(), receiverCode : $("#receiver-code").val(), content : $("#message-content").html()  },
			dataType: "json"
		}).done(function( json ) {
			var data = eval("("+json+")");
			if (data.flag) {
				$(".error-message").html("");
				$("#message-content").html("");
				alert("发送成功");
			}
			else
				$(".error-message").html(data.msg);
		}).fail(function() {
			alert("FAIL");
		}).error(function (XMLHttpRequest, textStatus, errorThrown) {
			$("#ajax").html(XMLHttpRequest.responseText);
		});
	}
</script>
</head>
<body>
<jsp:include page="header.jsp" />
<div id="main">
<div id="panel">
	<div class="selected urm">显示未读的信息</div>
	<div class="rm">显示已读的信息</div>
	<div class="write-message">写信</div>
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
<div id="write-message">发送消息给 
	<select id="receiver-type">
		<option value="user" selected="selected">用户</option>
		<option value="publisher">商家</option>
		<option value="admin">管理员</option>
	</select>
	<span class="tip">请输入用户名称</span><input type="text" id="receiver-code" /><span class="error-message"></span>
	<div contentEditable="true" id="message-content"></div>
	<button onclick="sendMessage()">send</button>
</div>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
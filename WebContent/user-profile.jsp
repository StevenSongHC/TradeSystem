<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
String basepath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>setting</title>
<link rel="stylesheet" type="text/css" href="/TradeSystem/css/user_profile.css">
<script type="text/javascript"	src="/TradeSystem/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"	src="/TradeSystem/js/jquery.form.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	
	var options = {
			url: "upload",
			success: function(json) {
				var data = eval("(" + json + ")");
				$(".pic-panel .upload-status").html(data.msg);
				$("#pic-preview").attr({"src" : "<%=basepath%>/" + data.picPath, "path" : data.picPath});
				updateProfile();
			}
		};
		$("#pic-form").ajaxForm(options);
		
});

function uploadPic() {
	var filePath = $(".pic-panel #pic").val();
	var fileType = filePath.substring(filePath.lastIndexOf("."),filePath.length).toUpperCase();
	if(fileType != ".BMP" && fileType != ".PNG" && fileType != ".GIF" && fileType != ".JPG" && fileType != ".JPEG")
		$(".pic-panel .upload-status").html("请选择图片文件");
	else {
		$("#pic-form #fileType").val(fileType);
		$(".pic-panel .upload-status").html("上传中");
		$("#pic-form").submit();
	}
}

function updateProfile() {
	$.ajax( {
		type: "POST",
		url: "doUpdateProfile",
		data: { summary : $("#summary").val(), password : $("#password").val(), portrait : $("#pic-preview").attr("path") },
		dataType: "json"
	}).done(function( json ) {
		var data = eval("("+json+")");
		$("#status").html(data.msg);
	}).fail(function() {
		$("#status").html("更新出错");
	}).error(function (XMLHttpRequest, textStatus, errorThrown) {
		$("#ajax").html(XMLHttpRequest.responseText);
	});
}
</script>
</head>
<body>
<jsp:include page="user-info-box.jsp" />
<div>头像
	<div class="pic-panel">
		<img alt="默认" src="<%=basepath%>/${user.portrait}" id="pic-preview" title="当前头像" path="${user.portrait}" />
		<form id="pic-form" method="post" enctype="multipart/form-data">
			<input type="file" name="file" id="pic" />
			<input type="hidden" name="type" value="userPortrait" />
			<input type="hidden" name="fileType" id="fileType" />
			<input type="button" value="上传并保存" onclick="uploadPic()" />
		</form>
		<span class="upload-status"></span>
	</div>
</div>
<div id="name">
${user.name}
</div>
<div>简介
<textarea id="summary">${user.summary}</textarea>
</div>
<div>重置密码
<input type="password" id="password">
</div>
<button onclick="updateProfile()">save</button>
<span id="status"></span>

<jsp:include page="footer.jsp" />
</body>
</html>
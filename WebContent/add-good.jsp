<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
String basepath = request.getContextPath();
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/TradeSystem/css/add_good.css">
<script type="text/javascript"	src="/TradeSystem/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"	src="/TradeSystem/js/jquery.form.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {

	var options = {
		url: "publisherUpload",
		success: function(json) {
			var data = eval("(" + json + ")");
			$(".pic-panel .upload-status").html(data.msg);
			$("#pic-preview").attr({"src" : "<%=basepath%>/" + data.picPath, "path" : data.picPath});
		}
	};
	$("#pic-form").ajaxForm(options);
	
});

function uploadPic() {
	var filePath = $(".pic-panel #pic").val();
	var fileType = filePath.substring(filePath.lastIndexOf("."),filePath.length).toUpperCase();
	if(fileType != ".BMP" && fileType != ".PNG.GIF" && fileType != ".JPG" && fileType != ".JPEG")
		$(".pic-panel .upload-status").html("请选择图片文件");
	else {
		$("#pic-form #fileType").val(fileType);
		$(".pic-panel .upload-status").html("上传中");
		$("#pic-form").submit();
		saveGood(true);
	}
}

function saveGood(skipCheck) {
	if (($("#title").val().trim() != "" && $("#pic-preview").attr("path").trim() != "" && $("#price").val().trim() != "" && $("#price").val().trim().match(/^\+?(:?(:?\d+\.\d+)|(:?\d+))$/)) || skipCheck) {
		$.ajax( {
			type: "POST",
			url: "saveGood",
			data: { title: $("#title").val(), pic : $("#pic-preview").attr("path"), price : $("#price").val(), desc : $("#desc").val() },
			dataType: "json"
		}).done(function( json ) {
			$("#error-field").html("");
			$("#msg-field").html(new Date());
		}).fail(function() {
			$("#error-field").html("保存商品信息出错");
			$("#msg-field").html("");
		}).error(function (XMLHttpRequest, textStatus, errorThrown) {
			$("#ajax").html(XMLHttpRequest.responseText);
		});
	}
	else {
		$("#error-field").html("illege form");
		$("#msg-field").html("");
	}
}

</script>
<title>新增一件商品</title>
</head>
<body>
<jsp:include page="user-info-box.jsp" />

<div id="main">
	<p>商品名称*<input type="text" name="title" id="title"></p>
	<p>价格*
		<input type="text" name="price" id="price" />
		<select name="currency">
		<option>RMB</option>
		</select>
	</p>
	<div>图片
		<div class="pic-panel">
		<img alt="默认" src="<%=basepath%>/images/good/nopic.jpg" id="pic-preview" path="images/good/nopic.jpg" />
		<form id="pic-form" method="post" enctype="multipart/form-data">
			<input type="file" name="file" id="pic" />
			<input type="hidden" name="type" value="goodPic" />
			<input type="hidden" name="fileType" id="fileType" />
			<input type="button" value="上传新图片" onclick="uploadPic()" />
		</form>
		<span class="upload-status"></span>
		</div>
	</div>
	<p>描述</p>
	<p><textarea rows="10" cols=100% name="desc" id="desc"></textarea></p>
	<input type="button" value="保存" onclick="saveGood(false)"/>
	<input type="button" value="提交" />
	<span id="error-field"></span><span id="msg-field"></span>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
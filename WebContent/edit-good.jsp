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
			saveGood(true);
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

function saveGood(skipCheck) {
	if (($("#title").val().trim() != "" && $("#pic-preview").attr("path").trim() != "" && $("#price").val().trim() != "" && $("#price").val().trim().match(/^\+?(:?(:?\d+\.\d+)|(:?\d+))$/)) || skipCheck) {
		$.ajax( {
			type: "POST",
			url: "saveGood",
			data: { gid : $("#gid").val(), title: $("#title").val(), pic : $("#pic-preview").attr("path"), price : $("#price").val(), desc : $("#desc").val() },
			dataType: "json"
		}).done(function( json ) {
			var data = eval("("+json+")");
			$("#error-field").html(data.error);
			$("#msg-field").html(data.msg);
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

function publishGood() {
	if (($("#title").val().trim() != "" && $("#pic-preview").attr("path").trim() != "" && $("#price").val().trim() != "" && $("#price").val().trim().match(/^\+?(:?(:?\d+\.\d+)|(:?\d+))$/)))
		window.location.href = "publishGood?gid="+$("#gid").val()+"&title="+$("#title").val()+"&pic="+$("#pic-preview").attr("path")+"&price="+$("#price").val()+"&desc="+$("#desc").val();
	else
		alert("illege form");
}

</script>
<title>编辑商品</title>
</head>
<body>
<jsp:include page="user-info-box.jsp" />

${msg}
<div id="main">
	<input type="hidden" id="gid" value="${good.id}" />
	<p>商品名称*<input type="text" name="title" id="title" value="${good.title}"/></p>
	<p>价格*
		<input type="text" name="price" id="price"  value="${good.price}" />
		<select name="currency">
		<option>RMB</option>
		</select>
	</p>
	<div>图片
		<div class="pic-panel">
		<img alt="默认" src="<%=basepath%>/${good.pic}" id="pic-preview" path="${good.pic}" />
		<form id="pic-form" method="post" enctype="multipart/form-data">
			<input type="file" name="file" id="pic" />
			<input type="hidden" name="type" value="goodPic" />
			<input type="hidden" name="fileType" id="fileType" />
			<input type="button" value="上传并保存" onclick="uploadPic()" />
		</form>
		<span class="upload-status"></span>
		</div>
	</div>
	<p>描述</p>
	<p><textarea rows="10" cols=100% name="desc" id="desc">${good.desc}</textarea></p>
	<input type="button" value="保存" onclick="saveGood(false)"/>
	<input type="button" value="提交" onclick="publishGood()" />
	<span id="error-field"></span><span id="msg-field"></span>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
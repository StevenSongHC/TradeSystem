<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<% 
String basepath = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="/TradeSystem/css/publisher_homepage.css">
<script type="text/javascript">
$(document).ready(function() {
	
	$("#page-size,#date-order").change(function() {
		getPage();
	});
	
	registerInteractiveAction();
	
	$("#page-size").val(${pageSizeValue});
	$("#date-order").val(${dateOrderValue});
	
});

function getPage(currentPage) {
	if (currentPage == 0)
		currentPage = $("#current-page").html();
	// act
	$.ajax( {
		type: "POST",
		url: "getPage",
		data: { naviType : "good", currentPage : currentPage, pid : ${publisher.id}, pageSize : $("#page-size").children("option:selected").val(), dateOrder : $("#date-order").children("option:selected").val() },
		dataType: "json"
	}).done(function( json ) {
		var data = eval("("+json+")");
		$(".page-navi").html(data.pageNavi);
		$("#good-item").html("");
		$.each(data.content, function(i, item) {
			$("#good-item").append("<hr><div class='item'><div class='title'><a href='<%=basepath%>/g/" + item.id + "'>" + item.title + "</a></div><div class='price'>" + item.price + "<span class='currency'>RMB</span></div><div class='pic'><img src='<%=basepath%>/" + item.pic + "' /></div><div class='desc'>" + item.desc + "</div><div class='date'>" + item.addTime.date + "</div><div style='clear: both'></div></div>");
		});
		
		registerInteractiveAction();
	}).fail(function() {
		alert("FAIL");
	}).error(function (XMLHttpRequest, textStatus, errorThrown) {
		$("#ajax").html(XMLHttpRequest.responseText);
	});
}

function registerInteractiveAction() {
	$(".item").hover(function() {
		$(this).css("border", "3px solid rgb(209, 236, 179)");
	}, function() {
		$(this).css("border", "0px solid rgb(209, 236, 179)");
	});
	$(".page-navi>.first,.page-navi>.last").click(function() {
		getPage(parseInt($(this).attr("which-page")));
	});
	$(".page-navi>.counter-base,.page-navi>.counter-next").click(function() {
		getPage(parseInt($(this).attr("which-page")));
	});
	$(".page-navi>.counter").click(function() {
		getPage(parseInt($(".page-navi>.counter-base").attr("which-page")) + parseInt($(this).attr("which-page")));
	});
	
	$(".page-navi>.first,.page-navi>.last").hover(function() {
		$(this).css({"color" : "#FFF", "background-color" : "rgb(190, 240, 255)"});
	}, function() {
		$(this).css({"color" : "rgb(190, 240, 255)", "background-color" : "#FFF"});
	});
	$(".page-navi>.counter").hover(function() {
		$(this).addClass("hover");
	}, function() {
		$(this).removeClass("hover");
	});
}
</script>

<h1>${publisher.name}</h1>
<div class="summary">${publisher.summary}</div>
<div class="good-list">
	<span class="add-good"><a href="addGood">添加商品</a></span>
	<span class="func-bar">
		<span>每页显示记录数
		<select id="page-size">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
			<option value="7">7</option>
			<option value="8">8</option>
			<option value="9">9</option>
			<option value="10">10</option>
		</select>
		</span>
		<span>排序方式
		<select id="date-order">
			<option value="-1">按发布时间逆排</option>
			<option value="1">按发布时间顺排</option>
		</select>
		</span>
	</span>
	<hr>
	<div id="good-item">
	<s:iterator value="#request.pageNavi.result" id="good">
	<hr>
	<div class="item">
		<div class="title"><a href="<%=basepath%>/g/${good.id}">${good.title}</a></div>
		<div class="price">${good.price}<span class="currency">RMB</span></div>
		<div class="pic"><img src="<%=basepath%>/${good.pic}" /></div>
		<div class="desc">${good.desc}</div>
		<div class="date">${good.addTime}</div>
		<div style="clear: both"></div>
	</div>
	</s:iterator>
	</div>
	<hr>
	<div class="page-info">共${pageNavi.allRow}条记录,每页显示${pageNavi.pageSize}条,一共${pageNavi.totalPage}页,当前第${pageNavi.currentPage}页</div>
	<div class="page-navi">
		${pageNavi.naviBar}
	</div>
</div>
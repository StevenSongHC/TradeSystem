<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<% 
String basepath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>C.C.S 管理界面</title>
<link rel="stylesheet" type="text/css" href="/TradeSystem/css/good_list.css">
<style type="text/css">
h4 {
	background-color: rgb(179, 184, 178);
}
.title {
	margin-left: 10px;
	font-size: 13px;
	font-weight: normal;
	background-color: rgb(211, 214, 210);
}
table {
	text-align: center;
}
table img {
	width: 50px;
}
table .status {
	width: 70px;
}
.t {
	background-color: rgb(243, 86, 86);
	color: rgb(243, 86, 86);
	cursor: pointer;
}
.f {
	background-color: rgb(174, 255, 100);
	color: rgb(174, 255, 100);
	cursor: pointer;
}
</style>
<script type="text/javascript"	src="<%=basepath%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$(".t").hover(function() {
		$(this).css({"background-color" : "rgb(128, 250, 16)", "color" : "rgb(128, 250, 16)"});
	}, function() {
		$(this).css({"background-color" : "rgb(243, 86, 86)", "color" : "rgb(243, 86, 86)"});
	});
	$(".f").hover(function() {
		$(this).css({"background-color" : "rgb(250, 25, 25)", "color" : "rgb(250, 25, 25)"});
	}, function() {
		$(this).css({"background-color" : "rgb(174, 255, 100)", "color" : "rgb(174, 255, 100)"});
	});
	
	
	///////////////////////////////////////////////////////////////////////
	/////////////////////////   query user list   /////////////////////////
	///////////////////////////////////////////////////////////////////////
	var filterArray = [2,2,2,2];		// filter-arg-array
	$("#u-page-size").change(function() {																		//........query by size........
		getPage("user", 
				$(this).children("option:selected").val(), 
				$("#user-list>input[type='hidden']").val(), 
				parseIntArray2String(filterArray), 
				$("input[type='radio'][name='user-list-sort']:checked").val());
	});
	$("input[type='radio'][name='user-list-sort']").click(function() {											//........query by sort solution........
		getPage("user", 
				$("#u-page-size").children("option:selected").val(), 
				$("#user-list>input[type='hidden']").val(), 
				parseIntArray2String(filterArray), 
				$(this).val());
	});
	$("input[type='checkbox'][name='user-list-filter']").click(function() {										//........query by filter........
		// initial
		var c = 0;																						// counter
		var n = parseInt($("input[type='checkbox'][name='user-list-filter']").last().attr("pos"))+1;	// loop
		for (var i=0; i<n; i++) {
			$("input[type='checkbox'][name='user-list-filter'][pos=" + i + "]:checked").each(function() {
				c += parseInt($(this).val())+1;
			});
			filterArray[i] = c!=0? c-1 : 2;
			c = 0;
		}
		getPage("user", 
				$("#u-page-size").children("option:selected").val(), 
				$("#user-list>input[type='hidden']").val(), 
				parseIntArray2String(filterArray), 
				$("input[type='radio'][name='user-list-sort']:checked").val());
	});
	$("#user-list>.page-navi>.counter").click(function() {														//........query by page........
		getPage("user", 
				$("#u-page-size").children("option:selected").val(), 
				parseInt($("#user-list>.page-navi>.counter-base").attr("which-page"))+parseInt($(this).attr("which-page")), 
				parseIntArray2String(filterArray), 
				$("input[type='radio'][name='user-list-sort']:checked").val());
	});
	$("#user-list>.page-navi>.first,#user-list>.page-navi>.last,#user-list>.page-navi>.counter-base,#user-list>.page-navi>.counter-next").click(function() {
		getPage("user", 
				$("#u-page-size").children("option:selected").val(), 
				parseInt($(this).attr("which-page")), 
				parseIntArray2String(filterArray), 
				$("input[type='radio'][name='user-list-sort']:checked").val());
	});
	
	
});

function getPage(type, size, currentPage, filterArray, sortArray) {
	$.ajax( {
		type: "POST",
		url: "queryPage",
		data: { naviType : type, pageSize : size, currentPage : currentPage, filterArray : filterArray, sortArray : sortArray },
		dataType: "json"
	}).done(function( json ) {
		var data = eval("("+json+")");
		$(".page-navi").html(data.pageNavi);
		$("#good-item").html("");
		$.each(data.content, function(i, item) {
			$("#good-item").append("<hr><div class='item'><div class='title'><a href='<%=basepath%>/g/" + item.id + "'>" + item.title + "</a><span class='buyer-count'>(" + item.buyerCount + ")</span></div><div class='price'>" + item.price + "<span class='currency'>RMB</span></div><div class='pic'><img src='<%=basepath%>/" + item.pic + "' /></div><div class='desc'>" + item.desc + "</div><div class='date'>" + item.addTime.date + "</div><div style='clear: both'></div></div>");
		});
		
		registerInteractiveAction();
	}).fail(function() {
		alert("FAIL");
	}).error(function (XMLHttpRequest, textStatus, errorThrown) {
		$("#ajax").html(XMLHttpRequest.responseText);
	});
}

function parseIntArray2String(array) {
	var str = "";
	for (var i=0; i<array.length; i++) {
		str += array[i]+",";
	}
	return str;
}
</script>
</head>
<jsp:include page="user-info-box.jsp" />
<body>

<div id="user-list">
<h4>用户列表 (${userList.allRow})
	<span class="title">每页显示数</span>
	<select id="u-page-size">
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5" selected="selected">5</option>
		<option value="6">6</option>
		<option value="7">7</option>
		<option value="8">8</option>
		<option value="9">9</option>
		<option value="10">10</option>
	</select>
</h4>
<input type="hidden" value="${userList.currentPage}" />
<table border="1" width="30%" align="center">
	<tr>
		<td><input type="radio" name="user-list-sort" checked="checked" value="id,0" />↑ | ↓<input type="radio" name="user-list-sort" value="id,1" /></td>
		<td></td>
		<td><input type="radio" name="user-list-sort" value="name,0" />↑ | ↓<input type="radio" name="user-list-sort" value="name,1" /></td>
		<td><input type="checkbox" name="user-list-filter" pos=0 value="1" />1 | 0<input type="checkbox" name="user-list-filter" pos=0 value="0" /></td>
		<td><input type="checkbox" name="user-list-filter" pos=1 value="1" />1 | 0<input type="checkbox" name="user-list-filter" pos=1 value="0" /></td>
		<td><input type="checkbox" name="user-list-filter" pos=2 value="1" />1 | 0<input type="checkbox" name="user-list-filter" pos=2 value="0" /></td>
		<td><input type="checkbox" name="user-list-filter" pos=3 value="1" />1 | 0<input type="checkbox" name="user-list-filter" pos=3 value="0" /></td>
	</tr>
	<tr>
		<td>uid</td>
		<td>u_portraits</td>
		<td>u_name</td>
		<td class="status">suspend</td>
		<td class="status">delete</td>
		<td class="status">restrict</td>
		<td>publisher?</td>
	</tr>
<s:iterator value="#request.userList.result" id="user">
	<tr>
		<td>${user.id}</td>
		<td><img src="<%=basepath%>/${user.portrait}" title="${user.name}'s portrait" /></td>
		<td><a href="<%=basepath%>/u/${user.account}" target="_blank">${user.name}</a></td>
		<td><span class="<s:if test="#user.isSuspend">t</s:if><s:else>f</s:else>">01</span></td>
		<td><span class="<s:if test="#user.isDelete">t</s:if><s:else>f</s:else>">01</span></td>
		<td><span class="<s:if test="#user.isRestrict">t</s:if><s:else>f</s:else>">01</span></td>
		<td><span style="<s:if test="#user.isPublisher">background-color:rgb(243, 86, 86);color:rgb(243, 86, 86)</s:if><s:else>background-color:rgb(174, 255, 100);color:rgb(174, 255, 100)</s:else>;cursor:default">01</span></td>
	</tr>
</s:iterator>
</table>
<div class="page-navi">
${userList.naviBar}
</div>
</div>

<div id="publisher-list">
<h4>发布者列表 (${publisherList.allRow})
	<span class="title">每页显示数</span>
	<select id="p-page-size">
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5" selected="selected">5</option>
		<option value="6">6</option>
		<option value="7">7</option>
		<option value="8">8</option>
		<option value="9">9</option>
		<option value="10">10</option>
	</select>
</h4>
<input type="hidden" value="${publisherList.currentPage}" />
<table border="1" width="40%" align="center">
	<tr>
		<td>pid</td>
		<td>uid</td>
		<td>p_name</td>
		<td>p_contact</td>
		<td>p_joinDate</td>
		<td>p_goodCount</td>
		<td class="status">restrict</td>
	</tr>
<s:iterator value="#request.publisherList.result" id="publisher">
	<tr>
		<td>${publisher.id}</td>
		<td>${publisher.uid}</td>
		<td><a href="<%=basepath%>/p/${publisher.id}" target="_blank">${publisher.name}</a></td>
		<td>${publisher.contact}</td>
		<td>${publisher.joinDate}</td>
		<td>${publisher.goodCount}</td>
		<td><span class="<s:if test="#publisher.isRestricted">t</s:if><s:else>f</s:else>">01</span></td>
	</tr>
</s:iterator>
</table>
<div class="page-navi">
${publisherList.naviBar}
</div>
</div>

<div id="good-list">
<h4>商品列表 (${goodList.allRow})
	<span class="title">每页显示数</span>
	<select id="g-page-size">
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5" selected="selected">5</option>
		<option value="6">6</option>
		<option value="7">7</option>
		<option value="8">8</option>
		<option value="9">9</option>
		<option value="10">10</option>
	</select>
</h4>
<input type="hidden" value="${goodList.currentPage}" />
<table border="1" width="50%" align="center">
	<tr>
		<td>gid</td>
		<td>pid</td>
		<td>g_pic</td>
		<td>g_title</td>
		<td>g_price</td>
		<td>g_addTime</td>
		<td>g_buyerCount</td>
		<td>online[agree](m)</td>
		<td>delete(m)</td>
	</tr>
<s:iterator value="#request.goodList.result" id="good">
	<tr>
		<td>${good.id}</td>
		<td><a href="<%=basepath%>/p/${good.publisherId}" target="_blank">${good.publisherId}</a></td>
		<td><img src="<%=basepath%>/${good.pic}" title="${good.title}'s pic" alt="!!!!" /></td>
		<td><a href="<%=basepath%>/g/${good.id}" target="_blank">${good.title}</a></td>
		<td>${good.price} RMB</td>
		<td>${good.addTime}</td>
		<td>${good.buyerCount}</td>
		<td class="status"><s:if test="#good.isAgree==true"><s:if test="#good.isComplete==false">cp </s:if><s:else><s:if test="#good.isAvailable==false">av </s:if></s:else></s:if><span class="<s:if test="#good.isComplete && #good.isAgree && #good.isAvailable && !#good.isDelete">f</s:if><s:else>t</s:else>">01</span></td>
		<td class="status"><span class="<s:if test="#good.isDelete">t</s:if><s:else>f</s:else>">01</span></td>
	</tr>
</s:iterator>
</table>
<div class="page-navi">
${goodList.naviBar}
</div>
</div>
<br><br><br>
<jsp:include page="footer.jsp" />
</body>
</html>
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
<link rel="stylesheet" type="text/css" href="/TradeSystem/css/admin_page.css">
<script type="text/javascript"	src="<%=basepath%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
var userFilterArray = [2,2,2,2];		// filter-arg-array
var publisherFilterArray = [2,2];
$(document).ready(function() {
	registerInteractiveAction();
	
	///////////////////////////////////////////////////////////////////////
	/////////////////////////   query user list   /////////////////////////
	///////////////////////////////////////////////////////////////////////
	$("#u-page-size").change(function() {																		//........query by size........
		getPage("user", 
				$(this).children("option:selected").val(), 
				$("#user-list>input[type='hidden']").val(), 
				parseIntArray2String(userFilterArray), 
				$("input[type='radio'][name='user-list-sort']:checked").val());
	});
	$("input[type='radio'][name='user-list-sort']").click(function() {											//........query by sort solution........
		getPage("user", 
				$("#u-page-size").children("option:selected").val(), 
				$("#user-list>input[type='hidden']").val(), 
				parseIntArray2String(userFilterArray), 
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
			userFilterArray[i] = c!=0? c-1 : 2;
			c = 0;
		}
		getPage("user", 
				$("#u-page-size").children("option:selected").val(), 
				$("#user-list>input[type='hidden']").val(), 
				parseIntArray2String(userFilterArray), 
				$("input[type='radio'][name='user-list-sort']:checked").val());
	});
	
	///////////////////////////////////////////////////////////////////////
	//////////////////////   query publisher list   ///////////////////////
	///////////////////////////////////////////////////////////////////////
	$("#p-page-size").change(function() {																		//........query by size........
		getPage("publisher", 
				$(this).children("option:selected").val(), 
				$("#publisher-list>input[type='hidden']").val(), 
				parseIntArray2String(publisherFilterArray), 
				$("input[type='radio'][name='publisher-list-sort']:checked").val());
	});
	$("input[type='radio'][name='publisher-list-sort']").click(function() {											//........query by sort solution........
		getPage("publisher", 
				$("#p-page-size").children("option:selected").val(), 
				$("#publisher-list>input[type='hidden']").val(), 
				parseIntArray2String(publisherFilterArray), 
				$(this).val());
	});
	$("input[type='checkbox'][name='publisher-list-filter']").click(function() {										//........query by filter........
		// initial
		var c = 0;																						// counter
		var n = parseInt($("input[type='checkbox'][name='publisher-list-filter']").last().attr("pos"))+1;	// loop
		for (var i=0; i<n; i++) {
			$("input[type='checkbox'][name='publisher-list-filter'][pos=" + i + "]:checked").each(function() {
				c += parseInt($(this).val())+1;
			});
			publisherFilterArray[i] = c!=0? c-1 : 2;
			c = 0;
		}
		getPage("publisher", 
				$("#p-page-size").children("option:selected").val(), 
				$("#publisher-list>input[type='hidden']").val(), 
				parseIntArray2String(publisherFilterArray), 
				$("input[type='radio'][name='publisher-list-sort']:checked").val());
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
		if (data.dataType == "user") {
			$("#user-list>table>tbody>.data-row").remove();
			$.each(data.result, function(i, u) {
				var col1 = "<td>" + u.id + "</td>";
				var col2 = "<td><img src=\"<%=basepath%>/" + u.portrait + "\" title=\"" + u.name + "'s portrait\" /></td>";
				var col3 = "<td><a href=\"<%=basepath%>/u/" + u.account + "\" target=\"_blank\">" + u.name + "</a></td>";
				var col4 = "<td><span class=\"" + (u.isSuspend? "t" : "f") + "\" data-coor=\"user," + u.id + ",0\">01</span></td>";
				var col5 = "<td><span class=\"" + (u.isDelete? "t" : "f") + "\" data-coor=\"user," + u.id + ",1\">01</span></td>";
				var col6 = "<td><span class=\"" + (u.isRestricted? "t" : "f") + "\" data-coor=\"user," + u.id + ",2\">01</span></td>";
				var col7 = "<td><span class=\"" + (u.isPublisher? "st" : "sf") + "\">01</span></td>";
				$("#user-list>table>tbody>tr:last").after("<tr class=\"data-row\">" + col1 + col2 + col3 + col4 + col5 + col6 + col7 + "</tr>");
			});
			$("#user-list>.page-navi").html(data.naviBar);
		}
		else if (data.dataType == "publisher") {
			$("#publisher-list>table>tbody>.data-row").remove();
			$.each(data.result, function(i, p) {
				var col1 = "<td>" + p.id + "</td>";
				var col2 = "<td>" + p.uid + "</td>";
				var col3 = "<td><a href=\"<%=basepath%>/p/" + p.id + "\" target=\"_blank\">" + p.name + "</a></td>";
				var col4 = "<td>" + p.contact + "</td>";
				var col5 = "<td>" + p.joinDate + "</td>";
				var col6 = "<td>" + p.goodCount + "</td>";
				var col7 = "<td><span class=\"" + (p.isActivate? "t" : "f") + "\" data-coor=\"publisher," + p.id + ",0\">01</span></td>";
				var col8 = "<td><span class=\"" + (p.isRestricted? "t" : "f") + "\" data-coor=\"publisher," + p.id + ",1\">01</span></td>";
				$("#publisher-list>table>tbody>tr:last").after("<tr class=\"data-row\">" + col1 + col2 + col3 + col4+ col5+ col6+ col7 + col8 + "</tr>");
			});
			$("#publisher-list>.page-navi").html(data.naviBar);
		}
		
		registerInteractiveAction();
	}).fail(function() {
		alert("FAIL");
	}).error(function (XMLHttpRequest, textStatus, errorThrown) {
		$("#ajax").html(XMLHttpRequest.responseText);
	});
}

function toggleStatus(statusCoor) {		// statusCoor = entityType + uid + dataColumn + Columnstatus
	$.ajax( {
		type: "POST",
		url: "toggleStatus",
		data: {statusCoor : statusCoor},
		dataType: "json"
	}).success(function( json ) {
		var data = eval("("+json+")");
		if (!data.isSucceed)
			alert("操作失败");
		
		registerInteractiveAction();
	}).fail(function() {
		alert("FAIL");
	}).error(function (XMLHttpRequest, textStatus, errorThrown) {
		$("#ajax").html(XMLHttpRequest.responseText);
	});
}

function registerInteractiveAction() {
	// hover style
	$(".t").hover(function() {
		$(this).toggleClass("t", false).toggleClass("t-h", true).toggleClass("f", false).toggleClass("f-h", false).css("cursor", "pointer");
	}, function() {
		$(this).toggleClass("t", true).toggleClass("t-h", false).toggleClass("f", false);
	});
	$(".f").hover(function() {
		$(this).toggleClass("f", false).toggleClass("f-h", true).toggleClass("t", false).toggleClass("t-h", false).css("cursor", "pointer");
	}, function() {
		$(this).toggleClass("f", true).toggleClass("f-h", false).toggleClass("t", false);
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
	
	// page navi-bar click action
	$("#user-list>.page-navi>.counter").click(function() {														//........query by page........
		getPage("user", 
				$("#u-page-size").children("option:selected").val(), 
				parseInt($("#user-list>.page-navi>.counter-base").attr("which-page"))+parseInt($(this).attr("which-page")), 
				parseIntArray2String(userFilterArray), 
				$("input[type='radio'][name='user-list-sort']:checked").val());
	});
	$("#user-list>.page-navi>.first,#user-list>.page-navi>.last,#user-list>.page-navi>.counter-base,#user-list>.page-navi>.counter-next").click(function() {
		getPage("user", 
				$("#u-page-size").children("option:selected").val(), 
				parseInt($(this).attr("which-page")), 
				parseIntArray2String(userFilterArray), 
				$("input[type='radio'][name='user-list-sort']:checked").val());
	});
	$("#publisher-list>.page-navi>.counter").click(function() {														//........query by page........
		getPage("publisher", 
				$("#p-page-size").children("option:selected").val(), 
				parseInt($("#publisher-list>.page-navi>.counter-base").attr("which-page"))+parseInt($(this).attr("which-page")), 
				parseIntArray2String(publisherFilterArray), 
				$("input[type='radio'][name='publisher-list-sort']:checked").val());
	});
	$("#publisher-list>.page-navi>.first,#publisher-list>.page-navi>.last,#publisher-list>.page-navi>.counter-base,#publisher-list>.page-navi>.counter-next").click(function() {
		getPage("publisher", 
				$("#p-page-size").children("option:selected").val(), 
				parseInt($(this).attr("which-page")), 
				parseIntArray2String(publisherFilterArray), 
				$("input[type='radio'][name='publisher-list-sort']:checked").val());
	});
	
	// status-span click action
	$(".f").click(function() {
		toggleStatus($(this).data("coor") + ",1");
		$(this).toggleClass("f-h", false).toggleClass("f", false).toggleClass("t", true).css("cursor", "default");
	});
	$(".t").click(function() {
		toggleStatus($(this).data("coor") + ",0");
		$(this).toggleClass("t-h", false).toggleClass("t", false).toggleClass("f", true).css("cursor", "default");
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
	<span class="warning">请别点击小方块频率过快或过多，容易造成数据库没响应导致服务器没响应，因为后台需要大量的权限验证。加上js性能原因（也许），有时会出现显示出错</span>
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
	<tr class="data-row">
		<td>${user.id}</td>
		<td><img src="<%=basepath%>/${user.portrait}" title="${user.name}'s portrait" /></td>
		<td><a href="<%=basepath%>/u/${user.account}" target="_blank">${user.name}</a></td>
		<td><span class="<s:if test="#user.isSuspend">t</s:if><s:else>f</s:else>" data-coor="user,${user.id},0">01</span></td>
		<td><span class="<s:if test="#user.isDelete">t</s:if><s:else>f</s:else>"  data-coor="user,${user.id},1">01</span></td>
		<td><span class="<s:if test="#user.isRestricted">t</s:if><s:else>f</s:else>" data-coor="user,${user.id},2">01</span></td>
		<td><span class="<s:if test="#user.isPublisher">st</s:if><s:else>sf</s:else>">01</span></td>
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
	<span class="warning">当出现操作无响应时（就是结果未变），请重启服务器吧</span>
</h4>
<input type="hidden" value="${publisherList.currentPage}" />
<table border="1" width="40%" align="center">
	<tr>
		<td><input type="radio" name="publisher-list-sort" checked="checked" value="id,0" />↑ | ↓<input type="radio" name="publisher-list-sort" value="id,1" /></td>
		<td><input type="radio" name="publisher-list-sort" value="uid,0" />↑ | ↓<input type="radio" name="publisher-list-sort" value="uid,1" /></td>
		<td><input type="radio" name="publisher-list-sort" value="name,0" />↑ | ↓<input type="radio" name="publisher-list-sort" value="name,1" /></td>
		<td></td>
		<td><input type="radio" name="publisher-list-sort" value="joinDate,0" />↑ | ↓<input type="radio" name="publisher-list-sort" value="joinDate,1" /></td>
		<td><input type="radio" name="publisher-list-sort" value="goodCount,0" />↑ | ↓<input type="radio" name="publisher-list-sort" value="goodCount,1" /></td>
		<td><input type="checkbox" name="publisher-list-filter" pos=0 value="1" />1 | 0<input type="checkbox" name="publisher-list-filter" pos=0 value="0" /></td>
		<td><input type="checkbox" name="publisher-list-filter" pos=1 value="1" />1 | 0<input type="checkbox" name="publisher-list-filter" pos=1 value="0" /></td>
	</tr>
	<tr>
		<td>pid</td>
		<td>uid</td>
		<td>p_name</td>
		<td>p_contact</td>
		<td>p_joinDate</td>
		<td>p_goodCount</td>
		<td class="status">activate</td>
		<td class="status">restrict</td>
	</tr>
<s:iterator value="#request.publisherList.result" id="publisher">
	<tr class="data-row">
		<td>${publisher.id}</td>
		<td>${publisher.uid}</td>
		<td><a href="<%=basepath%>/p/${publisher.id}" target="_blank">${publisher.name}</a></td>
		<td>${publisher.contact}</td>
		<td>${publisher.joinDate}</td>
		<td>${publisher.goodCount}</td>
		<td><span class="<s:if test="#publisher.isActivate">t</s:if><s:else>f</s:else>" data-coor="publisher,${publisher.id},0">01</span></td>
		<td><span class="<s:if test="#publisher.isRestricted">t</s:if><s:else>f</s:else>" data-coor="publisher,${publisher.id},1">01</span></td>
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
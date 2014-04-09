<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
#my-order {
	position: fixed;
	left: 10px;
	top: 100px;
	width: 15%;
	background-color: rgb(252, 255, 216);
	padding: 5px;
}
#my-order div {
	margin: 5px;
	padding: 3px;
	cursor: pointer;
	color: #000;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	$("#my-order>div").hover(function() {
		$(this).css("background", "rgb(252, 255, 182)");
	}, function() {
		$(this).css("background", "rgb(252, 255, 216)");
	});
});
</script>
<div id="my-order">
	<div class="complete-order">已完成的订单</div>
	<div class="incomplete-order">未完成的订单</div>
</div>
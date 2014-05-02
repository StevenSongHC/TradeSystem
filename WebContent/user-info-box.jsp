<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<style type="text/css">
#user {
	position: absolute;
	top: 10px;
	right: 10px;
	background-color: #DFD;
	padding-left: 6.5px;
}
#user a,#user span {
	text-decoration: none;
	color: gray;
}
#user .login {
	font-size: 20px;
}
#user .join {
	font-size: 17px;
}
#user .admin {
	color: orange;
}
#user .publisher {
	color: blue;
}
#user .notification-amount {
	background-color: rgb(182, 182, 182);
	font-size: 14px;
	margin-left: -2px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		var isLoad = false;
		var isTry = false;
		$(".login").click(function() {
			if (!isLoad) {
				var login_box = "<div id=\"login-box\" style=\"width:270px; height:100px; position:fixed; left:50%; top:50%; margin:-150px 0 0 -130px; background-color:gray; filter:alpha(opacity=80); opacity:0.8;\"><table id=\"login-box\" style=\"margin-top:10px; border: 1px solid #000; padding: 7px 0 -10px 0;\"<tbody><tr><td><b>Account</b></td><td><input type=\"text\" id=\"account\"></td></tr><tr><td><b>Password</b></td><td><input type=\"password\" id=\"password\"></td></tr></tbody></table><div class=\"function_bar\" style=\"text-align:center;\"><button id=\"confirm_login\">login</button><button id=\"cancel\">cancel</button></div>";
				$("#user").append(login_box);
				$("#account").focus();
				
				$("#login-box").keypress(function(event) {
					if (event.which == 13)
						$("#confirm_login").click();
				});

				$("#cancel").click(function() {
					$("#login-box").remove();
					isLoad = false;
					isTry = false;
				});
				$("#confirm_login").click(function() {
					if ($("#account").val() !== "" && $("#password").val() !== "") {
						if (!isTry) {
							login();
							isTry = true;
						}
					}
					else
						alert("请填写完整!");
				});
				isLoad = true;
			}
		}).css("cursor", "pointer");
		
		$(".logout").click(function() {
			logout();
		}).css("cursor", "pointer");
		
		
		function login() {
			$("#confirm_login").attr("disabled", "true");
			$.ajax( {
				type: "POST",
				url: "login",
				data: { account: $("#account").val(), password: $("#password").val() },
				dataType: "json"
			}).done(function( json ) {
				isTry = false;
				var data = eval("("+json+")");
				switch ( data.status ) {
					case 1 :
						location.reload();
						break;
					case -2 :var flag = true;
						$("#account").val(data.msg);
						$("#password").val("");
						$("#account").focus(function() {
							if (flag) {
								$("#account").val("");
							}
							flag = false;
						});
						break;
					case -1 :
						alert("Incorrect Password");
						$("#account").val(data.msg);
						$("#password").val("");
						break;
					case 0 :
						alert(data.msg);
						break;
				}
			}).fail(function() {
				alert("FAIL");
			}).error(function (XMLHttpRequest, textStatus, errorThrown) {
				$("#ajax").html(XMLHttpRequest.responseText);
			});
		}
		
		function logout() {
			$.ajax( {
				type: "POST",
				url: "logout",
				data: { redirectUrl: location.href },
				dataType: "json"
			}).done(function( json ) {
				var data = eval("("+json+")");
				self.location.href=data.redirectUrl;
			}).fail(function() {
				alert("FAIL");
			}).error(function (XMLHttpRequest, textStatus, errorThrown) {
				$("#ajax").html(XMLHttpRequest.responseText);
			});
		}
		
		// if login
		// fetch notification
		var cookies = document.cookie.split("; ");
		for (var i =0; i < cookies.length; i++) {
			if (cookies[i].split("=")[0] !== "") {
				setInterval(function() {
					$.ajax( {
						type: "POST",
						url: "checkNotification",
						dataType: "json"
					}).done(function( json ) {
						var data = eval("("+json+")");
						if (data.amount !== 0) {
							$(".notification-amount").html("<a href=\"message_list\">" + data.amount + "</a>");
						}
					}).fail(function() {
						alert("FAIL");
					}).error(function (XMLHttpRequest, textStatus, errorThrown) {
						$("#notification").html(XMLHttpRequest.responseText);
					});
				},10000);
				break;
			}
		}
		
	});
</script>
<div id="user">
    	<s:if test="#session.USER_SESSION!=null">
    		<span class="notification-amount"></span>
    		<s:a href="/TradeSystem/u/%{#session.USER_SESSION.account}">${USER_SESSION.name}</s:a><s:if test="#session.PUBLISHER_SESSION!=null"><s:if test="#session.PUBLISHER_SESSION.isAdmin==2779"><span class="admin"> (<s:a href="asd">A</s:a>)</span></s:if><s:else><span class="publisher"> (<s:a href="/TradeSystem/p/%{#session.PUBLISHER_SESSION.id}">P</s:a>)</span></s:else></s:if> | <span class="logout">log out</span>
    	</s:if>
    	<s:else>
    		<span class="login">log in</span> | <span class="join"><s:a href="/TradeSystem/join">join</s:a></span>
    	</s:else>
</div>
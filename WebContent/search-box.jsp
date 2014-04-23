<style type="text/css">
.search_box {
	width: 30%;
	margin: auto;
	padding: 15px 0 30px 0;
	position: relative;
}
.search_box .search_input {
	width: 75%;
	position: absolute;
	top: 0.6em;
	left: 4%;
}
.search_box input {
	width: 100%;
	padding: 0.1em;
	background-color: white;
	font-size: 18px;
	border: 1px solid #000;
}
.search_box .suggestion {
	width: 100%;
	overflow: hidden;
}
.search_box .suggestion a {
	display: block;
	text-decoration: none;
	text-align: left;
	color: #000;
	font-size: 18px;
}
.search_box .suggestion a:hover {
	background-color: #D1D1D1;
}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		$("#search").keyup(function(event) {
			if ($("#search").val().trim() != "") {
				// press ENTER, open a new window
				if (event.which == 13)
					window.open("search?keyword="+$("#search").val().trim());
				$("#search_suggestion").html("");
				delay(function() {
					$.ajax( {
					type: "POST",
					url: "searchSuggestion",
					data: { keyword: $("#search").val().trim() },
					dataType: "JSON"
					}).done(function(json) {
						$("#search_suggestion").css("background-color", "#FFF");
						var data = eval("("+json+")");
						$.each(data, function(index, suggestion) {
							$("#search_suggestion").append("<a href=\"search?keyword=" + suggestion + "\" target=\"_blank\">" + suggestion + "</a>");
						});
					});
				}, 350);
			}
		});
		/* $("#search").focusout(function() {
			$("#search_suggestion").css("display", "none");
		});
		$("#search").focusin(function() {
			$("#search_suggestion").css("display", "block");
		}); */
	});
	
	/* duplicate from stackoverflow: http://stackoverflow.com/questions/1909441/jquery-keyup-delay */
	var delay = (function(){
  		var timer = 0;
  		return function(callback, ms){
    		clearTimeout (timer);
    		timer = setTimeout(callback, ms);
  		};
	})();
	
</script>

<div class="search_box">
	<div class="search_input">
		<input type="text" id="search" spellcheck="false" autocomplete="off">
		<div class="suggestion" id="search_suggestion"></div>
	</div>
</div>
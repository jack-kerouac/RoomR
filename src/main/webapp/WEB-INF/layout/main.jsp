<html>
	<head>
		<title>Munich Flatshares - <sitemesh:write property="title" /></title>
		<style type="text/css">
		body {
			font-family: arial, sans-serif;
			background-color: #ccccff;
		}
		
		h1,h2,h3,h4 {
			text-align: center;
			background-color: #ccccee;
			border-top: 1px solid #cccccc;
		}
		
		.mainBody {
			padding: 10px;
			border: 1px solid #555555;
		}
		
		.disclaimer {
			text-align: center;
			border-top: 1px solid #cccccc;
			margin-top: 40px;
			color: #666666;
			font-size: smaller;
		}
		</style>
		<script src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
		<script type="text/javascript">
		 $(document).ready(function(){
		
		 	$("#offerIdField").keyup(function(event) {
		 		if(event.keyCode==13) {
		 			window.location = "/market/offers/" + $("#offerIdField").val();
		 		}
		 	});
		 	
		 });
		</script>
		
		<sitemesh:write property="head" />
	</head>
	<body>
	
		<h1 class="title">
			Munich Flatshares - <sitemesh:write property="title" />
		</h1>
		
		<div class="menu">
			<ul>
				<li>
					<a href="/"/>Home</a>
				</li>
				<li>
					<a href="/market/offers"/>All offers</a>
				</li>
				<li>
					<a href="/market/filter"/>Filter offers</a>
				</li>
				<li>
					jump to offer <input type="text" id="offerIdField" />
				</li>
			</ul>
		</div>
	
		<div class="mainBody">
			<sitemesh:write property="body" />
		</div>
	
		<div class="disclaimer">Site disclaimer. This is an example.</div>
	</body>
</html>
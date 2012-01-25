<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Offers</h1>

	<form action="/market/offers" method="get">
		<select name="seekerId">
			<option value="">Unspecified</option>
			<option value="alice">Alice</option>
			<option value="bob">Bob</option>
		</select>
		max price: <input type="text" name="maxPrice" value="" /><br> min floor
		space: <input type="text" name="minFloorSpace" value=""/><br> quarters:
		<input type="text" name="quarters" /><br> <input type="submit" />
	</form>




</body>
</html>

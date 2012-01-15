<%@ page pageEncoding="utf-8" %>

<!doctype html>
<!-- paulirish.com/2008/conditional-stylesheets-vs-css-hacks-answer-neither/ -->
<!--[if lt IE 7]> <html class="no-js ie6 oldie" lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://ogp.me/ns/fb#"> <![endif]-->
<!--[if IE 7]>    <html class="no-js ie7 oldie" lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://ogp.me/ns/fb#"> <![endif]-->
<!--[if IE 8]>    <html class="no-js ie8 oldie" lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://ogp.me/ns/fb#"> <![endif]-->
<!-- Consider adding an manifest.appcache: h5bp.com/d/Offline -->
<!--[if gt IE 8]><!--> <html class="no-js" lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://ogp.me/ns/fb#"> <!--<![endif]-->
<head>
	<sitemesh:write property="head" />
	
<!-- 	<base href="http://localhost:8080/" /> -->
	
	<meta charset="utf-8">

	<!-- Use the .htaccess and remove these lines to avoid edge case issues.
	More info: h5bp.com/b/378 -->
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	
	<title>Munich Livingroom - <sitemesh:write property="title" /></title>
	<meta name="description" content="BLABLA">
	<meta name="author" content="Florian Rampp">
	
	<!-- Mobile viewport optimized: j.mp/bplateviewport -->
	<meta name="viewport" content="width=device-width,initial-scale=1">
	
	<!-- Place favicon.ico and apple-touch-icon.png in the root directory: mathiasbynens.be/notes/touch-icons -->
	
	<!-- CSS: implied media=all -->
	<!-- CSS concatenated and minified via ant build script-->
	<link rel="stylesheet" href="css/style.css" type="text/css">
	<link rel="stylesheet" href="css/custom-theme/jquery-ui-1.8.16.custom.css" type="text/css" />
	<!-- end CSS-->
	
	<!-- More ideas for your <head> here: h5bp.com/d/head-Tips -->
	
	<!-- All JavaScript at the bottom, except for Modernizr / Respond.
	Modernizr enables HTML5 elements & feature detects; Respond is a polyfill for min/max-width CSS3 Media Queries
	For optimal performance, use a custom Modernizr build: www.modernizr.com/download/ -->
	<script src="js/libs/modernizr-2.0.6.min.js"></script>
</head>

<body>
	<header>Munich Living <sitemesh:write property="title" /></header>
	<div id="main" role="main">
		<sitemesh:write property="body" />
	</div>
	<footer>A custom footer, ... copyright, blabla</footer>
	
	<!--! end of #container -->

	<!-- required for loading Facebook JS-SDK -->	
	<div id="fb-root"></div>
	
	<!-- JavaScript at the bottom for fast page loading -->
	
	<!-- Change UA-XXXXX-X to be your site's ID -->
	<script>
		window._gaq = [['_setAccount', 'UAXXXXXXXX1'], ['_trackPageview'], ['_trackPageLoadTime']];
		
		Modernizr.load([
			// Grab Google CDN's jQuery, with a protocol relative URL; fall back to local if offline
			{
				load: '//ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js',
				complete: function() {
					if (!window.jQuery) {
						Modernizr.load('js/libs/jquery-1.6.2.min.js');
					}
				}
			},
			{
				load: '//connect.facebook.net/en_US/all.js',
				complete: function() {
					FB.init({
						appId : '312919605386817', // App ID
						//channelUrl : '//WWW.YOUR_DOMAIN.COM/channel.html', // Channel File
						status : true, // check login status
						cookie : true, // enable cookies to allow the server to access the session
						logging : true, // enable logging
						oauth : true, // enable OAuth 2.0
						xfbml : true  // parse XFBML
					});
		
					// Additional initialization code here
		
					update = function(response) {
						if(response.authResponse) {
							// logged in and connected user, someone you know
							console.log("user logged into FB: " + response);
						} else {
							// no user session available, someone you dont know
							console.log("user NOT logged into FB: " + response);
						}
					};
		
					FB.getLoginStatus(update, true);
					
					FB.Event.subscribe('auth.login', update);
					FB.Event.subscribe('auth.logout', update);
				}
			},
			{
				load: 'js/libs/jquery-ui-1.8.16.custom.min.js'
			},
			{
				load: ['js/plugins.js','js/script.js']
			},
			// always load analytics last.
			{
				load: ('https:' == location.protocol ? '//ssl' : '//www') + '.google-analytics.com/ga.js'
			}
		]);
	</script>
	
	<!-- Prompt IE 6 users to install Chrome Frame. Remove this if you want to support IE 6.
	chromium.org/developers/how-tos/chrome-frame-getting-started -->
	<!--[if lt IE 7 ]>
	<script src="//ajax.googleapis.com/ajax/libs/chrome-frame/1.0.3/CFInstall.min.js"></script>
	<script>window.attachEvent('onload',function(){CFInstall.check({mode:'overlay'})})</script>
	<![endif]-->
</body>
</html>

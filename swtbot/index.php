<?php  																														
require_once($_SERVER['DOCUMENT_ROOT'] . "/eclipse.org-common/system/app.class.php");	
require_once($_SERVER['DOCUMENT_ROOT'] . "/eclipse.org-common/system/nav.class.php"); 	
require_once($_SERVER['DOCUMENT_ROOT'] . "/eclipse.org-common/system/menu.class.php"); 	
$App 	= new App();	
$Nav	= new Nav();	
$Menu 	= new Menu();		
include($App->getProjectCommon());

	$pageTitle 		= "SWTBot &ndash; UI Testing for SWT and Eclipse";
	
	# Add page-specific Nav bars here
	# Format is Link text, link URL (can be http://www.someothersite.com/), target (_self, _blank), level (1, 2 or 3)
	# $Nav->addNavSeparator("My Page Links", 	"downloads.php");
	# $Nav->addCustomNav("My Link", "mypage.php", "_self", 3);
	# $Nav->addCustomNav("Google", "http://www.google.com/", "_blank", 3);
		
	# Paste your HTML content between the ob_start/ob_end_clean markers!	
	ob_start();
?>

	<div id="midcolumn">
		<h1>What is SWTBot?</h1>
		<p>SWTBot is an open-source Java based UI/functional testing tool for testing <a href="http://eclipse.org/swt">SWT</a> and <a href="http://eclipse.org">Eclipse</a> based applications.</p>

		<p>SWTBot provides APIs that are simple to read and write. The APIs also hide the complexities involved with SWT and Eclipse. This makes it suitable for UI/functional testing by everyone, not just developers. SWTBot also provides its own set of assertions that are useful for SWT. You can also use your own assertion framework with SWTBot.</p>

		<p>SWTBot can record and playback tests and integrates with Eclipse, and also provides for <a href="advanced/ant-integration.html">ant tasks</a> so that you can run your builds from within CruiseControl or any other CI tool that you use.</p>

		<p>SWTBot can run on all platforms that SWT runs on. Very few other testing tools provide such a wide variety of platforms.</p>
		
		<h1>Resources</h1>
		 <ul>
			<li><a href="http://eclipse.org">Eclipse Home Page</a></li>
		  	<li><a href="http://eclipse.org/swt">SWT Home Page</a></li>
		 	<li><a href="http://eclipse.org/swt/snippets">Some SWT Snippets</a></li>
		</ul>
	</div>
	
	<div id="rightcolumn">
	<? include("website/parts/incubation.php"); ?>
	</div>

<?php
$html = $GOOGLE_ANALYTICS.ob_get_contents();
ob_end_clean();

	# Generate the web page
	$App->generatePage($theme, $Menu, $Nav, $pageAuthor, $pageKeywords, $pageTitle, $html);
?>

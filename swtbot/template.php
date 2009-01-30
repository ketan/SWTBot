<?php  																														
require_once($_SERVER['DOCUMENT_ROOT'] . "/eclipse.org-common/system/app.class.php");	
require_once($_SERVER['DOCUMENT_ROOT'] . "/eclipse.org-common/system/nav.class.php"); 	
require_once($_SERVER['DOCUMENT_ROOT'] . "/eclipse.org-common/system/menu.class.php"); 	
$App 	= new App();	
$Nav	= new Nav();	
$Menu 	= new Menu();		
include($App->getProjectCommon());    
# All on the same line to unclutter the user's desktop'

	#
	# Begin: page-specific settings.  Change these. 
	$pageTitle 		= "SWTBot -- UI Testing for SWT and Eclipse";
	$pageKeywords	= "SWTBot, UI Testing for SWT, UI Testing for Eclipse, SWT testing, Eclipse testing";
	$pageAuthor		= "Ketan Padegaonkar";
	
	$GOOGLE_ANALYTICS = <<<GA

		<!-- begin google analytics -->
		<script type="text/javascript">
		var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
		document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
		</script>
		<script type="text/javascript">
		try {
		var pageTracker = _gat._getTracker("UA-414875-5");
		pageTracker._trackPageview();
		} catch(err) {}</script>
		<!-- end google analytics -->
GA;
	# Add page-specific Nav bars here
	# Format is Link text, link URL (can be http://www.someothersite.com/), target (_self, _blank), level (1, 2 or 3)
	# $Nav->addNavSeparator("My Page Links", 	"downloads.php");
	# $Nav->addCustomNav("My Link", "mypage.php", "_self", 3);
	# $Nav->addCustomNav("Google", "http://www.google.com/", "_blank", 3);

	# End: page-specific settings
	#
		
	# Paste your HTML content between the EOHTML markers!	
	ob_start();
	?>

<div id="maincontent">
	<div id="midcolumn">
		<h1>$pageTitle</h1>
		<h2>Section title</h2>
		<p>Intro text.<br /> <a href="#">more about something &raquo;</a> </p>
		<div class="homeitem">
			<h3>Narrow column</h3>
			<ul>
				<li><a href="#">Link</a>. Teaser text <a href="#">'Reference'</a><span class="dates">02/05/05</span></li>
				<li><a href="#">Link</a>. Teaser text <a href="#">'Reference'</a><span class="dates">02/05/05</span></li>
				<li><a href="#">Link</a>. Teaser text <a href="#">'Reference'</a><span class="dates">02/05/05</span></li>
			</ul>
		</div>
		<div class="homeitem">
			<h3>Narrow column</h3>
			<ul>
				<li><a href="#">Link</a>. Teaser text <a href="#">'Reference'</a><span class="dates">02/05/05</span></li>
				<li><a href="#">Link</a>. Teaser text <a href="#">'Reference'</a><span class="dates">02/05/05</span></li>
				<li><a href="#">Link</a>. Teaser text <a href="#">'Reference'</a><span class="dates">02/05/05</span></li>
			</ul>
		</div>
		<div class="homeitem3col">
			<h3>This is a wide column</h3>
			<ul>
				<li><a href="#">Link</a>. Teaser text <a href="#">'Reference'</a><span class="dates">02/05/05</span></li>
				<li><a href="#">Link</a>. Teaser text <a href="#">'Reference'</a><span class="dates">02/05/05</span></li>
				<li><a href="#">Link</a>. Teaser text <a href="#">'Reference'</a><span class="dates">02/05/05</span></li>
			</ul>
		</div>
		<hr class="clearer" />
		<p>Some free text</p>
		<ul class="midlist">
			<li>list of items in free text</li>
			<li>list of items in free text</li>
			<li>list of items in free text</li>
		</ul>
		<ol>
			<li>Ordered list</li>
			<li>Ordered list</li>
			<li>Ordered list</li>
		</ol>
	</div>
	<div id="rightcolumn">
		<div class="sideitem">
			<h6>Related links</h6>
			<ul>
				<li><a href="#">Link</a> - descriptive text</li>
				<li><a href="#">Link</a> - descriptive text</li>
				<li><a href="#">Link</a> - descriptive text</li>
				<li><a href="#">Link</a> - descriptive text</li>
				<li><a href="#">Link</a> - descriptive text</li>
			</ul>
		</div>
		<div class="sideitem">
			<h6>Related links</h6>
			<ul>
				<li><a href="#">Link</a> - descriptive text</li>
				<li><a href="#">Link</a> - descriptive text</li>
				<li><a href="#">Link</a> - descriptive text</li>
				<li><a href="#">Link</a> - descriptive text</li>
				<li><a href="#">Link</a> - descriptive text</li>
			</ul>
		</div>
	</div>
</div>


<?php
$html = $GOOGLE_ANALYTICS.ob_get_contents();
ob_end_clean();



	# Generate the web page
	$App->generatePage($theme, $Menu, $Nav, $pageAuthor, $pageKeywords, $pageTitle, $html);
?>

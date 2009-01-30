<?php  																														
require_once($_SERVER['DOCUMENT_ROOT'] . "/eclipse.org-common/system/app.class.php");	
require_once($_SERVER['DOCUMENT_ROOT'] . "/eclipse.org-common/system/nav.class.php"); 	
require_once($_SERVER['DOCUMENT_ROOT'] . "/eclipse.org-common/system/menu.class.php"); 	
$App 	= new App();	
$Nav	= new Nav();	
$Menu 	= new Menu();		
include($App->getProjectCommon());
	
	$pageTitle 		= "SWTBot &ndash; Downloads";
	
	# Add page-specific Nav bars here
	# Format is Link text, link URL (can be http://www.someothersite.com/), target (_self, _blank), level (1, 2 or 3)
	# $Nav->addNavSeparator("My Page Links", 	"downloads.php");
	# $Nav->addCustomNav("My Link", "mypage.php", "_self", 3);
	# $Nav->addCustomNav("Google", "http://www.google.com/", "_blank", 3);
		
	# Paste your HTML content between the ob_start/ob_end_clean markers!	
	ob_start();
?>
	<div id="midcolumn">
		<p>We recommend that you use the update site for your downloads. It's easier to install and allows you to stay up to date with the latest versions of SWTBot using the eclipse update manager.</p>
		
		<p>SWTBot comes in two flavors, one that works with SWT and one that builds on top of SWT and works with Eclipse.</p>
			
		<p>If you are a plugin developer you should be downloading 'SWTBot for Eclipse Testing'. If you don't know what that means, you should be downloading 'SWTBot for SWT Testing'</p>

	<!-- downloads -->
		<div class="homeitem3col">
			<h3>Nightly build</h3>
			
			<p>Nightly builds of SWTBot are extremely stable, since SWTBot has a large number of tests, providing very good test coverage.</p>

			<p>Supports Eclipse 3.3 and above</p>				

			<ul>			
				<li>
					<a title="Update Site Zip" href="http://www.eclipse.org/downloads/download.php?file=/technology/swtbot/dev-build/swtbot-update-site-2.0.0.133-dev.zip"><img border=0 src="/swtbot/images/icon-save.gif"></a>
					<a href="http://download.eclipse.org/technology/swtbot/dev-build/update-site">http://download.eclipse.org/technology/swtbot/dev-build/update-site</a> (Eclipse 3.3 and above)
				</li>
				
				<li>
					<a title="SWTBot for SWT Testing" href="http://www.eclipse.org/downloads/download.php?file=/technology/swtbot/dev-build/org.eclipse.swtbot-2.0.0.133-dev.zip"><img border=0 src="/swtbot/images/icon-save.gif"></a> <b>SWTBot for SWT Testing</b>
				</li>
				
				
				<li>
					<a title="SWTBot for SWT Testing" href="http://www.eclipse.org/downloads/download.php?file=/technology/swtbot/dev-build/org.eclipse.swtbot.eclipse-2.0.0.133-dev.zip"><img border=0 src="/swtbot/images/icon-save.gif"></a> <b>SWTBot for Eclipse Testing</b>
				</li>
				
				<li>
					<a title="SWTBot for SWT Testing" href="http://www.eclipse.org/downloads/download.php?file=/technology/swtbot/dev-build/org.eclipse.swtbot.eclipse.test-2.0.0.133-dev.zip"><img border=0 src="/swtbot/images/icon-save.gif"></a> <b>Headless Testing Framework (for running tests from within ant)</b>
				</li>
			</ul>
		</div>
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
<?php

	$pageAuthor		= "Ketan Padegaonkar";
	$pageKeywords	= "SWTBot, UI Testing for SWT, UI Testing for Eclipse, SWT testing, Eclipse testing";
	$pageTitle 		= "SWTBot &ndash; UI Testing for SWT and Eclipse";

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

	# Set the theme for your project's web pages.
	# See the Committer Tools "How Do I" for list of themes
	# https://dev.eclipse.org/committers/
	# Optional: defaults to system theme 
	$theme = "";

	# Define your project-wide Nav bars here.
	# Format is Link text, link URL (can be http://www.someothersite.com/), target (_self, _blank), level (1, 2 or 3)
	# these are optional
	$Nav->addNavSeparator("Project Home", 	"index.php");
	$Nav->addCustomNav( "About This Project", "/projects/project_summary.php?projectid=technology.swtbot", "", 2);
	$Nav->addCustomNav("Downloads", 		"downloads.php", 			"_self", 2);
	$Nav->addCustomNav("Users Guide", 		"http://wiki.eclipse.org/SWTBot/UsersGuide", 			"_self", 2);
	$Nav->addCustomNav("FAQ", 				"http://wiki.eclipse.org/SWTBot/FAQ", 			"_self", 2);

	$Nav->addCustomNav("Who's Using It?", 				"http://wiki.eclipse.org/SWTBot/Who_Is_Using_SWTBot", 			"_self", 2);
	$Nav->addCustomNav("Contributing", 				"http://wiki.eclipse.org/SWTBot/Contributing", 			"_self", 2);
	$Nav->addCustomNav("All Pages", 				"http://wiki.eclipse.org/Category:SWTBot", 			"_self", 2);
	
	
?>
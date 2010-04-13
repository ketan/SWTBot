package org.eclipse.swtbot.eclipse.ui.project;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TemplatizerTest {

	@Test
	public void templatizesAllParams() throws Exception {
		Templatizer templatizer = new Templatizer("org.eclipse.swtbot.example", "foo plugin", "1.2.3.4", "Eclipse.org");
		String templatize = templatizer.templatize("" +
				"Manifest-Version: 1.0\n" + 
				"Bundle-ManifestVersion: 2\n" + 
				"Bundle-Name: @PLUGIN_NAME@\n" + 
				"Bundle-SymbolicName: @PLUGIN_ID@;singleton:=true\n" + 
				"Bundle-Version: @PLUGIN_VERSION@\n" + 
				"Bundle-ActivationPolicy: lazy\n" + 
				"Bundle-Vendor: @PLUGIN_PROVIDER@\n" + 
				"Bundle-RequiredExecutionEnvironment: J2SE-1.5\n" + 
				"Require-Bundle: org.eclipse.swtbot.go\n" + 
				"");
		assertEquals("" +
				"Manifest-Version: 1.0\n" + 
				"Bundle-ManifestVersion: 2\n" + 
				"Bundle-Name: foo plugin\n" + 
				"Bundle-SymbolicName: org.eclipse.swtbot.example;singleton:=true\n" + 
				"Bundle-Version: 1.2.3.4\n" + 
				"Bundle-ActivationPolicy: lazy\n" + 
				"Bundle-Vendor: Eclipse.org\n" + 
				"Bundle-RequiredExecutionEnvironment: J2SE-1.5\n" + 
				"Require-Bundle: org.eclipse.swtbot.go\n" + 
				"", templatize);
	}

}

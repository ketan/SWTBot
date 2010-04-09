package org.eclipse.swtbot.eclipse.ui.launcher;

import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jdt.junit.launcher.JUnitLaunchConfigurationTab;
import org.eclipse.pde.ui.launcher.JUnitTabGroup;

public class SWTBotJUnitTabGroup extends JUnitTabGroup {

	// use the pde defaults, just change the first tab to use the junit
	// config instead of the pde one.
	// SWTBot uses its own IApplication, which takes care of threading.
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		super.createTabs(dialog, mode);
		ILaunchConfigurationTab[] tabs = getTabs();
		tabs[0] = new JUnitLaunchConfigurationTab();
	}

}

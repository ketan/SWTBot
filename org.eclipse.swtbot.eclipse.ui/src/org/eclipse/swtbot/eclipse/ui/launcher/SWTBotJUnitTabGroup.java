/*******************************************************************************
 * Copyright (c) 2010 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
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

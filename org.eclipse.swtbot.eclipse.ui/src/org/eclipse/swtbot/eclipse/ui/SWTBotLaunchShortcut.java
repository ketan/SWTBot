/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.ui;

import org.eclipse.pde.ui.launcher.JUnitWorkbenchLaunchShortcut;

public class SWTBotLaunchShortcut extends JUnitWorkbenchLaunchShortcut {

	protected String getLaunchConfigurationTypeId() {
		return "org.eclipse.swtbot.eclipse.ui.launcher.JunitLaunchConfig";
	}

}

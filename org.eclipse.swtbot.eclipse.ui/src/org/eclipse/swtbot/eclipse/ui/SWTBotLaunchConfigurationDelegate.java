/*******************************************************************************
 * Copyright (c) 2008, 2009 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     EclipseSource Corporation - ongoing enhancements
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.ui;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.pde.ui.launcher.JUnitLaunchConfigurationDelegate;

/**
 * A launch delegate for launching JUnit Plug-in tests.
 *
 * @since 3.3
 */
@SuppressWarnings("all")
public class SWTBotLaunchConfigurationDelegate extends JUnitLaunchConfigurationDelegate {

	public static final String	LAUNCH_CONFIG_ID	= "org.eclipse.swtbot.eclipse.ui.launcher.JunitLaunchConfig";	//$NON-NLS-1$

	@Override
	protected String getApplication(ILaunchConfiguration configuration) {
		return Activator.APPLICATION_ID;
	}

}

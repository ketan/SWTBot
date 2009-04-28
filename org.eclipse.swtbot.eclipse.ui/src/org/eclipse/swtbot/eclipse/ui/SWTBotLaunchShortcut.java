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

/**
 * Enhances the {@link JUnitWorkbenchLaunchShortcut} to launch SWTBot's launch configuration.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotLaunchShortcut extends JUnitWorkbenchLaunchShortcut {

	protected String getLaunchConfigurationTypeId() {
		return SWTBotLaunchConfigurationDelegate.LAUNCH_CONFIG_ID;
	}

}

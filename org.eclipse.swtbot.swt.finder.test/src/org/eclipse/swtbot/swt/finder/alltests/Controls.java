/*******************************************************************************
 *  Copyright 2007 new SWTBot, http://swtbot.org/
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/

package org.eclipse.swtbot.swt.finder.alltests;

import org.eclipse.swt.examples.addressbook.AddressBook;
import org.eclipse.swt.examples.clipboard.ClipboardExample;
import org.eclipse.swt.examples.controlexample.ControlExample;
import org.eclipse.swt.examples.controlexample.CustomControlExample;
import org.eclipse.swt.examples.dnd.DNDExample;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class Controls {

	private static Controls		instance;
	public CustomControlExample	customControlExample;
	public Shell				customControlShell;
	public ControlExample		controlExample;
	public Display				display;
	public Shell				controlShell;
	public Thread				UIThread;
	public AddressBook			menuExample;
	public Shell				menuShell;
	public ClipboardExample		clipboardExample;
	public Shell				clipboardExampleShell;
	public Shell				dndShell;
	public DNDExample			dndExample;

	public static Controls getInstance() {
		if (instance == null) {
			instance = new Controls();
			try {
				new Setup().initialize(instance);
			} catch (Exception e) {
				throw new RuntimeException("Could not create example controls.");
			}
		}
		return instance;
	}
}

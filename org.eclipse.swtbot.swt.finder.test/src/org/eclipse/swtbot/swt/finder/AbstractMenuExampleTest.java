/*******************************************************************************
 * Copyright 2007 new SWTBot, http://swtbot.org/
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder;


import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.finders.MenuFinder;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: AbstractMenuExampleTest.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public abstract class AbstractMenuExampleTest extends AbstractSWTTestCase {

	protected MenuFinder	menuFinder;

	/**
	 * 
	 */
	public AbstractMenuExampleTest() {
		super();
	}

	protected void setUp() throws Exception {
		super.setUp();
		menuFinder = new MenuFinder();
	}

	protected Shell getFocusShell() {
		return menuShell;
	};
}

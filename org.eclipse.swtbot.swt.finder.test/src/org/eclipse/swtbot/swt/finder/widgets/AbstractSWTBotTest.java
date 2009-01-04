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
package org.eclipse.swtbot.swt.finder.widgets;


import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class AbstractSWTBotTest extends AbstractSWTTestCase {

	public void testThrowsExceptionOnNullObjectPassedIntoConstructor() throws Exception {
		try {
			new AbstractSWTBot(null) {
			};
			fail("Was expecting a WidgetNotFoundException");
		} catch (WidgetNotFoundException e) {
			assertEquals("The widget was null.", e.getMessage());
		}
	}

	public void testThrowsExceptionOnDisposedObjectPassedIntoConstructor() throws Exception {
		try {
			Shell shell = UIThreadRunnable.syncExec(display, new WidgetResult<Shell>() {
				public Shell run() {
					Shell shell = new Shell(display);
					shell.close();
					return shell;
				}
			});
			new AbstractSWTBot(shell) {
			};
			fail("Was expecting a WidgetNotFoundException");
		} catch (WidgetNotFoundException e) {
			assertEquals("The widget {Shell with text {}} was disposed.", e.getMessage());
		}
	}

}

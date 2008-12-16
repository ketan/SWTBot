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
package org.eclipse.swtbot.swt.finder.waits;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class ShellClosesTest extends AbstractSWTTestCase {

	private static final String	TEXT	= "this should close in a while" + ShellClosesTest.class;
	private Shell	shell;

	@SuppressWarnings("unchecked")
	public void testWaitsForShellClose() throws Exception {

		long start = System.currentTimeMillis();
		new SWTBot().waitUntil(Conditions.shellCloses(new SWTBotShell(shell)));
		long end = System.currentTimeMillis();

		int time = (int) (end - start);
		assertThat(time, allOf(lessThan(5000), greaterThan(200)));
	}


	protected void setUp() throws Exception {
		super.setUp();
		shell = createShell(TEXT);

		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(200);
				} catch (InterruptedException niceTry) {
				}
				new SWTBotShell(shell).close();
			}
		}).start();
	}
}

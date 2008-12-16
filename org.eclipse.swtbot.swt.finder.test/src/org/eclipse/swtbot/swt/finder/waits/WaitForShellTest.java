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

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.waits.Conditions;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class WaitForShellTest extends AbstractSWTTestCase {

	private static final String	TEXT	= "this should close in a while - " + WaitForShellTest.class;

	@SuppressWarnings("unchecked")
	public void testWaitsForShellToAppear() throws Exception {
		createShellAfter500milliSecs();

		destroyShellAfter800milliSecs();

		long start = System.currentTimeMillis();
		new SWTBot().waitUntil(Conditions.waitForShell(withText(TEXT)));
		long end = System.currentTimeMillis();

		int time = (int) (end - start);
		assertThat(time, allOf(lessThan(800), greaterThan(500)));
	}

	private void destroyShellAfter800milliSecs() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(800);
				} catch (InterruptedException niceTry) {
				}
				new SWTBot().shell(TEXT).close();
			}
		}).start();
	}

	private void createShellAfter500milliSecs() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException niceTry) {
				}
				createShell(TEXT);
			}
		}).start();
	}

}

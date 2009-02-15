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
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class WaitForShellTest extends AbstractSWTTestCase {

	private static final String	TEXT	= "this should close in a while - " + WaitForShellTest.class.getSimpleName();

	@SuppressWarnings("unchecked")
	@Test
	public void waitsForShellToAppear() throws Exception {
		createShellAfter(100);

		destroyShellAfter(600);

		long start = System.currentTimeMillis();
		new SWTBot().waitUntil(Conditions.waitForShell(withText(TEXT)));
		long end = System.currentTimeMillis();

		int time = (int) (end - start);
		assertThat(time, allOf(lessThan(800), greaterThanOrEqualTo(500)));
	}

	private void destroyShellAfter(int delay) {
		new Thread(new DelayedExecutionRunnable(new Runnable() {
			public void run() {
				new SWTBot().shell(TEXT).close();
			}
		}, delay)).start();
	}

	private void createShellAfter(int delay) {
		new Thread(new DelayedExecutionRunnable(new Runnable() {
			public void run() {
				createShell(TEXT);
			}
		}, delay)).start();
	}

	private class DelayedExecutionRunnable implements Runnable {

		private final Runnable	runnable;
		private final int		delayInMillis;

		public DelayedExecutionRunnable(Runnable runnable, int delayInMillis) {
			this.runnable = runnable;
			this.delayInMillis = delayInMillis;
		}

		public void run() {
			try {
				Thread.sleep(delayInMillis);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			runnable.run();

		}

	}

}

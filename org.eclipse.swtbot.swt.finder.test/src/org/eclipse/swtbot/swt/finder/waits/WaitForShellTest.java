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

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.UIThread;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.test.AbstractSWTTest;
import org.hamcrest.Matcher;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class WaitForShellTest extends AbstractSWTTest {

	private static final String	TEXT	= "Test shell";

	@UIThread
	public void runUIThread() {
		new Display();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void waitsForShellToAppear() throws Exception {
		createShellAfter(100);
		long start = System.currentTimeMillis();
		Matcher<Shell> withText = withText(TEXT);
		new SWTBot().waitUntil(Conditions.waitForShell(withText));
		long end = System.currentTimeMillis();

		int time = (int) (end - start);
		assertThat(time, allOf(lessThan(800), greaterThanOrEqualTo(450)));
	}

	private void createShellAfter(int delay) {
		new Thread(new DelayedExecutionRunnable(new Runnable() {
			public void run() {
				UIThreadRunnable.syncExec(new WidgetResult<Shell>() {
					public Shell run() {
						Shell shell = new Shell(Display.getDefault());
						shell.setText(TEXT);
						shell.setLayout(new GridLayout(1, false));
						shell.open();
						return shell;
					}
				});
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

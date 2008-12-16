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
 *     Vincent Mahe - http://swtbot.org/bugzilla/show_bug.cgi?id=123
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder;

import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @author Vincent Mahe &lt;vmahe [at] irisa [dot] fr&gt;
 * @version $Id$
 */
public class SWTBotTest extends AbstractMenuExampleTest {

	private SWTBot	bot;

	public void testWaits5SecondsAndFailsForFailingCondition() throws Exception {
		final long begin = System.currentTimeMillis();
		try {
			bot.waitUntil(new DefaultCondition() {
				public boolean test() throws Exception {
					return false;
				}

				public String getFailureMessage() {
					return "timed out";
				}
			});
			fail("Expected a timeout exception");
		} catch (TimeoutException expected) {
			final long end = System.currentTimeMillis();
			final long timeout = end - begin;
			assertTrue(timeout >= 5000);
			assertTrue(timeout <= 6000);
			assertEquals("Timeout after: 5000 ms.: timed out", expected.getMessage());
		}
	}

	public void testDoesNotWait5SecondsAndPassesForPassingCondition() throws Exception {
		final long begin = System.currentTimeMillis();
		bot.waitUntil(new DefaultCondition() {
			public boolean test() throws Exception {
				return true;
			}

			public String getFailureMessage() {
				return "timed out";
			}
		});
		final long end = System.currentTimeMillis();
		final long timeout = end - begin;
		assertTrue(timeout <= 100);

	}

	public void testThrowsExceptionOnNegativeTimeOut() throws Exception {
		try {
			bot.waitUntil(null, -10);
			fail("expecting an exception");
		} catch (Exception e) {
			assertEquals("assertion failed: timeout value is negative", e.getMessage());
		}
	}

	public void testGetsAllShells() throws Exception {
		assertEquals(7, bot.shells().length);
	}

	public void testFindsShellsById() throws Exception {
		final SWTBotShell shell = bot.shell("SWT Clipboard");
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				shell.widget.setData("foo-shell", "bar");
			}
		});

		assertSame(shell.widget, bot.shellWithId("foo-shell", "bar").widget);
	}

	public void testWaitsLessThan5sWhenConditionSwitchToFailing() {
		final long begin = System.currentTimeMillis();
		bot.waitWhile(new DefaultCondition() {

			public String getFailureMessage() {
				return "time out";
			}

			public boolean test() throws Exception {
				if (System.currentTimeMillis() < begin + 2500)
					return true;
				return false;
			}

		});
		final long end = System.currentTimeMillis();
		final long timeout = end - begin;
		assertTrue(timeout >= 2500);
		assertTrue(timeout < 5000);
	}

	public void testWaitsMoreThan5sWhenConditionDoesNotSwitchToFailing() {
		final long begin = System.currentTimeMillis();
		try {
			bot.waitWhile(new DefaultCondition() {

				public String getFailureMessage() {
					return "time out";
				}

				public boolean test() throws Exception {
					if (System.currentTimeMillis() < begin + 10000)
						return true;
					return false;
				}

			});
		} catch (TimeoutException expected) {
			final long end = System.currentTimeMillis();
			final long timeout = end - begin;
			assertTrue(timeout >= 5000);
			assertTrue(timeout <= 6000);
			assertEquals("Timeout after: 5000 ms.: time out", expected.getMessage());
		}
	}

	protected void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
	}
}

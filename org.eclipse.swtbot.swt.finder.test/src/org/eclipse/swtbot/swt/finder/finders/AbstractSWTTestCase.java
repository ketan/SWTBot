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
package org.eclipse.swtbot.swt.finder.finders;

import static org.hamcrest.Matchers.containsString;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.examples.addressbook.AddressBook;
import org.eclipse.swt.examples.browserexample.BrowserExample;
import org.eclipse.swt.examples.clipboard.ClipboardExample;
import org.eclipse.swt.examples.controlexample.ControlExample;
import org.eclipse.swt.examples.controlexample.CustomControlExample;
import org.eclipse.swt.examples.dnd.DNDExample;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.alltests.Controls;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public abstract class AbstractSWTTestCase {

	static {
		// System.setProperty(LogFactory.DIAGNOSTICS_DEST_PROPERTY, "STDOUT");
		System.setProperty("org.eclipse.swt.internal.carbon.smallFonts", "");
		System.setProperty("org.eclipse.swtbot.keyboard.interval", "80");
	}
	public static final Display					display					= Controls.getInstance().display;
	public static final Shell					controlShell			= Controls.getInstance().controlShell;
	public static final ControlExample			controlExample			= Controls.getInstance().controlExample;

	public static final Shell					menuShell				= Controls.getInstance().menuShell;
	public static final AddressBook				menuExample				= Controls.getInstance().menuExample;

	public static final Shell					customControlShell		= Controls.getInstance().customControlShell;
	public static final CustomControlExample	customControlExample	= Controls.getInstance().customControlExample;

	public static final Shell					clipboardExampleShell	= Controls.getInstance().clipboardExampleShell;
	public static final ClipboardExample		clipboardExample		= Controls.getInstance().clipboardExample;

	public static final Shell					dndShell				= Controls.getInstance().dndShell;
	public static final DNDExample				dndExample				= Controls.getInstance().dndExample;

	public static final Shell					browserShell				= Controls.getInstance().browserShell;
	public static final BrowserExample			browserExample				= Controls.getInstance().browserExample;

	public static final Thread					UIThread				= Controls.getInstance().UIThread;

	protected final Logger						log;
	protected SWTBot							bot;

	public AbstractSWTTestCase() {
		super();
		log = Logger.getLogger(getClass());
	}

	@Before
	public void setUp() throws Exception {
		bot = new SWTBot();
		// log.debug(MessageFormat.format("Executing test: {0}#{1}", getClass(), getName()));
		log.debug(MessageFormat.format("Activating shell: {0}", SWTUtils.toString(getFocusShell())));
		SWTBotShell shell = new SWTBotShell(getFocusShell());
		shell.activate();
		shell.setFocus();
		//
		// display.syncExec(new Runnable() {
		// public void run() {
		// new SWTBot
		// getFocusShell().setActive();
		// }
		// });
	}

	@After
	public void tearDown() throws Exception {
		// log.debug(MessageFormat.format("Finished executing test: {0}#{1}", getClass(), getName()));
	}

	protected Shell getFocusShell() {
		return controlShell;
	}

	protected static boolean contains(String needle, String hayStack) {
		return (hayStack.indexOf(needle) >= 0);
	}

	protected Shell createShell(final String text) {
		return UIThreadRunnable.syncExec(new WidgetResult<Shell>() {
			public Shell run() {
				Shell shell = new Shell(display);
				shell.setText(text);
				shell.setLayout(new GridLayout(1, false));
				shell.open();
				return shell;
			}
		});
	}

	protected Table createTable(final Shell shell) {
		return UIThreadRunnable.syncExec(new WidgetResult<Table>() {
			public Table run() {
				Table table = new Table(shell, SWT.SINGLE | SWT.FULL_SELECTION);
				table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				table.setLinesVisible(true);
				table.setHeaderVisible(true);

				for (int i = 0; i < table.getColumnCount(); i++) {
					table.getColumn(i).pack();
				}
				shell.layout(true);
				return table;
			}
		});
	}

	protected Tree createTree(final Shell shell) {
		return UIThreadRunnable.syncExec(new WidgetResult<Tree>() {
			public Tree run() {
				Tree tree = new Tree(shell, SWT.SINGLE);
				tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				tree.setLinesVisible(true);
				tree.setHeaderVisible(true);

				shell.layout(true);
				return tree;
			}
		});
	}

	protected void assertEventMatches(final SWTBotText listeners, String expected) {
		expected = expected.replaceAll("time=-?\\d+", "time=SOME_TIME_AGO").replaceAll("x=\\d+", "x=X_CO_ORDINATE").replaceAll("y=\\d+", "y=Y_CO_ORDINATE");
		final Matcher<String> matcher = containsString(expected);
		bot.waitUntil(new DefaultCondition() {

			private String	text;

			public boolean test() throws Exception {
				text = listeners.getText();
				// keyLocation was added in 3.6, we don't care about it for the tests
				String listenersText = text.replaceAll("time=-?\\d+", "time=SOME_TIME_AGO").replaceAll("x=\\d+", "x=X_CO_ORDINATE").replaceAll("y=\\d+", "y=Y_CO_ORDINATE").replaceAll("keyLocation=\\d+ ", "");
				return matcher.matches(listenersText);
			}

			public String getFailureMessage() {
				Description description = new StringDescription();
				description.appendText("\nExpected:\n").appendDescriptionOf(matcher).appendText("\ngot:\n").appendValue(text).appendText("\n");
				return description.toString();
			}
		});
	}

}

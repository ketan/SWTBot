/*******************************************************************************
 * Copyright (c) 2010 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.test;

import static org.hamcrest.Matchers.containsString;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public abstract class BaseSWTShellTest extends BaseSWTTest {

	protected static final String SHELL_TEXT = "Test shell";
	protected Display display;
	protected Shell shell;

	@Override
	public final void runUIThread() {
		display = Display.getDefault();

		shell = new Shell(display, SWT.SHELL_TRIM);
		shell.setLayout(new FillLayout());
		shell.setText(SHELL_TEXT);

		createUI(shell);
		shell.open();
	}

	protected abstract void createUI(Composite parent);

	//TODO: move into some Assert class
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
	
	protected boolean isCocoa() {
		return SWT.getPlatform().equals("cocoa");
	}
	
}
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
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.WaitForWidgetInParent;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class WaitForWidgetInParentTest extends AbstractSWTTestCase {


	public void testWaitsForWidgetToAppearInParent() throws Exception {
		long start = System.currentTimeMillis();

		WaitForWidgetInParent condition = Conditions.waitForWidget(new EvaluateTrueAfterAWhile(500), getFocusShell());
		new SWTBot().waitUntil(condition);
		long end = System.currentTimeMillis();

		int time = (int) (end - start);
		assertThat(time, allOf(lessThan(1000), greaterThanOrEqualTo(500)));
		assertFalse(condition.getWidgets().isEmpty());
	}

	private final class EvaluateTrueAfterAWhile extends BaseMatcher {
		private final long	start;
		private final int	i;

		private EvaluateTrueAfterAWhile(int i) {
			this.start = System.currentTimeMillis();
			this.i = i;
		}

		public boolean matches(Object item) {
			long diff = System.currentTimeMillis() - start;
			if (diff >= i)
				return true;
			return false;
		}

		public void describeTo(Description description) {

		}
	}

}

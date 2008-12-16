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

import java.util.ArrayList;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;

/**
 * Condiion that waits for a shell with the specified text to appear.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 2.0
 */
public class WaitForShell extends DefaultCondition {

	private ArrayList<Shell>	matchingShells;
	private final Matcher<?>	matcher;

	WaitForShell(Matcher<?> matcher) {
		this.matcher = matcher;
	}

	public String getFailureMessage() {
		return "Could not find shell matching: " + matcher;
	}

	public boolean test() throws Exception {
		Shell[] shells = bot.getFinder().getShells();
		this.matchingShells = new ArrayList<Shell>();
		for (Shell shell : shells) {
			if (matcher.matches(shell)) {
				matchingShells.add(shell);
			}
		}
		return !matchingShells.isEmpty();
	}

	public ArrayList<Shell> getAllShells() {
		return matchingShells;
	}

	public Shell get(int index) {
		return matchingShells.get(index);
	}

}

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

import org.eclipse.swtbot.swt.finder.SWTBot;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 1.2
 */
public interface ICondition {

	/**
	 * Tests if the condition has been met.
	 * 
	 * @return <code>true</code> if the condition is satisfied, <code>false</code> otherwise.
	 * @throws Exception if the test encounters an error while processing the check.
	 */
	boolean test() throws Exception;

	/**
	 * Initializes the condition with the given {@link SWTBot} instance. This should never be <code>null</code>.
	 * 
	 * @param bot the SWTBot instance that this instance may use to evaluate the test.
	 */
	void init(SWTBot bot);

	/**
	 * Gets the failure message when a test fails (returns <code>false</code>).
	 * 
	 * @return the failure message to show in case the test fails.
	 */
	String getFailureMessage();
}

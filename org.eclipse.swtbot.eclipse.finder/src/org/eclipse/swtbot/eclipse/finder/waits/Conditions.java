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
package org.eclipse.swtbot.eclipse.finder.waits;

import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewReference;
import org.hamcrest.Matcher;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class Conditions extends org.eclipse.swtbot.swt.finder.waits.Conditions {

	/**
	 * @param matcher a matcher
	 * @return a condition that waits until the matcher evaluates to true.
	 */
	public static WaitForView waitForView(Matcher<IViewReference> matcher) {
		return new WaitForView(matcher);
	}

	/**
	 * @param matcher a matcher
	 * @return a condition that waits until the matcher evaluates to true.
	 */
	public static WaitForEditor waitForEditor(Matcher<IEditorReference> matcher) {
		return new WaitForEditor(matcher);
	}

}

/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Ketan Padegaonkar - http://swtbot.org/bugzilla/show_bug.cgi?id=126
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder.matchers;

import org.eclipse.swtbot.swt.finder.matchers.AbstractMatcher;
import org.eclipse.ui.IWorkbenchPartReference;
import org.hamcrest.Description;
import org.hamcrest.Factory;


/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: WithPartName.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 * @since 2.0
 */
public class WithPartName<T extends IWorkbenchPartReference> extends AbstractMatcher<T> {

	private final String	viewLabel;

	/**
	 * @param partName the label of the view.
	 */
	public WithPartName(String partName) {
		this.viewLabel = partName;
	}

	public boolean doMatch(Object item) {
		if (item instanceof IWorkbenchPartReference) {
			IWorkbenchPartReference part = (IWorkbenchPartReference) item;
			return part.getPartName().equals(viewLabel);
		}
		return false;
	}

	public void describeTo(Description description) {

	}

	/**
	 * Matches a workbench part (view/editor) with the specfied name.
	 *
	 * @param text the label of the part.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static AbstractMatcher<? extends IWorkbenchPartReference> withPartName(String text) {
		return new WithPartName<IWorkbenchPartReference>(text);
	}

}

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
package org.eclipse.swtbot.eclipse.finder.widgets.utils;

import org.eclipse.swtbot.swt.finder.utils.ClassUtils;
import org.eclipse.ui.IWorkbenchPartReference;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

/**
 * Describes the IWorkbenchPartReference, by invoking {@link IWorkbenchPartReference#getPartName()} on it.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class PartLabelDescription<T extends IWorkbenchPartReference> implements SelfDescribing {

	private final T	partReference;

	public PartLabelDescription(T partReference) {
		this.partReference = partReference;
	}

	public void describeTo(Description description) {
		description.appendText(ClassUtils.simpleClassName(partReference) + " with label {" + partReference.getPartName() + "}"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public String toString() {
		return StringDescription.asString(this);
	}

}

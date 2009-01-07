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
package org.eclipse.swtbot.eclipse.finder.test.ui.data.internal;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * A simple label provider to display the name of the SWT Bot Test Data.
 * 
 * @author Stephen Paulin &lt;paulin [at] spextreme [dot] com&gt;
 * @version $Id$
 */
public class SWTBotTestLabelProvider extends LabelProvider implements ITableLabelProvider {

	/**
	 * No image provided.
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	/**
	 * Gets the text for the given element.
	 */
	public String getColumnText(Object element, int columnIndex) {
		return element.toString();
	}
}

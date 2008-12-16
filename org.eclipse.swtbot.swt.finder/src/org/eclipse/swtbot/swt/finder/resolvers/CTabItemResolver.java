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
package org.eclipse.swtbot.swt.finder.resolvers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

/**
 * Resolves {@link CTabItem}s.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: CTabItemResolver.java 1188 2008-12-02 06:55:23Z kpadegaonkar $
 */
public class CTabItemResolver implements IChildrenResolver, IParentResolver {

	public List getChildren(Widget w) {
		ArrayList children = new ArrayList();
		Control control = ((CTabItem) w).getControl();
		if (control != null)
			children.add(control);
		return children;
	}

	public boolean hasChildren(Widget w) {
		return canResolve(w) && ((CTabItem) w).getControl() != null;
	}

	public boolean canResolve(Widget w) {
		return w instanceof CTabItem;
	}

	public Class[] getResolvableClasses() {
		return new Class[] { CTabItem.class };
	}

	public Widget getParent(Widget w) {
		return canResolve(w) ? ((CTabItem) w).getParent() : null;
	}

	public boolean hasParent(Widget w) {
		return getParent(w) != null;
	}

}

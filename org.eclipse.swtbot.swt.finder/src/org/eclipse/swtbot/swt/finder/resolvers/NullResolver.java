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

import org.eclipse.swt.widgets.Widget;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: NullResolver.java 1188 2008-12-02 06:55:23Z kpadegaonkar $
 */
public class NullResolver implements IChildrenResolver, IParentResolver {

	public boolean canResolve(Widget w) {
		return true;
	}

	public List getChildren(Widget w) {
		return new ArrayList();
	}

	public Widget getParent(Widget w) {
		return null;
	}

	public Class[] getResolvableClasses() {
		return new Class[0];
	}

	public boolean hasChildren(Widget w) {
		return false;
	}

	public boolean hasParent(Widget w) {
		return false;
	}

}

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
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Widget;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: DefaultChildrenResolver.java 1188 2008-12-02 06:55:23Z kpadegaonkar $
 */
public class DefaultChildrenResolver extends Resolvable implements IChildrenResolver {

	public List getChildren(Widget w) {
		List result = new ArrayList();

		if (!hasChildren(w))
			return result;

		List resolvers = resolver.getResolvers(w.getClass());

		for (Iterator iterator = resolvers.iterator(); iterator.hasNext();) {
			IChildrenResolver resolver = (IChildrenResolver) iterator.next();
			if (resolver.canResolve(w) && resolver.hasChildren(w)) {
				List children = resolver.getChildren(w);
				if (children != null) {
					result.addAll(children);
					return result;
				}
			}
		}
		return result;
	}

	public boolean hasChildren(Widget w) {
		List resolvers = resolver.getResolvers(w.getClass());
		for (Iterator iterator = resolvers.iterator(); iterator.hasNext();) {
			IChildrenResolver resolver = (IChildrenResolver) iterator.next();
			if (resolver.canResolve(w) && resolver.hasChildren(w))
				return true;
		}

		return false;
	}

}

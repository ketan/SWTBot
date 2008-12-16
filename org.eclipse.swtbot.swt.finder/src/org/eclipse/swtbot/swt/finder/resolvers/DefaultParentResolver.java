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

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Widget;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: DefaultParentResolver.java 1188 2008-12-02 06:55:23Z kpadegaonkar $
 */
public class DefaultParentResolver extends Resolvable implements IParentResolver {

	public Widget getParent(Widget w) {
		if (!hasParent(w))
			return null;

		List resolvers = getResolver().getResolvers(w.getClass());

		for (Iterator iter = resolvers.iterator(); iter.hasNext();) {
			IParentResolver resolver = (IParentResolver) iter.next();
			if (resolver.hasParent(w))
				return resolver.getParent(w);
		}
		return null;
	}

	public boolean hasParent(Widget w) {

		List resolvers = getResolver().getResolvers(w.getClass());

		for (Iterator iter = resolvers.iterator(); iter.hasNext();) {
			IParentResolver resolver = (IParentResolver) iter.next();
			if (resolver.hasParent(w))
				return true;
		}
		return false;
	}

}

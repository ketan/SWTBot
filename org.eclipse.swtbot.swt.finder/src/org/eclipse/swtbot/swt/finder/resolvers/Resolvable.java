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

import java.util.Set;

import org.eclipse.swt.widgets.Widget;

/**
 * Finds a resolver that can resolve the parent anc children of a widget.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class Resolvable implements IResolvable {

	/** The resolver */
	protected Resolver	resolver;

	/**
	 * Create a resolvable instance, with some default resolvers.
	 */
	public Resolvable() {
		this(new Resolver());
		resolver.addResolver(new CTabFolderResolver());
		resolver.addResolver(new TabFolderResolver());
		resolver.addResolver(new CTabItemResolver());
		resolver.addResolver(new TabItemResolver());
		resolver.addResolver(new ToolbarResolver());
		resolver.addResolver(new CompositeResolver());
		resolver.addResolver(new NullResolver());
	}

	/**
	 * Creates a resolvable using the given resolvable item. It is recommended that the default construction be used.
	 * 
	 * @param resolver the resolver
	 */
	public Resolvable(Resolver resolver) {
		this.resolver = resolver;
	}

	/**
	 * Returns {@code true} if any of the resolvers can resolve the widget.
	 * 
	 * @see org.eclipse.swtbot.swt.finder.resolvers.IResolvable#canResolve(org.eclipse.swt.widgets.Widget)
	 * @param w The widget to resolve.
	 * @return <code>true</code> if any of the resolvers can resolve the widget. Otherwise <code>false</code>.
	 */
	public boolean canResolve(Widget w) {
		Class[] resolvableClasses = getResolvableClasses();
		for (Class clazz : resolvableClasses) {
			if (w.getClass().equals(clazz))
				return true;
		}

		return false;
	}

	/**
	 * Gets the complete list of widget types that this object can resolve.
	 * 
	 * @return the types that this resolver can resolve
	 */
	public Class[] getResolvableClasses() {
		Set keySet = resolver.map.keySet();
		Class[] result = new Class[keySet.size()];
		keySet.toArray(result);
		return result;
	}

	/**
	 * Gets the resolver.
	 * 
	 * @return the resolver.
	 */
	public Resolver getResolver() {
		return resolver;
	}

}

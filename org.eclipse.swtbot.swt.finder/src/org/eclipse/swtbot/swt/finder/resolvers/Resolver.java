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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


import org.apache.commons.collections.map.MultiValueMap;
import org.eclipse.swtbot.swt.finder.collections.OrderedSet;

/**
 * A resolver that maps classes to the {@link IChildrenResolver}s that resolve the classes.
 *
 * @see IChildrenResolver#getResolvableClasses()
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class Resolver {

	/** The map that maps classes to {@link IChildrenResolver}s */
	MultiValueMap	map	= MultiValueMap.decorate(new HashMap(), OrderedSet.class);

	/**
	 * Map all the classes that the resolver resolver to the resolver.
	 *
	 * @param resolver the resolver.
	 * @see IChildrenResolver#getResolvableClasses()
	 */
	public void addResolver(IResolvable resolver) {
		Class[] resolvableClasses = resolver.getResolvableClasses();
		if ((resolvableClasses != null) && (resolvableClasses.length > 0))
			addResolver(resolver, resolvableClasses);
	}

	/**
	 * Gets the resolvers that match the given class.
	 *
	 * @param clazz the class that should be resolved using the resolvers.
	 * @return the list of {@link Resolver}s that can resolve objects of type <code>clazz</code>
	 */
	public List<IResolvable> getResolvers(Class clazz) {

		OrderedSet<IResolvable> result = new OrderedSet<IResolvable>();

		Collection resolvers = map.getCollection(clazz);

		if ((resolvers != null) && !resolvers.isEmpty())
			result.addAll(resolvers);
		else if (!Object.class.equals(clazz))
			result.addAll(getResolvers(clazz.getSuperclass()));

		return new ArrayList(result);
	}

	/**
	 * Adds a new resolver to the list.
	 *
	 * @param resolver The resolver to add.
	 * @param resolvableClasses The classes supported by the resolver.
	 */
	private void addResolver(IResolvable resolver, Class[] resolvableClasses) {
		for (Class clazz : resolvableClasses) {
			map.put(clazz, resolver);
		}
	}
}

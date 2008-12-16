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
package org.eclipse.swtbot.swt.finder.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 2.0
 */
public class OrderedSet<T> extends ArrayList<T> {

	private static final long	serialVersionUID	= 8082255567260066555L;
	private HashSet<T>			set;

	public OrderedSet() {
		this.set = new HashSet<T>();
	}

	public OrderedSet(List<T> initial) {
		this();
		addAll(initial);
	}

	public boolean add(T o) {
		if (set.contains(o)) {
			return true;
		}
		// else add to both sets and return
		set.add(o);
		return super.add(o);
	}

	public boolean addAll(Collection<? extends T> c) {
		boolean result = false;
		for (T widget : c) {
			result = result | add(widget);
		}
		return result;
	}

}

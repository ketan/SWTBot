/*******************************************************************************
 * Copyright (c) 2008-2009 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Maps a key to a collection of values. The values are maintained in a set that gurantees order, adding the same value twice for the same key will have no effect.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @noextend This class is not intended to be subclassed by clients.
 */
public class MultiValueMap<K, V> {

	private HashMap<K, LinkedHashSet<V>>	map;

	public MultiValueMap() {
		map = new HashMap<K, LinkedHashSet<V>>();
	}

	public void put(K k, V v) {
		LinkedHashSet<V> collection = map.get(k);
		if (collection == null) {
			collection = new LinkedHashSet<V>();
			map.put(k, collection);
		}
		collection.add(v);
	}

	public Collection<V> getCollection(K k) {
		return map.get(k);
	}

	public Set<K> keySet() {
		return map.keySet();
	}

}

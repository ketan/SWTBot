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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * <b>Note: This is not error proof, and adding the same key twice with a different value will have uncertain
 * results.</b>
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @noextend This class is not intended to be subclassed by clients.
 */
class BidiMap<K, V> implements Iterable<Entry<K, V>> {

	private final HashMap<K, V>	forward;
	private final HashMap<V, K>	reverse;

	BidiMap() {
		forward = new HashMap<K, V>();
		reverse = new HashMap<V, K>();
	}

	public void put(K k, V v) {
		forward.put(k, v);
		reverse.put(v, k);
	}

	public K getKey(V v) {
		return reverse.get(v);
	}

	public int size() {
		return forward.size();
	}

	public Iterator<Entry<K, V>> iterator() {
		return forward.entrySet().iterator();
	}

	public V getValue(K k) {
		return forward.get(k);
	}

}

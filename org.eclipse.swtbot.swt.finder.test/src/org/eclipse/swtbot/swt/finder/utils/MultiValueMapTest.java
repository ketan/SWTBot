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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class MultiValueMapTest {

	private MultiValueMap<String, String>	map	= new MultiValueMap<String, String>();

	@Test
	public void mapsMultipleValuesToAKey() throws Exception {
		assertThat(map.getCollection("key"), nullValue());
		map.put("key", "value1");
		assertThat(map.getCollection("key"), hasItems("value1"));
		map.put("key", "value2");
		assertThat(map.getCollection("key"), hasItems("value1", "value2"));
		map.put("key", "value3");
		assertThat(map.getCollection("key"), hasItems("value1", "value2", "value3"));
	}

	@Test
	public void getsKeySetOfTheMap() throws Exception {
		assertThat(map.keySet().size(), equalTo(0));
		map.put("foo1", "bar");
		assertThat(map.keySet(), hasItems("foo1"));
		map.put("foo2", "bar");
		assertThat(map.keySet(), hasItems("foo1", "foo2"));
		map.put("foo3", "bar");
		assertThat(map.keySet(), hasItems("foo1", "foo3", "foo2"));
	}
}

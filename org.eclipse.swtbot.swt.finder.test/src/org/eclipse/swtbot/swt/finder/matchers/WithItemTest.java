/*******************************************************************************
 * Copyright (c) 2010 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.matchers;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.junit.Assert.assertEquals;

import org.eclipse.swt.widgets.Item;
import org.hamcrest.StringDescription;
import org.junit.Test;

public class WithItemTest {

	@Test
	public void testDescription() throws Exception {
		WithItem<Item> withItem = WithItem.withItem(withText("foo"));
		assertEquals("with item matching (with text 'foo')", StringDescription.toString(withItem));

	}
}

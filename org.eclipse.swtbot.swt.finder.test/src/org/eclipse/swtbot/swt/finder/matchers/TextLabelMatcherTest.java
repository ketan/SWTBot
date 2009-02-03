/*******************************************************************************
 * Copyright 2007 new SWTBot, http://swtbot.org/
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.matchers;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withLabel;
import static org.junit.Assert.assertEquals;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class TextLabelMatcherTest extends AbstractSWTTestCase {

	@Test
	public void getsToString() throws Exception {
		assertEquals("with label (with mnemonic 'SomeLabel')", withLabel("SomeLabel").toString());
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	protected Shell getFocusShell() {
		return clipboardExampleShell;
	}
}

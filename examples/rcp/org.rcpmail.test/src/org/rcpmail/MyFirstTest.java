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
package org.rcpmail;

import org.eclipse.swtbot.eclipse.finder.SWTBotEclipseTestCase;


public class MyFirstTest extends SWTBotEclipseTestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testThisPasses() throws Exception {
		pass();
	}

	public void testThisFails() throws Exception {
		fail();
	}

}

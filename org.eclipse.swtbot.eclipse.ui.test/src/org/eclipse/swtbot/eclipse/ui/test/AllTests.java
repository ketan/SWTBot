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
package org.eclipse.swtbot.eclipse.ui.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.swtbot.eclipse.ui.preferences.PreferenceInitializerTest;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: AllTests.java 4 2008-12-16 16:56:08Z kpadegaonka $
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.swtbot.eclipse.ui.test");
		//$JUnit-BEGIN$

		// Widgets
		suite.addTestSuite(PreferenceInitializerTest.class);

		//$JUnit-END$

		return suite;
	}

}

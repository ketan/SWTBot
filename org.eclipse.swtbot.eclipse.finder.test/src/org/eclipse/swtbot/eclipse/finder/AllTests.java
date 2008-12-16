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
package org.eclipse.swtbot.eclipse.finder;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.swtbot.eclipse.finder.exceptions.QuickFixNotFoundExceptionTest;
import org.eclipse.swtbot.eclipse.finder.finders.CommandFinderTest;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditorTest;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotViewTest;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: AllTests.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.swtbot.eclipse.finder");
		//$JUnit-BEGIN$

		// Exception
		suite.addTestSuite(QuickFixNotFoundExceptionTest.class);

		// Finder
		suite.addTestSuite(CommandFinderTest.class);

		// Widgets
		suite.addTestSuite(SWTBotViewTest.class);
		suite.addTestSuite(SWTBotEclipseEditorTest.class);

		//$JUnit-END$

		return suite;
	}

}

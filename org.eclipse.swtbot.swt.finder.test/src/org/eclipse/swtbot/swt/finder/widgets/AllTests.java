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
package org.eclipse.swtbot.swt.finder.widgets;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.swtbot.swt.finder.widgets");
		//$JUnit-BEGIN$
		suite.addTestSuite(SWTBotComboTest.class);
		suite.addTestSuite(SWTBotToolbarButtonWithToolTipTest.class);
		suite.addTestSuite(SWTBotTabItemTest.class);
		suite.addTestSuite(SWTBotTableTest2.class);
		suite.addTestSuite(SWTBotTableClickTest.class);
		suite.addTestSuite(SWTBotTableItemTest.class);
		suite.addTestSuite(SWTBotTextTest.class);
		suite.addTestSuite(SWTBotTableDoubleClickTest.class);
		suite.addTestSuite(SWTBotLabelTest.class);
		suite.addTestSuite(SWTBotDateTimeTest.class);
		suite.addTestSuite(SWTBotMenuTest2.class);
		suite.addTestSuite(SWTBotListTest.class);
		suite.addTestSuite(SWTBotButtonTest.class);
		suite.addTestSuite(SWTBotToolbarDropDownButtonWithToolTipTest.class);
		suite.addTestSuite(SWTBotPopupMenuTest.class);
		suite.addTestSuite(SWTBotTableTest.class);
		suite.addTestSuite(SWTBotTableColumnTest.class);
		suite.addTestSuite(SWTBotCheckBoxTest.class);
		suite.addTestSuite(AbstractSWTBotTest.class);
		suite.addTestSuite(SWTBotCComboTest.class);
		suite.addTestSuite(SWTBotTreeTest.class);
		suite.addTestSuite(SWTBotShellTest.class);
		suite.addTestSuite(SWTBotStyledTextTest.class);
		suite.addTestSuite(SWTBotToolbarDropDownButtonTest.class);
		suite.addTestSuite(SWTBotTreeItemTest.class);
		suite.addTestSuite(SWTBotRadioTest.class);
		suite.addTestSuite(SWTBotMenuTest.class);
		suite.addTestSuite(SWTBotToolbarButtonTest.class);
		suite.addTestSuite(SWTBotCLabelTest.class);
		//$JUnit-END$
		return suite;
	}

}

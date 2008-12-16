import junit.framework.Test;
import junit.framework.TestSuite;

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

/**
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for default package");
		suite.addTest(org.eclipse.swtbot.swt.finder.AllTests.suite());
		suite.addTest(org.eclipse.swtbot.swt.finder.collections.AllTests.suite());
		suite.addTest(org.eclipse.swtbot.swt.finder.finders.AllTests.suite());
		suite.addTest(org.eclipse.swtbot.swt.finder.matchers.AllTests.suite());
		suite.addTest(org.eclipse.swtbot.swt.finder.resolvers.AllTests.suite());
		suite.addTest(org.eclipse.swtbot.swt.finder.utils.AllTests.suite());
		suite.addTest(org.eclipse.swtbot.swt.finder.waits.AllTests.suite());
		suite.addTest(org.eclipse.swtbot.swt.finder.widgets.AllTests.suite());
		return suite;
	}

}

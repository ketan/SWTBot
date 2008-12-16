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

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.rcpmail");
		//$JUnit-BEGIN$
		suite.addTestSuite(MessageCreateTest.class);
		suite.addTestSuite(MyFirstTest.class);
		suite.addTestSuite(MySecondTest.class);
		//$JUnit-END$
		return suite;
	}

}

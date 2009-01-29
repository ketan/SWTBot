/*******************************************************************************
 * Copyright (c) 2009 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.junit4.headless;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class XMLJUnitResultFormatter extends RunListener {

	public void testAssumptionFailure(Failure failure) {
		System.out.println("XMLJUnitResultFormatter.testAssumptionFailure()");
	}

	public void testFailure(Failure failure) throws Exception {
		System.out.println("XMLJUnitResultFormatter.testFailure()");
	}

	public void testFinished(Description description) throws Exception {
		System.out.println("XMLJUnitResultFormatter.testFinished()");
	}

	public void testIgnored(Description description) throws Exception {
		System.out.println("XMLJUnitResultFormatter.testIgnored()");
	}

	public void testRunFinished(Result result) throws Exception {
		System.out.println("XMLJUnitResultFormatter.testRunFinished()");
	}

	public void testRunStarted(Description description) throws Exception {
		System.out.println("XMLJUnitResultFormatter.testRunStarted()");
	}

	public void testStarted(Description description) throws Exception {
		System.out.println("XMLJUnitResultFormatter.testStarted()");
	}

}

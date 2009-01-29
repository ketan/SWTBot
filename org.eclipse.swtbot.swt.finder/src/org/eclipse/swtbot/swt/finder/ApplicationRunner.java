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
package org.eclipse.swtbot.swt.finder;

import org.eclipse.swtbot.swt.finder.utils.SWTUtils;

/**
 * Starts the application under test.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public interface ApplicationRunner {

	/**
	 * Starts the application. <b>Note:</b> This will <b>not</b> be called on the same thread as the junit test. So it's
	 * safe to call your application's main class directly.
	 */
	void startApplication();

	/**
	 * Returns <code>true</code> if the application is running, <code>false</code> otherwise. A typical implementation
	 * would be to wait until {@link SWTUtils#display()} returns something.
	 * 
	 * @return <code>true</code> if the application is running, <code>false</code> otherwise.
	 */
	boolean isApplicationRunning();

}

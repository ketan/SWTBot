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

import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.finder.SWTBotEclipseTestCase;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.junit.Test;

public class MySecondTest extends SWTBotEclipseTestCase {

	@Test public void ThatSWTBotDoesNotRunOnTheUIThread() throws Exception {
		assertNull(Display.getCurrent());
		assertNotSame(Thread.currentThread(), SWTUtils.display().getThread());
	}


}

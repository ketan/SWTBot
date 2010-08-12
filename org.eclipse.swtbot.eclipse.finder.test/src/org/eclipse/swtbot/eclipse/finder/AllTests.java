/*******************************************************************************
 * Copyright (c) 2008,2009,2010 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder;

import org.eclipse.swtbot.eclipse.finder.exceptions.QuickFixNotFoundExceptionTest;
import org.eclipse.swtbot.eclipse.finder.finders.CommandFinderTest;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditorTest;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotMultiPageEditorTest;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotViewTest;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJUnit4Suite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@RunWith(SWTBotJUnit4Suite.class)
@SuiteClasses({
		QuickFixNotFoundExceptionTest.class,
		CommandFinderTest.class,
		SWTBotEclipseEditorTest.class,
		SWTBotViewTest.class,
		SWTBotMultiPageEditorTest.class })
public class AllTests {
}

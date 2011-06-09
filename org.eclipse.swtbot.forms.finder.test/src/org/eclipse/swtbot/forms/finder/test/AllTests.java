/*******************************************************************************
 * Copyright (c) 2011 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.forms.finder.test;

import org.eclipse.swtbot.forms.finder.test.widgets.SWTBotHyperlinkTest;
import org.eclipse.swtbot.forms.finder.test.widgets.SWTBotImageHyperlinkTest;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJUnit4Suite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 */
@RunWith(SWTBotJUnit4Suite.class)
@SuiteClasses({
	SWTBotHyperlinkTest.class,
	SWTBotImageHyperlinkTest.class,
	})
public class AllTests {

}

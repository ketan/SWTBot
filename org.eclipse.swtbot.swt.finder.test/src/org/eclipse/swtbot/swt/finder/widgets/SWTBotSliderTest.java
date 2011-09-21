/*******************************************************************************
 * Copyright (c) 2009 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Yann N. Dauphin - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.swtbot.swt.finder.test.AbstractControlExampleTest;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Yann N. Dauphin
 * @version $Id$
 */
public class SWTBotSliderTest extends AbstractControlExampleTest {

	@Test
	public void findsSlider() throws Exception {
		assertNotNull(bot.slider().widget);
	}

	@Test
	public void findsSliderInGroup() throws Exception {
		assertNotNull(bot.sliderInGroup("Slider").widget);
	}

	@Test
	public void canSetSelection() throws Exception {
		bot.sliderInGroup("Slider").setSelection(11);
		assertEquals(11, bot.sliderInGroup("Slider").getSelection());
	}

	@Before
	public void prepareExample() throws Exception {
		bot.tabItem("Slider").activate();
	}

}

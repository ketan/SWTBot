/*******************************************************************************
 * Copyright (c) 2010 Chris Aniszczyk and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Chris Aniszczyk <caniszczyk@gmail.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.forms.finder.test.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.swtbot.forms.finder.test.AbstractSWTBotFormsTestCase;
import org.eclipse.swtbot.forms.finder.widgets.SWTBotImageHyperlink;
import org.junit.Test;

public class SWTBotImageHyperlinkTest extends AbstractSWTBotFormsTestCase {

	@Test
	public void findImageHyperlink() throws Exception {
		SWTBotImageHyperlink link = bot.imageHyperlink("Image link with no image");
		assertNotNull(link);
		assertEquals("Image link with no image", link.getText());
	}
	
}

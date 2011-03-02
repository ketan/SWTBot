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

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withRegex;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.swtbot.forms.finder.widgets.SWTBotImageHyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.junit.Test;

public class SWTBotImageHyperlinkTest extends AbstractSWTBotFormsTest {

	@Test
	public void findImageHyperlinkWithNoImage() throws Exception {
		String text = "Image link with no image";
		SWTBotImageHyperlink link = bot.imageHyperlink(text);
		assertNotNull(link);
		assertEquals(text, link.getText());
		assertEquals(ImageHyperlink.class, link.widget.getClass());
	}
	
	@Test
	public void canFindImageHyperlinkWithNoImageByRegex() throws Exception {
		String text = "link with no";
		SWTBotImageHyperlink link = new SWTBotImageHyperlink((ImageHyperlink) bot.widget(withRegex(text)));

		assertNotNull(link);
		assertEquals("Image link with no image", link.getText());
		assertEquals(ImageHyperlink.class, link.widget.getClass());
	}
	
	
	@Test
	public void findImageHyperlinkWithImage() throws Exception {
		String text = "Link with image and text";
		SWTBotImageHyperlink link = bot.imageHyperlink(text);
		assertNotNull(link);
		assertEquals(text, link.getText());
		assertEquals(ImageHyperlink.class, link.widget.getClass());
	}
	
	@Test
	public void canFindImageHyperlinkWithImageByRegex() throws Exception {
		String text = "with image";
		SWTBotImageHyperlink link = new SWTBotImageHyperlink((ImageHyperlink) bot.widget(withRegex(text)));

		assertNotNull(link);
		assertEquals("Link with image and text", link.getText());
		assertEquals(ImageHyperlink.class, link.widget.getClass());
	}
}

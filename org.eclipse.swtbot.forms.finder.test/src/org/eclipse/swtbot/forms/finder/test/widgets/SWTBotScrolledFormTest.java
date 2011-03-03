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

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withRegex;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.forms.finder.widgets.SWTBotScrolledForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.junit.Test;

public class SWTBotScrolledFormTest extends AbstractSWTBotFormsTest {

	@Test
	public void findsScrolledFormText() throws Exception {
		String title = "Hello, Eclipse Forms";
		SWTBotScrolledForm text = bot.scrolledForm();
		assertNotNull(text);
		assertEquals(title, text.getText());
		assertEquals(ScrolledForm.class, text.widget.getClass());
	}

	@Test
	public void findsScrolledFormTextByRegex() throws Exception {
		String regex = "Hello, [Ee]clip.. Forms";
		Widget widget = bot.widget(allOf(widgetOfType(ScrolledForm.class), withRegex(regex)));
		SWTBotScrolledForm text = new SWTBotScrolledForm((ScrolledForm) widget);
		assertNotNull(text);
		assertEquals("Hello, Eclipse Forms", text.getText());
		assertEquals(ScrolledForm.class, text.widget.getClass());
	}
}

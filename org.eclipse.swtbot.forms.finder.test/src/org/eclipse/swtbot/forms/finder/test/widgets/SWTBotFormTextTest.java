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

import static org.junit.Assert.assertNotNull;

import org.eclipse.swtbot.forms.finder.test.AbstractSWTBotFormsTestCase;
import org.eclipse.swtbot.forms.finder.widgets.SWTBotFormText;
import org.junit.Test;

public class SWTBotFormTextTest extends AbstractSWTBotFormsTestCase {

	@Test
	public void findsFormText() throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("<form>");
		buf.append("<p>");
		buf.append("Here is some plain text for the text to render; ");
		buf.append("this text is at <a href=\"http://www.eclipse.org\" nowrap=\"true\">http://www.eclipse.org</a> web site.");
		buf.append("</p>");
		buf.append("<p>");
		buf.append("<span color=\"header\" font=\"header\">This text is in header font and color.</span>");
		buf.append("</p>");
		buf.append("<p>This line will contain some <b>bold</b> and some <span font=\"code\">source</span> text. ");
		buf.append("We can also add <img href=\"image\"/> an image. ");
		buf.append("</p>");
		buf.append("<li>A default (bulleted) list item.</li>");
		buf.append("<li>Another bullet list item.</li>");
		buf.append("<li style=\"text\" value=\"1.\">A list item with text.</li>");
		buf.append("<li style=\"text\" value=\"2.\">Another list item with text</li>");
		buf.append("<li style=\"image\" value=\"image\">List item with an image bullet</li>");
		buf.append("<li style=\"text\" bindent=\"20\" indent=\"40\" value=\"3.\">A list item with text.</li>");
		buf.append("<li style=\"text\" bindent=\"20\" indent=\"40\" value=\"4.\">A list item with text.</li>");
		buf.append("<p>     leading blanks;      more white \n\n new lines   <br/><br/><br/> \n more <b>   bb   </b>  white  . </p>");
		buf.append("</form>");
		SWTBotFormText text = bot.formText();
		assertNotNull(text);
		System.out.println(text.widget.getSelectionText());
	}
	
}

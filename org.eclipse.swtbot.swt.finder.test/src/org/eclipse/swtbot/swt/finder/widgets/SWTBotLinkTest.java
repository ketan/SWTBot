/*******************************************************************************
 * Copyright (c) 2009 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotLinkTest extends AbstractSWTTestCase {

	private SWTBotText	listeners;

	@Test
	public void clicksOnALink() throws Exception {
		SWTBotLink link = bot.link();
		link.click();
		assertEventMatches(listeners.getText(), "MouseDown [3]: MouseEvent{Link {Visit the <A HREF=\"www.eclipse.org\">Eclipse.org</A> project and the <A HREF=\"www.eclipse.org\\swt\">SWT</A> homepage or <a>Foo</a>.} time=1561910868 data=null button=1 stateMask=0 x=234 y=10 count=1}"); 	
		assertEventMatches(listeners.getText(), "MouseUp [4]: MouseEvent{Link {Visit the <A HREF=\"www.eclipse.org\">Eclipse.org</A> project and the <A HREF=\"www.eclipse.org\\swt\">SWT</A> homepage or <a>Foo</a>.} time=1561910872 data=null button=1 stateMask=524288 x=234 y=10 count=1}");
	}

	@Test
	public void clicksOnALinkWithHyperlinkTextAndHREF() throws Exception {
		SWTBotLink link = bot.link();
		link.click("SWT");
		assertEventMatches(listeners.getText(), "MouseDown [3]: MouseEvent{Link {Visit the <A HREF=\"www.eclipse.org\">Eclipse.org</A> project and the <A HREF=\"www.eclipse.org\\swt\">SWT</A> homepage or <a>Foo</a>.} time=1561910868 data=null button=1 stateMask=0 x=234 y=10 count=1}"); 	
		assertEventMatches(listeners.getText(), "Selection [13]: SelectionEvent{Link {Visit the <A HREF=\"www.eclipse.org\">Eclipse.org</A> project and the <A HREF=\"www.eclipse.org\\swt\">SWT</A> homepage or <a>Foo</a>.} time=1561910872 data=null item=null detail=0 x=0 y=0 width=0 height=0 stateMask=0 text=www.eclipse.org\\swt doit=true}");
		assertEventMatches(listeners.getText(), "MouseUp [4]: MouseEvent{Link {Visit the <A HREF=\"www.eclipse.org\">Eclipse.org</A> project and the <A HREF=\"www.eclipse.org\\swt\">SWT</A> homepage or <a>Foo</a>.} time=1561910872 data=null button=1 stateMask=524288 x=234 y=10 count=1}");
	}

	@Test
	public void clicksOnALinkWithHyperlinkTextAndNoHREF() throws Exception {
		SWTBotLink link = bot.link();
		link.click("Foo");
		assertEventMatches(listeners.getText(), "MouseDown [3]: MouseEvent{Link {Visit the <A HREF=\"www.eclipse.org\">Eclipse.org</A> project and the <A HREF=\"www.eclipse.org\\swt\">SWT</A> homepage or <a>Foo</a>.} time=1561910868 data=null button=1 stateMask=0 x=234 y=10 count=1}"); 	
		assertEventMatches(listeners.getText(), "Selection [13]: SelectionEvent{Link {Visit the <A HREF=\"www.eclipse.org\">Eclipse.org</A> project and the <A HREF=\"www.eclipse.org\\swt\">SWT</A> homepage or <a>Foo</a>.} time=1561910872 data=null item=null detail=0 x=0 y=0 width=0 height=0 stateMask=0 text=Foo doit=true}");
		assertEventMatches(listeners.getText(), "MouseUp [4]: MouseEvent{Link {Visit the <A HREF=\"www.eclipse.org\">Eclipse.org</A> project and the <A HREF=\"www.eclipse.org\\swt\">SWT</A> homepage or <a>Foo</a>.} time=1561910872 data=null button=1 stateMask=524288 x=234 y=10 count=1}");
	}

	public void setUp() throws Exception {
		super.setUp();
		bot.tabItem("Link").activate();
		bot.checkBox("Listen").select();
		bot.button("Clear").click();
		listeners = bot.textInGroup("Listeners");
	}

}



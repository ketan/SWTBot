/*******************************************************************************
 * Copyright (c) 2008 Cedric Chabanois and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cedric Chabanois - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.finders;

import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertText;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.hamcrest.Matcher;
import org.junit.Test;

/**
 * @author Cedric Chabanois &lt;cchabanois [at] no-log [dot] org&gt;
 * @version $Id$
 */
public class ChildrenControlFinderTest extends AbstractSWTTestCase {

	@SuppressWarnings("unchecked")
	// varargs and generics doesn't mix well!
	@Test
	public void childrenControlFinder1() throws Exception {
		Group group = getGroup("Text Buttons");

		ChildrenControlFinder finder = new ChildrenControlFinder(group);
		Matcher<Button> withText = withText("One");
		List<Button> buttons = finder.findControls(allOf(widgetOfType(Button.class), withText));

		assertEquals(1, buttons.size());

		Button button = buttons.get(0);
		assertText("One", button);
		assertNull(getImage(button));
	}

	@SuppressWarnings("unchecked")
	// varargs and generics doesn't mix well!
	@Test
	public void childrenControlFinder2() throws Exception {
		Group group = getGroup("Image and Text Buttons");
		ChildrenControlFinder finder = new ChildrenControlFinder(group);

		Matcher<Button> withText = withText("One");
		List<Button> buttons = finder.findControls(allOf(widgetOfType(Button.class), withText));
		assertEquals(1, buttons.size());
		Button button = buttons.get(0);

		assertText("One", button);
		assertNotNull(getImage(button));
	}

	@SuppressWarnings("unchecked")
	// varargs and generics doesn't mix well!
	private Group getGroup(String text) {
		ControlFinder finder = new ControlFinder();
		Matcher<Group> withText = withText(text);
		List<Group> groups = finder.findControls(allOf(widgetOfType(Group.class), withText));
		assertEquals(1, groups.size());
		return groups.get(0);
	}

	private Image getImage(final Button button) {
		return UIThreadRunnable.syncExec(new Result<Image>() {
			public Image run() {
				return button.getImage();
			}
		});
	}

	public void setUp() throws Exception {
		super.setUp();
		new SWTBot().tabItem("Button").activate();
	}

}

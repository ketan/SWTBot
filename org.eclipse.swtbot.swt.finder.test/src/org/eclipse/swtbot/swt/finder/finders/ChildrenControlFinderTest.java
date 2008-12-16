/*******************************************************************************
 *  Copyright 2007 new SWTBot, http://swtbot.org/
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

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import java.util.List;


import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.ChildrenControlFinder;
import org.eclipse.swtbot.swt.finder.finders.ControlFinder;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;

/**
 * @author Cedric Chabanois &lt;cchabanois [at] no-log [dot] org&gt;
 * @version $Id$
 */
public class ChildrenControlFinderTest extends AbstractSWTTestCase {

	public void testChildrenControlFinder1() throws Exception {
		Group group = getGroup("Text Buttons");

		ChildrenControlFinder finder = new ChildrenControlFinder(group);
		List buttons = finder.findControls(allOf(instanceOf(Button.class), withText("One")));

		assertEquals(1, buttons.size());

		Button button = (Button) buttons.get(0);
		assertText("One", button);
		assertNull(getImage(button));
	}

	public void testChildrenControlFinder2() throws Exception {
		Group group = getGroup("Image and Text Buttons");
		ChildrenControlFinder finder = new ChildrenControlFinder(group);

		List buttons = finder.findControls(allOf(instanceOf(Button.class), withText("One")));
		assertEquals(1, buttons.size());
		Button button = (Button) buttons.get(0);

		assertText("One", button);
		assertNotNull(getImage(button));
	}

	private Group getGroup(String text) {
		ControlFinder finder = new ControlFinder();
		List groups = finder.findControls(allOf(instanceOf(Group.class), withText(text)));
		assertEquals(1, groups.size());
		return (Group) groups.get(0);
	}

	private Image getImage(final Button button) {
		return UIThreadRunnable.syncExec(new Result<Image>() {
			public Image run() {
				return button.getImage();
			}
		});
	}

	protected void setUp() throws Exception {
		super.setUp();
		new  SWTBot().tabItem("Button").activate();
	}

}
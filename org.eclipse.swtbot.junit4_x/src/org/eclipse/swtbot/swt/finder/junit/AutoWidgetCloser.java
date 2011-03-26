/*******************************************************************************
 * Copyright (c) 2011 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Marc Philipp - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.junit;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swtbot.swt.finder.widgets.CloseableWidget;
import org.junit.rules.ExternalResource;

/**
 * Closes previously added instances of {@link CloseableWidget} after the
 * execution of test cases, useful for cleaning up after tests.
 * 
 * @author Marc Philipp, http://github.com/marcphilipp
 */
public class AutoWidgetCloser extends ExternalResource {

	private final List<CloseableWidget> widgets = new LinkedList<CloseableWidget>();

	@Override
	protected void after() {
		for (CloseableWidget widget : widgets) {
			widget.close();
		}
	}

	public <T extends CloseableWidget> T add(T widget) {
		widgets.add(0, widget);
		return widget;
	}
}

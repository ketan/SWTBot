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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.swtbot.swt.finder.widgets.CloseableWidget;
import org.junit.Test;

public class AutoWidgetCloserTest {

	private final static class BuggyWidget implements CloseableWidget {
		public void close() {
			throw new RuntimeException("expected");
		}
	}

	private final static class NormalWidget implements CloseableWidget {

		private boolean closed;

		public boolean isClosed() {
			return closed;
		}

		public void close() {
			closed = true;
		}
	}

	public AutoWidgetCloser autoCloser = new AutoWidgetCloser();

	@Test
	public void sameInstanceIsReturned() throws Exception {
		CloseableWidget widget = new NormalWidget();
		assertSame(widget, autoCloser.add(widget));
	}

	@Test
	public void closesSingleWidget() throws Exception {
		NormalWidget widget = new NormalWidget();
		autoCloser.add(widget);

		autoCloser.after();
		assertTrue(widget.isClosed());
	}

	@Test(expected = RuntimeException.class)
	public void rethrowsExceptionWhenClosing() throws Exception {
		autoCloser.add(new BuggyWidget());
		autoCloser.after();
	}

	@Test
	public void closesLastWidgetFirst() throws Exception {
		NormalWidget secondWidget = new NormalWidget();
		autoCloser.add(new BuggyWidget());
		autoCloser.add(secondWidget);

		try {
			autoCloser.after();
			fail("Exception expected");
		} catch (Exception expected) {
			assertTrue(secondWidget.isClosed());
		}
	}
}

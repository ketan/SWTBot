/*******************************************************************************
 * Copyright (c) 2008 David Green and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     David Green - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.dsl;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;

/**
 * A DSL for manipulating the workbench. Clients can implement their own workbench interface for performing these
 * operations, which may be required for testing a custom RCP application, that needs to have a different implementation
 * for performing these operations.
 * <p>
 * Sample usage:
 * </p>
 * 
 * <pre>
 * EclipseIDE.workbench().switchPerspectives(&quot;Java&quot;).resetPerspective().reset();
 * </pre>
 * 
 * @author David Green
 */
public interface Workbench {
	/**
	 * switch to the named perspective
	 * 
	 * @param perspectiveName the name of the perspective as it appears in the UI
	 * @throws WidgetNotFoundException
	 * @throws TimeoutException
	 */
	public abstract Workbench switchPerspectives(String perspectiveName) throws WidgetNotFoundException, TimeoutException;

	/**
	 * reset the current perspective to its defaults
	 * 
	 * @throws WidgetNotFoundException
	 * @throws TimeoutException
	 */
	public abstract Workbench resetPerspective() throws WidgetNotFoundException, TimeoutException;

	/**
	 * Reset the workbench such that all open dialogs are closed. This is typically called in preparation for starting a
	 * new test.
	 * 
	 * @throws WidgetNotFoundException
	 * @throws TimeoutException
	 */
	public abstract void reset() throws WidgetNotFoundException, TimeoutException;

	// public abstract PackageExplorer packageExplorer() throws WidgetNotFoundException;
	// public abstract OutlineView outlineView() throws WidgetNotFoundException;
}

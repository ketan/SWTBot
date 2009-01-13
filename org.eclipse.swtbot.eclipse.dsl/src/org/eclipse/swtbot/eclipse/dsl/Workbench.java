/*******************************************************************************
 * Copyright (c) 2009 David Green and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     David Green - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.dsl;

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
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface Workbench {
	/**
	 * Switch to the named perspective
	 * 
	 * @param perspectiveName the name of the perspective as it appears in the UI
	 * @return itself
	 */
	public Workbench switchToPerspective(String perspectiveName);

	/**
	 * Reset the current perspective to its defaults.
	 * 
	 * @return itself
	 */
	public Workbench resetPerspective();

	/**
	 * Reset the workbench such that all open editors are saved and closed, all open dialogs are closed. This is
	 * typically called in preparation for starting a new test, or cleaning up after a test.
	 * 
	 * @return itself
	 */
	public Workbench resetWorkbench();

	/**
	 * Closes all the open shells in the workbench. Does not close the workbench shell itself.
	 * 
	 * @return itself
	 */
	public Workbench closeAllShells();

	/**
	 * Closes all the open editors in the workbench.
	 * 
	 * @return itself
	 */
	public Workbench closeAllEditors();

	/**
	 * Saves all the open editors in the workbench.
	 * 
	 * @return itself
	 */
	public Workbench saveAllEditors();

}

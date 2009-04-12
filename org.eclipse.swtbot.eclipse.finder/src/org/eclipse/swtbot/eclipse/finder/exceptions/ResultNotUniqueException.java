/*******************************************************************************
 * Copyright (c) 2009 SWTBot Committers and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ralf Ebert www.ralfebert.de - (bug 271630) SWTBot Improved RCP / Workbench support
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder.exceptions;

import java.util.Collection;

import org.hamcrest.Matcher;

/**
 * Thrown when a particular result is not unique, when it should be. For e.g. two perspectives or views with the same
 * id.
 * 
 * @author Ralf Ebert www.ralfebert.de - (bug 271630) SWTBot Improved RCP / Workbench support
 * @version $Id$
 */
public class ResultNotUniqueException extends RuntimeException {

	private static final long	serialVersionUID	= 1L;

	private ResultNotUniqueException(String message) {
		super(message);
	}

	/**
	 * Creates an exception based on the matcher and matches it found.
	 * @param matcher the matcher.
	 * @param matches the matches found by the matcher.
	 */
	public ResultNotUniqueException(Matcher<?> matcher, Collection<?> matches) {
		this("Matcher results not unique " + matcher + " -> " + matches);
	}

}

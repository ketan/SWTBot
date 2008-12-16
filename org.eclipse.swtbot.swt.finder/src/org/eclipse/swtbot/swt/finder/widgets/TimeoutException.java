/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

/**
 * This is an exception that is thrown when a timeout occurs waiting for something (e.g. a condition) to complete.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: TimeoutException.java 1188 2008-12-02 06:55:23Z kpadegaonkar $
 */
public class TimeoutException extends RuntimeException {

	/** the serialVersionUID */
	private static final long	serialVersionUID	= -4097219100771019730L;

	/**
	 * Constructs the exception with the given message.
	 * 
	 * @param message the message.
	 */
	public TimeoutException(String message) {
		super(message);
	}
}

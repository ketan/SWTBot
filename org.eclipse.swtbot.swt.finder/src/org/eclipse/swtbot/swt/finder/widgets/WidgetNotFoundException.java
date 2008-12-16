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
 * An exception that is thrown when a widget can not be found on the given display/shell.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class WidgetNotFoundException extends RuntimeException {

	/** the serialVersionUID */
	private static final long	serialVersionUID	= -4097219100771019730L;

	/**
	 * Constructs an exception with the given message.
	 * 
	 * @param message the message.
	 */
	public WidgetNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructs this exception with the given message and cause.
	 * 
	 * @param message the message.
	 * @param cause the underlying cause.
	 */
	public WidgetNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}

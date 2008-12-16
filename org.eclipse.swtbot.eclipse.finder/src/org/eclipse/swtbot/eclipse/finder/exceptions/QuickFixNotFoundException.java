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
package org.eclipse.swtbot.eclipse.finder.exceptions;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class QuickFixNotFoundException extends Exception {
	/**
	 * The serialization version identifier.
	 */
	private static final long	serialVersionUID	= -6129340921268076590L;

	/**
	 * Cosntructs an instance with the given message.
	 * 
	 * @param message the message.
	 */
	public QuickFixNotFoundException(String message) {
		super(message);
	}

	/**
	 * Cosntructs an instance with the given message and cause.
	 * 
	 * @param message the message.
	 * @param cause the root cause.
	 */
	public QuickFixNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Cosntructs an instance with the given cause.
	 * 
	 * @param cause the root cause.
	 */
	public QuickFixNotFoundException(Throwable cause) {
		super(cause);
	}

}

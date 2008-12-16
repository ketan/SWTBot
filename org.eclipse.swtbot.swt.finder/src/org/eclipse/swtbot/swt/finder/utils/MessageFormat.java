/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Hans Schwaebli - http://swtbot.org/bugzilla/show_bug.cgi?id=112
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.utils;

import java.util.Arrays;

/**
 * Message formatter to optimize logging performance. The cost of logging is mostly in the string concatenation and
 * parameter evaluation of arguments. Log4j logs the object by invoking {@link #toString()} on the object being logged.
 * This class performs lazy evaluation of the message, only when {@link #toString()} is invoked, which happens at the
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: MessageFormat.java 1202 2008-12-02 09:01:13Z kpadegaonkar $
 */
public class MessageFormat {
	private final String	s;
	private final Object[]	args;

	public MessageFormat(String s, Object... args) {
		this.s = s;
		this.args = args;
	}

	public static MessageFormat format(String s, Object... args) {
		return new MessageFormat(s, args);
	}

	public String toString() {
		try {
			return java.text.MessageFormat.format(s, args);
		} catch (Exception e) {
			return "MessageFormat: Could not translate message: '" + s + "' using arguments " + Arrays.asList(args);
		}
	}
}

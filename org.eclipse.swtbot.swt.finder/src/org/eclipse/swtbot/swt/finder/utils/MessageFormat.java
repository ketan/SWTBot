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
import java.util.Map;

import org.apache.commons.collections.map.LRUMap;

/**
 * Message formatter to optimize logging performance. The cost of logging is mostly in the string concatenation and
 * parameter evaluation of arguments. Log4j logs the object by invoking {@link #toString()} on the object being logged.
 * This class performs lazy evaluation of the message, only when {@link #toString()} is invoked, which happens at the
 * time of logging.
 * <p>
 * <b>Note:</b> This class uses a ThreadLocal cache instead of a single cache, since the cache would then need to be
 * synchronized since multiple threads would access it.
 * </p>
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class MessageFormat extends ThreadLocal<Map> {

	private final String	pattern;
	private final Object[]	args;

	private MessageFormat(String pattern, Object... args) {
		this.pattern = pattern;
		this.args = args;
	}

	public static MessageFormat format(String s, Object... args) {
		return new MessageFormat(s, args);
	}

	public String toString() {
		try {
			return formatter(pattern).format(args);
		} catch (Exception e) {
			return "MessageFormat: Could not translate message: '" + pattern + "' using arguments " + Arrays.asList(args);
		}
	}

	protected Map initialValue() {
		return new LRUMap(512);
	}

	@SuppressWarnings("unchecked")
	private java.text.MessageFormat formatter(String pattern) {
		java.text.MessageFormat formatter = (java.text.MessageFormat) get().get(pattern);
		if (formatter == null) {
			formatter = new java.text.MessageFormat(pattern);
			get().put(pattern, formatter);
		}
		return formatter;
	}
}

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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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
public class MessageFormat {

	private final String													pattern;
	private final Object[]													args;
	private static final ThreadLocal<Map<String, java.text.MessageFormat>>	threadLocal	= new ThreadLocal<Map<String, java.text.MessageFormat>>() {
																							protected java.util.Map<String, java.text.MessageFormat> initialValue() {
																								return new LRUMap<String, java.text.MessageFormat>();
																							};
																						};

	private MessageFormat(String pattern, Object... args) {
		this.pattern = pattern;
		this.args = args;
	}

	public static MessageFormat format(String s, Object... args) {
		return new MessageFormat(s, args);
	}

	public String toString() {
		try {
			java.text.MessageFormat formatter = formatter(pattern);
			return formatter.format(args);
		} catch (Exception e) {
			return "MessageFormat: Could not translate message: '" + pattern + "' using arguments " + Arrays.asList(args); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	private java.text.MessageFormat formatter(String pattern) {
		java.text.MessageFormat formatter = threadLocal.get().get(pattern);
		if (formatter == null) {
			formatter = new java.text.MessageFormat(pattern);
			threadLocal.get().put(pattern, formatter);
		}
		return formatter;
	}

	private static final class LRUMap<K, V> extends LinkedHashMap<K, V> {
		private static final int	MAX_SIZE	= 512;

		protected boolean removeEldestEntry(Entry<K, V> eldest) {
			return size() > MAX_SIZE;
		}
	}
}

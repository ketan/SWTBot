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
package org.eclipse.swtbot.swt.recorder.generators;

import org.eclipse.core.runtime.Assert;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotAccessor {

	private final String	bot;
	private final String	methodCall;
	private final String	accessor;
	private final int		index;

	/**
	 * Create an accessor with the specified parameters.
	 * 
	 * @param bot the name of the bot.
	 * @param methodCall the name of the method call that will find the widget.
	 * @param accessor the accessor used to find the widget.
	 */
	public SWTBotAccessor(String bot, String methodCall, String accessor) {
		this(bot, methodCall, accessor, 0);
	}

	/**
	 * Create an accessor with the specified parameters.
	 * 
	 * @param bot the name of the bot.
	 * @param methodCall the name of the method call that will find the widget.
	 * @param accessor the accessor used to find the widget.
	 * @param index the index of the widget.
	 */
	public SWTBotAccessor(String bot, String methodCall, String accessor, int index) {
		Assert.isNotNull(bot);
		Assert.isNotNull(methodCall);
		Assert.isNotNull(accessor);
		Assert.isTrue(index >= 0);
		this.index = index;
		this.bot = bot;
		this.methodCall = methodCall;
		this.accessor = accessor;
	}

	public String toString() {
		return toJava();
	}

	/**
	 * Returns a string of the format {@code bot.button("Foo")} or {@code bot.button("Foo", 10)}
	 * 
	 * @return the Java representation of this accessor.
	 */
	public String toJava() {
		String index = "";
		if (this.index != 0)
			index = ", " + this.index;

		String accessor = "";
		if (!"".equals(this.accessor))
			accessor = "\"" + this.accessor + "\"";

		return bot + "." + methodCall + "(" + accessor + index + ")";
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SWTBotAccessor other = (SWTBotAccessor) obj;
		return accessor.equals(other.accessor) && bot.equals(other.bot) && (index == other.index) && methodCall.equals(other.methodCall);
	}

}

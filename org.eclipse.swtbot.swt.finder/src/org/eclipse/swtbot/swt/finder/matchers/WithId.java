/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Ketan Padegaonkar - http://swtbot.org/bugzilla/show_bug.cgi?id=126
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.matchers;


import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.hamcrest.Description;
import org.hamcrest.Factory;


/**
 * Tells if a particular widget has value for the given key.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: WithId.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 * @since 2.0
 */
public class WithId<T extends Widget> extends AbstractMatcher<T> {

	/**
	 * The key to use while matching widgets.
	 */
	private final String	key;
	/**
	 * The value to use while matching widgets.
	 */
	private final String	value;

	/**
	 * Matches a widget that has the specified Key/Value pair set as data into it.
	 *
	 * @see Widget#setData(String, Object)
	 * @param key the key
	 * @param value the value
	 */
	public WithId(String key, String value) {
		this.key = key;
		this.value = value;
	}

	protected boolean doMatch(final Object obj) {
		String data = UIThreadRunnable.syncExec(new Result<String>(){
			public String run() {
				return (String) ((Widget) obj).getData(key);
			}
		});
		return value.equals(data);
	}

	public void describeTo(Description description) {
		description.appendText("with key/value (").appendText(key).appendText("/").appendText(value).appendText(")");
	}

	/**
	 * Matches a widget that has the specified Key/Value pair set as data into it.
	 *
	 * @see org.eclipse.swt.widgets.Widget#setData(String, Object)
	 * @param key the key
	 * @param value the value
	 * @return a matcher.
	 */
	@Factory
	public static AbstractMatcher<? extends Widget> withId(String key, String value) {
		return new WithId<Widget>(key, value);
	}

	/**
	 * Matches a widget that has the specified value set for the key
	 * {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 *
	 * @see org.eclipse.swt.widgets.Widget#setData(String, Object)
	 * @param value the value
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static AbstractMatcher<? extends Widget> withId(String value) {
		return new WithId<Widget>(org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences.DEFAULT_KEY, value);
	}

}

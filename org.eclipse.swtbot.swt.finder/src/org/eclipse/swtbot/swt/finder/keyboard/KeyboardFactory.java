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
package org.eclipse.swtbot.swt.finder.keyboard;

import java.awt.Robot;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.hamcrest.SelfDescribing;

/**
 * Creates a keyboard using the specified strategy.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class KeyboardFactory {

	final Class<?>	strategyClass;

	/**
	 * @param strategyClass the name of the class that is an instance of {@link KeyboardStrategy}.
	 * @throws Exception if the class cannot be instantiated.
	 */
	public KeyboardFactory(String strategyClass) throws Exception {
		this(Class.forName(strategyClass));
	}

	/**
	 * @param strategyClass the class representing an instance of {@link KeyboardStrategy}.
	 * @throws Exception if the class cannot be instantiated.
	 */
	public KeyboardFactory(Class<?> strategyClass) throws Exception {
		this.strategyClass = strategyClass;
		createStrategy();
	}

	/**
	 * @param widget the widget on which the mock events are typed.
	 * @param description the description of the widget.
	 * @return the keyboard configured with the specified strategy.
	 */
	public Keyboard keyboard(Widget widget, SelfDescribing description) {
		return new Keyboard(strategy(widget, description));
	}

	private KeyboardStrategy strategy(Widget widget, SelfDescribing description) {
		try {
			KeyboardStrategy strategy = createStrategy();
			strategy.init(widget, description);
			return strategy;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private KeyboardStrategy createStrategy() throws Exception {
		return (KeyboardStrategy) strategyClass.newInstance();
	}

	/**
	 * @param widget the widget on which the mock events are typed.
	 * @param description the description of the widget.
	 * @return the default keyboard as defined by {@link SWTBotPreferences#keyboardStrategy()}.
	 */
	public static Keyboard getDefaultKeyboard(Widget widget, SelfDescribing description) {
		try {
			return new KeyboardFactory(SWTBotPreferences.keyboardStrategy()).keyboard(widget, description);
		} catch (Exception e) {
			return getAWTKeyboard();
		}
	}

	/**
	 * Creates a keyboard that creates mock events directly pumped to the widget.
	 * 
	 * @param widget the widget on which the mock events are typed.
	 * @param description the description of the widget.
	 * @return a keyboard
	 */
	public static Keyboard getMockKeyboard(Widget widget, SelfDescribing description) {
		MockKeyboardStrategy strategy = new MockKeyboardStrategy();
		strategy.init(widget, description);
		return new Keyboard(strategy);
	}

	/**
	 * Creates a keyboard that uses AWT {@link Robot} to press keys.
	 * 
	 * @return a keyboard.
	 */
	public static Keyboard getAWTKeyboard() {
		return new Keyboard(new AWTKeyboardStrategy());
	}

	/**
	 * Creates a keyboard that uses {@link Display#post(Event)} to press keys.
	 * 
	 * @return a keyboard.
	 */
	public static Keyboard getSWTKeyboard() {
		return new Keyboard(new SWTKeyboardStrategy());
	}

}

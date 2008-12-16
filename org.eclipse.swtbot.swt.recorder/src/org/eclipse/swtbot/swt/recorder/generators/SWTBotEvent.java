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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.swt.recorder.methodargs.NullArgument;
import org.eclipse.swtbot.swt.recorder.methodargs.SWTBotEventArguments;
import org.eclipse.swtbot.swt.recorder.methodargs.StringArgument;
import org.eclipse.swtbot.swt.recorder.methodargs.StringArrayArgument;

/**
 * Represents an {@link Event} object as a method call that SWTBot can execute.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotEvent {

	private final String				methodCall;
	private final SWTBotEventArguments	arguments;

	/**
	 * @param methodCall the method to be invoked in order to generate an event, the method must not take any arguments.
	 */
	public SWTBotEvent(String methodCall) {
		this(methodCall, new NullArgument());
	}

	/**
	 * @param methodCall the method to be invoked in order to generate an event.
	 * @param arguments the arguments that the method takes.
	 */
	public SWTBotEvent(String methodCall, SWTBotEventArguments arguments) {
		if ((methodCall == null) || "".equals(methodCall.trim()))
			throw new IllegalArgumentException("Argument cannot be empty or null");
		Assert.isNotNull(arguments);
		this.methodCall = methodCall;
		this.arguments = arguments;
	}

	/**
	 * @param methodCall the method to be invoked in order to generate an event.
	 * @param arg the string argument that the method takes.
	 */
	public SWTBotEvent(String methodCall, String arg) {
		this(methodCall, new StringArgument(arg));
	}

	/**
	 * @param methodCall the method to be invoked in order to generate an event.
	 * @param arguments the string array that the method takes.
	 */
	public SWTBotEvent(String methodCall, String[] arguments) {
		this(methodCall, new StringArrayArgument(arguments));
	}

	public String toString() {
		return methodCall();
	}

	/**
	 * @return the {@link #methodCall()}
	 */
	public String methodCall() {
		return methodCall;
	}

	/**
	 * @return the arguments as a string.
	 */
	public String args() {
		return arguments.asString();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SWTBotEvent other = (SWTBotEvent) obj;
		return arguments.equals(other.arguments) && methodCall.equals(other.methodCall);
	}

	/**
	 * This is useful when the user interacts with the same control multiple times, but only the last event is relevant;
	 * or in case when multiple keystrokes in a textbox should generate one event with all the keystrokes.
	 * 
	 * @param event the event to collate.
	 * @return <code>true</code> if the specified event is similar to this instance.
	 */
	public boolean canCollate(SWTBotEvent event) {
		return methodCall.equals(event.methodCall);
	}

}

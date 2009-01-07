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
package org.eclipse.swtbot.swt.recorder.methodargs;

/**
 * Represents an array of string arguments.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class StringArrayArgument extends AbstractSWTBotEventArguments {

	/** the arguments to the array */
	protected final String[]	args;

	/**
	 * @param args the string arguments.
	 */
	public StringArrayArgument(String[] args) {
		this.args = args;
	}

	public String asString() {
		String result = "new String [] { "; //$NON-NLS-1$
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			result += "\"" + arg + "\", "; //$NON-NLS-1$ //$NON-NLS-2$
		}
		result = result.substring(0, result.lastIndexOf(", ")); //$NON-NLS-1$
		result = result + " }"; //$NON-NLS-1$
		return result;
	}

}

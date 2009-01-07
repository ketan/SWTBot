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

import org.eclipse.swtbot.swt.finder.utils.internal.Assert;

/**
 * Represents an argument of type {@link String}.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class StringArgument extends AbstractSWTBotEventArguments {

	private final String	arg;

	/**
	 * @param arg the argument
	 */
	public StringArgument(String arg) {
		Assert.isNotNull(arg);
		this.arg = arg;
	}

	public String asString() {
		return "\"" + arg + "\""; //$NON-NLS-1$ //$NON-NLS-2$
	}
}

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
package org.eclipse.swtbot.swt.recorder.ui;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class Main {

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.err.println("Usage: java " + Main.class.getName() + " com.your.MainClass [arguments to your main...]"); //$NON-NLS-1$ //$NON-NLS-2$
			throw new IllegalArgumentException("Usage: java " + Main.class.getName() + " com.your.MainClass [arguments to your main...]"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		try {
			new Bootstrapper(mainClassName(args), getProgramArguments(args)).start();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private static String mainClassName(String[] args) {
		return args[0];
	}

	protected static String[] getProgramArguments(String[] args) {
		String[] trimmedArgs = new String[args.length - 1];
		System.arraycopy(args, 1, trimmedArgs, 0, args.length - 1);
		return trimmedArgs;
	}

}

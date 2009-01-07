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

	private final String[]	args;

	/**
	 * @param args the arguemnts to {@link Main}
	 */
	public Main(String[] args) {
		this.args = args;
	}

	public static void main(String[] args) throws Exception  {
		if (args.length == 0){
			System.err.println("Usage: java " + Main.class.getName() + " com.your.MainClass [arguments to your main...]"); //$NON-NLS-1$ //$NON-NLS-2$
			throw new IllegalArgumentException("Usage: java " + Main.class.getName() + " com.your.MainClass [arguments to your main...]"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		try{
			new Main(args).start();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	void start() throws Exception {
		final Class mainClass = Class.forName(mainClassName());
		Thread applicationThread = new Thread("ApplicationThread") { //$NON-NLS-1$
			public void run() {
				try {
					mainClass.getMethod("main", new Class[] { String[].class }).invoke(null, new Object[] { getProgramArguments() }); //$NON-NLS-1$
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
		applicationThread.start();
		new SWTBotRecorderUI().initialize();
		applicationThread.join();
	}

	private String mainClassName() {
		return args[0];
	}

	protected String[] getProgramArguments() {
		String[] trimmedArgs = new String[args.length - 1];
		System.arraycopy(args, 1, trimmedArgs, 0, args.length-1);
		return trimmedArgs;
	}

}

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
package org.eclipse.swtbot.generator;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotGeneratorMain.java 1194 2008-12-02 07:36:32Z kpadegaonkar $
 */
public class SWTBotGeneratorMain {

	public static void main(String[] args) {
		try {
			XmlConfigurator.main(new String[] { "widgets.xml", "../org.eclipse.swtbot.swt.finder/src", "org.eclipse.swtbot.swt.finder.SWTBot", "../org.eclipse.swtbot.swt.finder/src" });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

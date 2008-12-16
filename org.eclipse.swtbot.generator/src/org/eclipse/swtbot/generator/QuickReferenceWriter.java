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

import java.io.PrintStream;
import java.util.Set;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class QuickReferenceWriter implements HamcrestWriter {

	private final PrintStream	out;

	public QuickReferenceWriter(PrintStream out) {
		this.out = out;
	}

	public void writeMethod(String method) {
		out.println(method.split("\n")[0]);
	}

	public void writeHeader(Set<String> imports) {

	}

	public void writeFooter() {

	}

	public void flush() {

	}

	public void close() {

	}

	public void beginClassDefinition() {

	}

}

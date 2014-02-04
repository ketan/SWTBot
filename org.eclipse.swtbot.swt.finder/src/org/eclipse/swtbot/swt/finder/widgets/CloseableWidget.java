/*******************************************************************************
 * Copyright (c) 2011 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Marc Philipp - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

/**
 * SWTBot widgets that can be closed such as shells, editors, or views implement
 * this interface.
 * 
 * @author Marc Philipp, http://github.com/marcphilipp
 */
public interface CloseableWidget {

	void close();

}

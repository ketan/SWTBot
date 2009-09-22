/*******************************************************************************
 * Copyright (c) 2009 Obeo
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mariot Chauvin <mariot.chauvin@obeo.fr> - initial API and implementation
 *******************************************************************************/

package org.eclipse.swtbot.eclipse.gef.finder;

import org.eclipse.swtbot.eclipse.finder.SWTBotEclipseTestCase;

/**
 * This is a wrapper test case to the SWTBotEclipseTestCase that adds a gef bot
 * instead of the standard eclipse bot.
 * @author mchauvin
 */
public class SWTBotGefTestCase extends SWTBotEclipseTestCase {
	protected SWTGefBot	bot	= new SWTGefBot();
}

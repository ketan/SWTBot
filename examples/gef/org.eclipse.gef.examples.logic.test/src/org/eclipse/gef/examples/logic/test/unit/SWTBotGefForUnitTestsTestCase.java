package org.eclipse.gef.examples.logic.test.unit;

import org.eclipse.swtbot.eclipse.finder.SWTBotEclipseTestCase;

/**
 * This is a wrapper test case to the SWTBotEclipseTestCase that adds a gef bot
 * instead of the standard eclipse bot.
 * @author mchauvin
 */
public class SWTBotGefForUnitTestsTestCase extends SWTBotEclipseTestCase {
	protected SWTGefForUnitTestsBot	bot	= new SWTGefForUnitTestsBot();
}
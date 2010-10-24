package org.eclipse.swtbot.swt.finder;

import java.util.List;

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.rules.MethodRule;

/**
 * UIThreadTestRunner runs test class methods annotated with @UIThread on the
 * main/UI thread while the tests are run in a separate test thread.
 * 
 * This runner was written because the UI thread has to be the main thread on
 * SWT/Cocoa. Normally, tests are executed on the main thread. This runner forks
 * separate threads for the tests and runs the @UIThread methods and event loop
 * instead.
 * 
 * @author Ralf Ebert
 */
public class UIThreadTestRunner extends SWTBotJunit4ClassRunner {

	public UIThreadTestRunner(Class<?> klass) throws Exception {
		super(klass);
	}

	@Override
	protected List<MethodRule> rules(Object test) {
		List<MethodRule> rules = super.rules(test);
		rules.add(new RunUIThreadRule(getTestClass().getAnnotatedMethods(UIThread.class)));
		return rules;
	}

}
package org.eclipse.swtbot.swt.finder;

import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class RunUIThreadRule implements MethodRule {

	private final List<FrameworkMethod> uiThreadMethods;
	private final Thread uiThread = Thread.currentThread();
	private Thread testThread;
	private Object testObject;
	private Display display;
	private boolean waitForDisplay = true;
	private Throwable testException;

	public RunUIThreadRule(List<FrameworkMethod> uiThreadMethods) {
		this.uiThreadMethods = uiThreadMethods;
	}

	public Statement apply(final Statement testStatement, FrameworkMethod method, final Object target) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				testObject = target;
				
				// Fork Test Thread
				testThread = new Thread(createTestRunnable(testStatement), "test");
				testThread.start();

				// Run UI thread
				runUiThread();
			}

		};

	}

	private Runnable createTestRunnable(final Statement testStatement) {
		return new Runnable() {
			public void run() {
				runTestThread(testStatement);
			}
		};
	}

	// Test Thread
	
	private void runTestThread(final Statement testStatement) {
		try {
			waitForDisplay();
			waitForEventLoop();
			testStatement.evaluate();
		} catch (Throwable e) {
			testException = e;
		} finally {
			disposeDisplay();
		}
	}

	private void waitForDisplay() {
		while ((display = Display.findDisplay(uiThread)) == null && waitForDisplay) {
			Thread.yield();
		}
		if (display == null) {
			throw new RuntimeException("@UIThread methods need to create a Display!");
		}
	}

	private void waitForEventLoop() {
		display.syncExec(new Runnable() {
			public void run() {
				// no-op, wait for sync
			}
		});
	}

	private void disposeDisplay() {
		if (display != null && !display.isDisposed()) {
			display.syncExec(new Runnable() {
				public void run() {
					display.dispose();
				}
			});
		}
	}

	// UI Thread

	private void runUiThread() throws Throwable {
		try {
			// Run all methods annotated with @UIThread
			for (FrameworkMethod frameworkMethod : uiThreadMethods) {
				frameworkMethod.invokeExplosively(testObject);
			}
		} finally {
			// Get the Display created by the @UIThread methods
			display = Display.getCurrent();

			// If the UI thread never created a Display, we need to tell the
			// test thread to stop looking for it.
			if (display == null) {
				waitForDisplay = false;
			}

			// Running the event loop in the @UIThread method is optional. If
			// the test doesn't run it, we do. It can also happen that the
			// @UIThread method finishes with an undisposed display. In this
			// case we also need to run the event loop to dispose the
			// Display properly.
			runEventLoop();
		}

		// Wait for the test thread to finish
		testThread.join();

		// Rethrow any test exception that could not be thrown directly
		if (testException != null) {
			throw testException;
		}

	}

	/**
	 * Runs the event loop very carefully to make sure the Display can be
	 * disposed in every case. This can never throw an Exception, if an
	 * exception bubbles up to the Event thread, it's only stored.
	 */
	private void runEventLoop() {
		while (display != null && !display.isDisposed()) {
			try {
				// Rescue Measure: if the test thread dies and leaves the
				// Display behind
				if (!testThread.isAlive()) {
					display.dispose();
				}
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			} catch (Exception e) {
				// An Exception that occurs from the event loop is recorded.
				// It's not allowed to disrupt the event loop, because an event
				// loop needs to be present to properly dispose the Display.
				testException = e;
			}
		}
	}
}
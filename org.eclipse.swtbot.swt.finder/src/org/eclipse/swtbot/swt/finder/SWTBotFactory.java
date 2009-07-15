/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Vincent Mahe - http://swtbot.org/bugzilla/show_bug.cgi?id=123
 *     Ketan Padegaonkar - http://swtbot.org/bugzilla/show_bug.cgi?id=136
 *     Hans Schwaebli - http://swtbot.org/bugzilla/show_bug.cgi?id=135
 *     Ketan Patel - https://bugs.eclipse.org/bugs/show_bug.cgi?id=259837
 *     Toby Weston - (Bug 259860)
 *     Ketan Patel - (Bug 259860)
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder;

import static org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable.syncExec;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withId;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withTooltip;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.waitForMenu;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.waitForShell;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.waitForWidget;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.Finder;
import org.eclipse.swtbot.swt.finder.matchers.WithItem;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.waits.WaitForObjectCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTrayItem;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.hamcrest.Matcher;

/**
 * Instances of this class provide a convenience API to access widgets.
 * <p>
 * Note: The SWTEclipeBot should be used if testing an eclipse based product/plug-in.
 * </p>
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @author Vincent Mahe &lt;vmahe [at] irisa [dot] fr&gt;
 * @version $Id$
 * @since 2.0
 */
abstract class SWTBotFactory {

	/** the delay between successive polling while waiting for a condition to be true. Use SWTBotPreferences#DEFAULT_POLL_DELAY */
	@Deprecated
	public static final long	DEFAULT_POLL_DELAY	= SWTBotPreferences.DEFAULT_POLL_DELAY;
	/** The display on which the bot operates on. */
	protected final Display		display;
	/** The finder used by the bot to find controls. */
	protected final Finder		finder;

	/**
	 * Constructs a bot with the given finder.
	 *
	 * @param finder the finder.
	 */
	public SWTBotFactory(Finder finder) {
		display = SWTUtils.display();
		this.finder = finder;
	}

	/**
	 * @return a wrapper around a @{link Shell} with the specified text.
	 * @param text the text on the shell.
	 */
	public SWTBotShell shell(String text) {
		return shell(text, 0);
	}

	/**
	 * @return a wrapper around a {@link Shell} with the specified index.
	 * @param text the text on the shell.
	 * @param index the index of the shell, in case there are multiple shells with the same text.
	 */
	public SWTBotShell shell(String text, int index) {
		return new SWTBotShell(shells(text).get(index));
	}

	/**
	 * @return a wrapper around a {@link Shell} with the specified index.
	 * @param text the text on the shell.
	 */
	public List<Shell> shells(String text) {
		Matcher<Shell> withText = withText(text);
		WaitForObjectCondition<Shell> waitForShell = waitForShell(withText);
		waitUntilWidgetAppears(waitForShell);
		List<Shell> allShells = waitForShell.getAllMatches();
		return allShells;
	}

	/**
	 * @return a wrapper around a @{link Shell} with the specified text.
	 * @param text the text on the shell.
	 * @param parent the parent under which a shell will be found.
	 */
	public SWTBotShell shell(String text, Shell parent) {
		return shell(text, parent, 0);
	}

	/**
	 * @return a wrapper around a {@link Shell} with the specified index.
	 * @param text the text on the shell.
	 * @param parent the parent under which a shell will be found.
	 * @param index the index of the shell, in case there are multiple shells with the same text.
	 */
	public SWTBotShell shell(String text, Shell parent, int index) {
		return new SWTBotShell(shells(text, parent).get(index));
	}

	/**
	 * @return a List of {@link Shell} which matched.
	 * @param text the text on the shell.
	 * @param parent the parent under which a shell will be found.
	 */
	public List<Shell> shells(String text, Shell parent) {
		Matcher<Shell> withText = withText(text);
		WaitForObjectCondition<Shell> waitForShell = waitForShell(withText, parent);
		waitUntilWidgetAppears(waitForShell);
		List<Shell> allShells = waitForShell.getAllMatches();
		return allShells;
	}

	/**
	 * @param value the value of the id
	 * @return a wrapper around a @{link Shell} with the specified value pair for its id.
	 * @since 2.0
	 */
	public SWTBotShell shellWithId(String value) {
		return shellWithId(value, 0);
	}

	/**
	 * @param key the key of the id
	 * @param value the value of the id
	 * @return a wrapper around a @{link Shell} with the specified key/value pair for its id.
	 */
	public SWTBotShell shellWithId(String key, String value) {
		return shellWithId(key, value, 0);
	}

	/**
	 * @param value the value of the id
	 * @param index the index of the shell, in case there are multiple shells with the same text.
	 * @return a wrapper around a @{link Shell} with the specified value for its id.
	 * @since 2.0
	 */
	public SWTBotShell shellWithId(String value, int index) {
		Matcher<Shell> withId = withId(value);
		WaitForObjectCondition<Shell> waitForShell = waitForShell(withId);
		waitUntilWidgetAppears(waitForShell);
		return new SWTBotShell(waitForShell.get(index));
	}

	/**
	 * @param key the key of the id
	 * @param value the value of the id
	 * @param index the index of the shell, in case there are multiple shells with the same text.
	 * @return a wrapper around a @{link Shell} with the specified key/value pair for its id.
	 */
	public SWTBotShell shellWithId(String key, String value, int index) {
		Matcher<Shell> withId = withId(key, value);
		WaitForObjectCondition<Shell> waitForShell = waitForShell(withId);
		waitUntilWidgetAppears(waitForShell);
		return new SWTBotShell(waitForShell.get(index));
	}

	/**
	 * @param text the text on the menu.
	 * @return a menu item that matches the specified text.
	 */
	public SWTBotMenu menu(String text) {
		return menu(text, 0);
	}

	/**
	 * @param text the text on the menu.
	 * @param index the index of the menu, in case there are multiple menus with the same text.
	 * @return a menu item that matches the specified text.
	 */
	public SWTBotMenu menu(String text, int index) {
		Matcher<MenuItem> withMnemonic = withMnemonic(text);
		return menu(activeShell(), withMnemonic, index);
	}

	/**
	 * @param value the value of the id.
	 * @return a wrapper around a @{link Menu} with the specified key/value pair for its id.
	 */
	public SWTBotMenu menuWithId(String value) {
		return menuWithId(value, 0);
	}

	/**
	 * @param value the value of the id.
	 * @param index the index of the menu item, in case there are multiple shells with the same text.
	 * @return a wrapper around a @{link Menu} with the specified key/value pair for its id.
	 */
	public SWTBotMenu menuWithId(String value, int index) {
		Matcher<MenuItem> withId = withId(value);
		return menu(activeShell(), withId, index);
	}

	/**
	 * @param key the key of the id.
	 * @param value the value of the id.
	 * @return a wrapper around a @{link Menu} with the specified key/value pair for its id.
	 */
	public SWTBotMenu menuWithId(String key, String value) {
		return menuWithId(key, value, 0);
	}

	/**
	 * @param key the key of the id.
	 * @param value the value of the id.
	 * @param index the index of the menu item, in case there are multiple shells with the same text.
	 * @return a wrapper around a @{link Menu} with the specified key/value pair for its id.
	 */
	public SWTBotMenu menuWithId(String key, String value, int index) {
		Matcher<MenuItem> withId = withId(key, value);
		return menu(activeShell(), withId, index);
	}

	/**
	 * @param shell the shell to search for the menu.
	 * @param matcher the matcher used to find the menu.
	 * @param index the index of the menu, in case there are multiple menus with the same text.
	 * @return a menu item that matches the specified text.
	 */
	public SWTBotMenu menu(SWTBotShell shell, Matcher<MenuItem> matcher, int index) {
		WaitForObjectCondition<MenuItem> waitForMenu = waitForMenu(shell, matcher);
		waitUntilWidgetAppears(waitForMenu);
		return new SWTBotMenu(waitForMenu.get(index), matcher);
	}

	/**
	 * @param matcher the matcher used to match widgets.
	 * @param parentWidget the parent widget to search for a given widget.
	 * @return a list of widgets that match the matcher.
	 */
	public <T extends Widget> List<? extends T> widgets(Matcher<T> matcher, Widget parentWidget) {
		WaitForObjectCondition<T> waitForWidget = waitForWidget(matcher, parentWidget);
		waitUntilWidgetAppears(waitForWidget);
		return waitForWidget.getAllMatches();
	}

	/**
	 * @param matcher the matcher used to match widgets.
	 * @return a list of widgets in the active shell that match the matcher.
	 */
	public <T extends Widget> List<? extends T> widgets(Matcher<T> matcher) {
		return widgets(matcher, activeShell().widget);
	}

	/**
	 * @param matcher the matcher used to match widgets.
	 * @param parentWidget the parent widget to search for a given widget.
	 * @return the first widget that matchs the matcher.
	 */
	public <T extends Widget> T widget(Matcher<T> matcher, Widget parentWidget) {
		return widget(matcher, parentWidget, 0);
	}

	/**
	 * @param matcher the matcher used to match widgets.
	 * @param parentWidget the parent widget to search for a given widget.
	 * @param index the index of the widget, incase the matcher finds multiple widgets
	 * @return the first widget that matchs the matcher.
	 */
	public <T extends Widget> T widget(Matcher<T> matcher, Widget parentWidget, int index) {
		WaitForObjectCondition<T> waitForWidget = waitForWidget(matcher, parentWidget);
		waitUntilWidgetAppears(waitForWidget);
		return waitForWidget.get(index);
	}

	/**
	 * @param matcher the matcher used to match widgets.
	 * @param index the index of the widget in case there are multiple widgets.
	 * @return the index'th widget matching the matcher.
	 */
	public <T extends Widget> T widget(Matcher<T> matcher, int index) {
		WaitForObjectCondition<T> waitForWidget = waitForWidget(matcher);
		waitUntilWidgetAppears(waitForWidget);
		return waitForWidget.get(index);
	}

	/**
	 * @param matcher the matcher used to match widgets.
	 * @return the index'th widget matching the matcher.
	 */
	public <T extends Widget> T widget(Matcher<T> matcher) {
		return widget(matcher, 0);
	}

	/**
	 * Gets the list of shells found in the display.
	 *
	 * @return all the shells in the display.
	 */
	public SWTBotShell[] shells() {
		Shell[] shells = finder.getShells();
		ArrayList<SWTBotShell> result = new ArrayList<SWTBotShell>();
		for (Shell shell : shells) {
			result.add(new SWTBotShell(shell));
		}
		return result.toArray(new SWTBotShell[] {});
	}

	/**
	 * Gets the current active shell.
	 *
	 * @return the current active shell
	 * @throws WidgetNotFoundException if the widget is not found.
	 */
	public SWTBotShell activeShell() throws WidgetNotFoundException {
		return new SWTBotShell(getFinder().activeShell());
	}

	public void waitUntilWidgetAppears(ICondition waitForWidget) {
		try {
			waitUntil(waitForWidget);
		} catch (TimeoutException e) {
			throw new WidgetNotFoundException("Could not find widget.", e); //$NON-NLS-1$
		}
	}

	/**
	 * Waits until a specified condition evaluates to true.
	 *
	 * @param condition the {@link ICondition} to be evaluated.
	 * @throws TimeoutException if the condition does not evaluate to true after {@link SWTBotPreferences#TIMEOUT} milliseconds.
	 * @since 1.2
	 */
	public void waitUntil(ICondition condition) throws TimeoutException {
		waitUntil(condition, SWTBotPreferences.TIMEOUT);
	}

	/**
	 * Waits until the timeout is reached or the condition is met.
	 *
	 * @param condition the condition to be evaluated.
	 * @param timeout the timeout.
	 * @throws TimeoutException if the condition does not evaluate to true after timeout milliseconds.
	 * @since 1.2
	 */
	public void waitUntil(ICondition condition, long timeout) throws TimeoutException {
		waitUntil(condition, timeout, SWTBotPreferences.DEFAULT_POLL_DELAY);
	}

	/**
	 * Waits until the condition has been meet, or the timeout is reached. The interval is the delay between evaluating
	 * the condition after it has failed.
	 *
	 * @param condition the condition to be evaluated.
	 * @param timeout the timeout.
	 * @param interval The delay time.
	 * @throws TimeoutException if the condition does not evaluate to true after timeout milliseconds.
	 */
	public void waitUntil(ICondition condition, long timeout, long interval) throws TimeoutException {
		Assert.isTrue(interval >= 0, "interval value is negative"); //$NON-NLS-1$
		Assert.isTrue(timeout >= 0, "timeout value is negative"); //$NON-NLS-1$
		long limit = System.currentTimeMillis() + timeout;
		condition.init((SWTBot) this);
		while (true) {
			try {
				if (condition.test())
					return;
			} catch (Throwable e) {
				// do nothing
			}
			sleep(interval);
			if (System.currentTimeMillis() > limit)
				throw new TimeoutException("Timeout after: " + timeout + " ms.: " + condition.getFailureMessage()); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Waits while the condition is true.
	 *
	 * @param condition the {@link ICondition} to be evaluated.
	 * @throws TimeoutException if the condition does not evaluate to false after {@link SWTBotPreferences#TIMEOUT} milliseconds.
	 * @since 2.0
	 */
	public void waitWhile(ICondition condition) throws TimeoutException {
		waitWhile(condition, SWTBotPreferences.TIMEOUT);
	}

	/**
	 * Waits while the condition is true, until the timeout is reached.
	 *
	 * @param condition the condition to be evaluated.
	 * @param timeout the timeout.
	 * @throws TimeoutException if the condition does not evaluate to false after timeout milliseconds.
	 * @since 2.0
	 */
	public void waitWhile(ICondition condition, long timeout) throws TimeoutException {
		waitWhile(condition, timeout, SWTBotPreferences.DEFAULT_POLL_DELAY);
	}

	/**
	 * Waits while the condition is true, until the timeout is reached. The interval is the delay between evaluating the
	 * condition after it has succeed.
	 *
	 * @param condition the condition to be evaluated.
	 * @param timeout the timeout.
	 * @param interval The delay time.
	 * @throws TimeoutException if the condition does not evaluate to false after timeout milliseconds.
	 * @since 2.0
	 */
	public void waitWhile(ICondition condition, long timeout, long interval) throws TimeoutException {
		Assert.isTrue(interval >= 0, "interval value is negative"); //$NON-NLS-1$
		Assert.isTrue(timeout >= 0, "timeout value is negative"); //$NON-NLS-1$
		long limit = System.currentTimeMillis() + timeout;
		condition.init((SWTBot) this);
		while (true) {
			try {
				if (!condition.test())
					return;
			} catch (Throwable e) {
				// do nothing
			}
			sleep(interval);
			if (System.currentTimeMillis() > limit)
				throw new TimeoutException("Timeout after: " + timeout + " ms.: " + condition.getFailureMessage()); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Sleeps for the given number of milliseconds.
	 *
	 * @param millis the time in milliseconds for which to sleep.
	 */
	public void sleep(long millis) {
		SWTUtils.sleep(millis);
	}

	/**
	 * Gets the display
	 *
	 * @return the display
	 * @since 1.0
	 */
	public Display getDisplay() {
		return display;
	}

	/**
	 * Captures a screenshot to the given file name.
	 *
	 * @see SWTUtils#captureScreenshot(String)
	 * @param fileName the filename to save screenshot to.
	 * @return <code>true</code> if the screenshot was created and saved, <code>false</code> otherwise.
	 * @since 1.1
	 */
	public boolean captureScreenshot(String fileName) {
		return SWTUtils.captureScreenshot(fileName);
	}

	/**
	 * @return the finder
	 */
	public Finder getFinder() {
		return finder;
	}

	/**
	 * Returns the control which currently has keyboard focus, or <code>null</code> if keyboard events are not currently
	 * going to any of the controls built by the currently running application.
	 *
	 * @return the control which currently has focus, or <code>null</code>
	 * @see Display#getFocusControl()
	 */
	public Control getFocusedWidget() {
		return syncExec(new WidgetResult<Control>() {
			public Control run() {
				return display.getFocusControl();
			}
		});
	}

	/**
	 * @return the first {@link SWTBotTrayItem}
	 */
	public SWTBotTrayItem trayItem() {
		return trayItem(0);
	}

	/**
	 * @param index he index of the tray item.
	 * @return a {@link SWTBotTrayItem} at specified <code>index</code>
	 */
	public SWTBotTrayItem trayItem(int index) {
		return trayItems().get(index);
	}

	/**
	 * @param tooltip the tooltip on the tray item.
	 * @return the first {@link SWTBotTrayItem} with the specified <code>tooltip</code>.
	 */
	public SWTBotTrayItem trayItemWithTooltip(String tooltip) {
		return trayItemWithTooltip(tooltip, 0);
	}

	/**
	 * @param tooltip the tooltip on the tray item.
	 * @param index the index of the tray item.
	 * @return a {@link SWTBotTrayItem} with the specified <code>tooltip</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTrayItem trayItemWithTooltip(String tooltip, int index) {
		java.util.List<SWTBotTrayItem> items = trayItems(allOf(widgetOfType(TrayItem.class), withTooltip(tooltip)));
		if (items.isEmpty() || items.size() <= index)
			throw new WidgetNotFoundException("Widget does not contain an item at index " + index);
		return items.get(index);
	}

	/**
	 * @return List of all tray items in the system tray.
	 */
	public java.util.List<SWTBotTrayItem> trayItems() {
		return trayItems(widgetOfType(TrayItem.class));
	}

	/**
	 * @param matcher the matcher used to match tray item
	 * @return List of {@link SWTBotTrayItem} matching the matcher.
	 */
	public List<SWTBotTrayItem> trayItems(Matcher<?> matcher) {
		WithItem<TrayItem> itemMatcher = WithItem.withItem(matcher);
		WaitForObjectCondition<?> waitForWidget = waitForWidget(itemMatcher, systemTray());
		waitUntilWidgetAppears(waitForWidget);
		List<SWTBotTrayItem> items = new ArrayList<SWTBotTrayItem>();
		for (TrayItem item : itemMatcher.getAllMatches())
			items.add(new SWTBotTrayItem(item));
		return items;
	}

	/**
	 * @return The single instance of the system tray
	 */
	protected Tray systemTray() {
		Tray tray = syncExec(SWTUtils.display(), new WidgetResult<Tray>() {
			public Tray run() {
				return SWTUtils.display().getSystemTray();
			}
		});
		if (tray == null) {
			throw new WidgetNotFoundException("no system tray found");
		}
		return tray;
	}
}

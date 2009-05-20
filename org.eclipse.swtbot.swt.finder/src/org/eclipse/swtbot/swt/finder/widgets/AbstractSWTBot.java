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
package org.eclipse.swtbot.swt.finder.widgets;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.ContextMenuFinder;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.keyboard.Keyboard;
import org.eclipse.swtbot.swt.finder.keyboard.KeyboardFactory;
import org.eclipse.swtbot.swt.finder.results.ArrayResult;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.results.IntResult;
import org.eclipse.swtbot.swt.finder.results.ListResult;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.StringResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTBotEvents;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.utils.Traverse;
import org.eclipse.swtbot.swt.finder.utils.WidgetTextDescription;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

/**
 * Helper to find SWT {@link Widget}s and perform operations on them.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @author Joshua Gosse &lt;jlgosse [at] ca [dot] ibm [dot] com&gt;
 * @version $Id$
 */
public abstract class AbstractSWTBot<T extends Widget> {

	/** The logger. */
	protected final Logger			log;
	/** With great power comes great responsibility, use carefully. */
	public final T					widget;
	/** With great power comes great responsibility, use carefully. */
	public final Display			display;
	/** The description of the widget. */
	protected final SelfDescribing	description;
	/** The keyboard to use to type on the widget. */
	private Keyboard				keyboard;

	/**
	 * Constructs a new instance with the given widget.
	 * 
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public AbstractSWTBot(T w) throws WidgetNotFoundException {
		this(w, new WidgetTextDescription(w));
	}

	/**
	 * Constructs a new instance with the given widget.
	 * 
	 * @param w the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public AbstractSWTBot(T w, SelfDescribing description) throws WidgetNotFoundException {
		if (w == null)
			throw new WidgetNotFoundException("The widget was null."); //$NON-NLS-1$

		this.widget = w;
		if (description == null)
			this.description = new WidgetTextDescription(w);
		else
			this.description = description;

		if (w.isDisposed())
			throw new WidgetNotFoundException("The widget {" + description + "} was disposed." + SWTUtils.toString(w)); //$NON-NLS-1$ //$NON-NLS-2$

		display = w.getDisplay();
		log = Logger.getLogger(getClass());
	}

	/**
	 * Sends a non-blocking notification of the specified type to the widget.
	 * 
	 * @param eventType the event type.
	 * @see Widget#notifyListeners(int, Event)
	 */
	protected void notify(final int eventType) {
		notify(eventType, createEvent());
	}

	/**
	 * Sends a non-blocking notification of the specified type to the {@link #widget}.
	 * 
	 * @param eventType the type of event.
	 * @param createEvent the event to be sent to the {@link #widget}.
	 */
	protected void notify(final int eventType, final Event createEvent) {
		notify(eventType, createEvent, widget);
	}

	/**
	 * Sends a non-blocking notification of the specified type to the widget.
	 * 
	 * @param eventType the type of event.
	 * @param createEvent the event to be sent to the {@link #widget}.
	 * @param widget the widget to send the event to.
	 */
	protected void notify(final int eventType, final Event createEvent, final Widget widget) {
		createEvent.type = eventType;
		final Object[] result = syncExec(new ArrayResult<Object>() {
			public Object[] run() {
				return new Object[] { SWTBotEvents.toString(createEvent), AbstractSWTBot.this.toString() };
			}
		});

		log.trace(MessageFormat.format("Enquing event {0} on {1}", result)); //$NON-NLS-1$
		asyncExec(new VoidResult() {
			public void run() {
				if ((widget == null) || widget.isDisposed()) {
					log.trace(MessageFormat.format("Not notifying {0} is null or has been disposed", AbstractSWTBot.this)); //$NON-NLS-1$
					return;
				}
				if (!isEnabledInternal()) {
					log.warn(MessageFormat.format("Widget is not enabled: {0}", AbstractSWTBot.this)); //$NON-NLS-1$
					return;
				}
				log.trace(MessageFormat.format("Sending event {0} to {1}", result)); //$NON-NLS-1$
				widget.notifyListeners(eventType, createEvent);
				log.debug(MessageFormat.format("Sent event {0} to {1}", result)); //$NON-NLS-1$
			}
		});

		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				// do nothing, just wait for sync.
			}
		});

		long playbackDelay = SWTBotPreferences.PLAYBACK_DELAY;
		if (playbackDelay > 0)
			sleep(playbackDelay);
	}

	/**
	 * Sleeps for millis milliseconds. Delegate to {@link SWTUtils#sleep(long)}
	 * 
	 * @param millis the time in milli seconds
	 */
	protected static void sleep(long millis) {
		SWTUtils.sleep(millis);
	}

	/**
	 * Creates an event.
	 * 
	 * @return an event that encapsulates {@link #widget} and {@link #display}. Subclasses may override to set other
	 *         event properties.
	 */
	protected Event createEvent() {
		Event event = new Event();
		event.time = (int) System.currentTimeMillis();
		event.widget = widget;
		event.display = display;
		return event;
	}

	/**
	 * Create a mouse event
	 * 
	 * @param x the x co-ordinate of the mouse event.
	 * @param y the y co-ordinate of the mouse event.
	 * @param button the mouse button that was clicked.
	 * @param stateMask the state of the keyboard modifier keys.
	 * @param count the number of times the mouse was clicked.
	 * @return an event that encapsulates {@link #widget} and {@link #display}
	 * @since 1.2
	 */
	protected Event createMouseEvent(int x, int y, int button, int stateMask, int count) {
		Event event = new Event();
		event.time = (int) System.currentTimeMillis();
		event.widget = widget;
		event.display = display;
		event.x = x;
		event.y = y;
		event.button = button;
		event.stateMask = stateMask;
		event.count = count;
		return event;
	}

	/**
	 * Create a key event
	 * 
	 * @param keyCode the key code of the key pressed
	 * @param character the character representation of the key
	 * @return an event that encapsulates {@link #widget} and {@link #display}
	 */
	private Event createKeyEvent(int keyCode, char character) {
		Event event = createEvent();
		event.keyCode = keyCode;
		event.character = character;
		return event;
	}

	/**
	 * Click on the table at given coordinates
	 * 
	 * @param x the x co-ordinate of the click
	 * @param y the y co-ordinate of the click
	 * @since 2.0
	 */
	protected void clickXY(int x, int y) {
		log.debug(MessageFormat.format("Clicking on {0}", this)); //$NON-NLS-1$
		notify(SWT.MouseEnter);
		notify(SWT.MouseMove);
		notify(SWT.Activate);
		notify(SWT.FocusIn);
		notify(SWT.MouseDown, createMouseEvent(x, y, 1, SWT.BUTTON1, 1));
		notify(SWT.MouseUp);
		notify(SWT.Selection);
		notify(SWT.MouseHover);
		notify(SWT.MouseMove);
		notify(SWT.MouseExit);
		notify(SWT.Deactivate);
		notify(SWT.FocusOut);
		log.debug(MessageFormat.format("Clicked on {0}", this)); //$NON-NLS-1$
	}

	/**
	 * Right click on the widget at given coordinates
	 * 
	 * @param x the x co-ordinate of the click
	 * @param y the y co-ordinate of the click
	 * @since 2.0
	 */
	private void rightClickXY(int x, int y) {
		log.debug(MessageFormat.format("Right clicking on {0}", this)); //$NON-NLS-1$
		notify(SWT.MouseEnter);
		notify(SWT.MouseMove);
		notify(SWT.Activate);
		notify(SWT.FocusIn);
		notify(SWT.MouseDown, createMouseEvent(x, y, 1, SWT.BUTTON3, 1));
		notify(SWT.MouseUp);
		notify(SWT.Selection);
		notify(SWT.MouseHover);
		notify(SWT.MouseMove);
		notify(SWT.MouseExit);
		notify(SWT.Deactivate);
		notify(SWT.FocusOut);
		log.debug(MessageFormat.format("Right clicked on {0}", this)); //$NON-NLS-1$
	}

	/**
	 * Double-click on the table at given coordinates
	 * 
	 * @param x the x co-ordinate of the click
	 * @param y the y co-ordinate of the click
	 * @since 2.0
	 */
	protected void doubleClickXY(int x, int y) {
		log.debug(MessageFormat.format("Double-clicking on {0}", widget)); //$NON-NLS-1$
		notify(SWT.MouseEnter);
		notify(SWT.MouseMove);
		notify(SWT.Activate);
		notify(SWT.FocusIn);
		notify(SWT.MouseDown, createMouseEvent(x, y, 1, SWT.BUTTON1, 1));
		notify(SWT.MouseUp);
		notify(SWT.Selection);
		notify(SWT.MouseDoubleClick, createMouseEvent(x, y, 1, SWT.BUTTON1, 2));
		notify(SWT.MouseHover);
		notify(SWT.MouseMove);
		notify(SWT.MouseExit);
		notify(SWT.Deactivate);
		notify(SWT.FocusOut);
		log.debug(MessageFormat.format("Double-clicked on {0}", widget)); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return StringDescription.toString(description);
	}

	// /**
	// * Finds a menu matching the current {@link Matcher}.
	// *
	// * @param matcher the matcher used to find menus.
	// * @return all menus that match the matcher.
	// */
	// protected List findMenus(Matcher<?> matcher) {
	// return finder.findMenus(matcher);
	// }

	// /**
	// * Finds the menu on the main menu bar matching the given information.
	// *
	// * @param menuName the name of the menu.
	// * @param matcher the matcher used to find the menu.
	// * @return the first menuItem that matches the matcher
	// * @throws WidgetNotFoundException if the widget is not found.
	// */
	// protected Widget findMenu(Matcher<?> matcher, String menuName) throws WidgetNotFoundException {
	// return findMenu(getMenuMatcher(menuName), 0);
	// }

	// /**
	// * Gets the menu matcher for the given name.
	// *
	// * @param menuName the name of the menuitem that the matcher must match.
	// * @return {@link WidgetMatcherFactory#menuMatcher(String)}
	// */
	// protected Matcher getMenuMatcher(String menuName) {
	// return WidgetMatcherFactory.menuMatcher(menuName);
	// }

	// /**
	// * Finds the menu on the main menu bar matching the given information.
	// *
	// * @param matcher the matcher used to find the menu.
	// * @param index the index in the list of the menu items that match the matcher.
	// * @return the index(th) menuItem that matches the matcher
	// * @throws WidgetNotFoundException if the widget is not found.
	// */
	// protected Widget findMenu(Matcher<?> matcher, int index) throws WidgetNotFoundException {
	// List findMenus = findMenus(matcher);
	// if (!findMenus.isEmpty())
	// return (MenuItem) findMenus.get(index);
	// throw new WidgetNotFoundException("Could not find menu using matcher " + matcher);
	// }

	/**
	 * Gets the text of this object's widget.
	 * 
	 * @return the text on the widget.
	 */
	public String getText() {
		return SWTUtils.getText(widget);
	}

	/**
	 * Gets the tooltip of this object's widget.
	 * 
	 * @return the tooltip on the widget.
	 * @since 1.0
	 */
	public String getToolTipText() {
		return syncExec(new StringResult() {
			public String run() {
				return SWTUtils.getToolTipText(widget);
			}
		});
	}

	/**
	 * Check if this widget has a style attribute.
	 * 
	 * @param w the widget.
	 * @param style the style bits, one of the constants in {@link SWT}.
	 * @return <code>true</code> if style is set on the widget.
	 */
	protected boolean hasStyle(Widget w, int style) {
		return SWTUtils.hasStyle(w, style);
	}

	/**
	 * Gets the context menu matching the text.
	 * 
	 * @param text the text on the context menu.
	 * @return the menu that has the given text.
	 * @throws WidgetNotFoundException if the widget is not found.
	 */
	public SWTBotMenu contextMenu(final String text) throws WidgetNotFoundException {
		if (widget instanceof Control) {
			return contextMenu((Control) widget, text);
		}
		throw new WidgetNotFoundException("Could not find menu: " + text); //$NON-NLS-1$
	}

	/**
	 * Gets the context menu on the given control, matching the text.
	 * 
	 * @param control the control
	 * @param text the text on the context menu.
	 * @return the menu that has the given text.
	 * @throws WidgetNotFoundException if the widget is not found.
	 * @since 2.0
	 */
	protected SWTBotMenu contextMenu(final Control control, final String text) {
		final Matcher<?> matcher = allOf(instanceOf(MenuItem.class), withMnemonic(text));
		final ContextMenuFinder menuFinder = new ContextMenuFinder(control);

		new SWTBot().waitUntil(new DefaultCondition() {
			public String getFailureMessage() {
				return "Could not find context menu with text: " + text; //$NON-NLS-1$
			}

			public boolean test() throws Exception {
				return !menuFinder.findMenus(matcher).isEmpty();
			}
		});
		return new SWTBotMenu(menuFinder.findMenus(matcher).get(0), matcher);
	}

	/**
	 * Gets if the object's widget is enabled.
	 * 
	 * @return <code>true</code> if the widget is enabled.
	 * @see Control#isEnabled()
	 */
	public boolean isEnabled() {
		if (widget instanceof Control)
			return syncExec(new BoolResult() {
				public Boolean run() {
					return isEnabledInternal();
				}
			});
		return false;
	}

	/**
	 * Gets if the widget is enabled.
	 * <p>
	 * This method is not thread safe, and must be called from the UI thread.
	 * </p>
	 * 
	 * @return <code>true</code> if the widget is enabled.
	 * @since 1.0
	 */
	protected boolean isEnabledInternal() {
		try {
			return ((Boolean) SWTUtils.invokeMethod(widget, "isEnabled")).booleanValue(); //$NON-NLS-1$
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * Invokes {@link ArrayResult#run()} on the UI thread.
	 * 
	 * @param toExecute the object to be invoked in the UI thread.
	 * @return the array returned by toExecute.
	 */
	protected <T> T[] syncExec(ArrayResult<T> toExecute) {
		return UIThreadRunnable.syncExec(display, toExecute);
	}

	/**
	 * Invokes {@link VoidResult#run()} on the UI thread.
	 * 
	 * @param toExecute the object to be invoked in the UI thread.
	 */
	protected void syncExec(VoidResult toExecute) {
		UIThreadRunnable.syncExec(display, toExecute);
	}

	/**
	 * Invokes {@link ListResult#run()} on the UI thread.
	 * 
	 * @param toExecute the object to be invoked in the UI thread.
	 * @return the list returned by toExecute
	 */
	protected <E> List<E> syncExec(ListResult<E> toExecute) {
		return UIThreadRunnable.syncExec(display, toExecute);
	}

	/**
	 * Invokes {@link BoolResult#run()} synchronously on the UI thread.
	 * 
	 * @param toExecute the object to be invoked in the UI thread.
	 * @return the boolean returned by toExecute
	 */
	protected boolean syncExec(BoolResult toExecute) {
		return UIThreadRunnable.syncExec(display, toExecute);
	}

	/**
	 * Invokes {@link BoolResult#run()} synchronously on the UI thread.
	 * 
	 * @param toExecute the object to be invoked in the UI thread.
	 * @return the boolean returned by toExecute
	 */

	protected String syncExec(StringResult toExecute) {
		return UIThreadRunnable.syncExec(display, toExecute);
	}

	/**
	 * Invokes {@link Result#run()} synchronously on the UI thread.
	 * 
	 * @param toExecute the object to be invoked in the UI thread.
	 * @return the boolean returned by toExecute
	 */
	protected <T> T syncExec(Result<T> toExecute) {
		return UIThreadRunnable.syncExec(display, toExecute);
	}

	/**
	 * Invokes {@link WidgetResult#run()} synchronously on the UI thread.
	 * 
	 * @param toExecute the object to be invoked in the UI thread.
	 * @return the Widget returned by toExecute
	 */
	protected T syncExec(WidgetResult<T> toExecute) {
		return UIThreadRunnable.syncExec(display, toExecute);
	}

	/**
	 * Invokes {@link IntResult#run()} synchronously on the UI thread.
	 * 
	 * @param toExecute the object to be invoked in the UI thread.
	 * @return the integer returned by toExecute
	 */

	protected int syncExec(IntResult toExecute) {
		return UIThreadRunnable.syncExec(display, toExecute);
	}

	/**
	 * Invokes {@link BoolResult#run()} asynchronously on the UI thread.
	 * 
	 * @param toExecute the object to be invoked in the UI thread.
	 */
	protected void asyncExec(VoidResult toExecute) {
		UIThreadRunnable.asyncExec(display, toExecute);
	}

	/**
	 * Gets the foreground color of the widget.
	 * 
	 * @return the foreground color on the widget, or <code>null</code> if the widget is not an instance of
	 *         {@link Control}.
	 * @since 1.0
	 */
	public Color foregroundColor() {
		return syncExec(new Result<Color>() {
			public Color run() {
				if (widget instanceof Control)
					return ((Control) widget).getForeground();
				return null;
			}
		});
	}

	/**
	 * Gets the background color of the widget.
	 * 
	 * @return the background color on the widget, or <code>null</code> if the widget is not an instance of
	 *         {@link Control}.
	 * @since 1.0
	 */
	public Color backgroundColor() {
		return syncExec(new Result<Color>() {
			public Color run() {
				if (widget instanceof Control)
					return ((Control) widget).getBackground();
				return null;
			}
		});
	}

	/**
	 * Check if the widget is enabled, throws if the widget is disabled.
	 * 
	 * @since 1.3
	 */
	protected void assertEnabled() {
		Assert.isTrue(isEnabled(), MessageFormat.format("Widget {0} is not enabled.", this)); //$NON-NLS-1$ //$NON-NLS-2$

	}

	/**
	 * Checks if the widget is visible.
	 * 
	 * @return <code>true</code> if the widget is visible, <code>false</code> otherwise.
	 * @since 1.0
	 */
	public boolean isVisible() {
		return syncExec(new BoolResult() {
			public Boolean run() {
				if (widget instanceof Control)
					return ((Control) widget).isVisible();
				return true;
			}
		});
	}

	/**
	 * Sets the focus on this control.
	 * 
	 * @since 1.2
	 */
	public void setFocus() {
		assertEnabled();
		log.debug(MessageFormat.format("Attempting to set focus on {0}", this));
		syncExec(new VoidResult() {
			public void run() {
				if (widget instanceof Control) {
					Control control = (Control) widget;
					control.getShell().forceActive();
					control.getShell().forceFocus();
					control.forceFocus();
				}
			}
		});
	}

	/**
	 * @param traverse the kind of traversal to perform.
	 * @return <code>true</code> if the traversal succeeded.
	 * @see Control#traverse(int)
	 */
	public boolean traverse(final Traverse traverse) {
		assertEnabled();
		setFocus();

		if (!(widget instanceof Control))
			throw new UnsupportedOperationException("Can only traverse widgets of type Control. You're traversing a widget of type: " //$NON-NLS-1$
					+ widget.getClass().getName());

		return syncExec(new BoolResult() {
			public Boolean run() {
				return ((Control) widget).traverse(traverse.type);
			}
		});
	}

	/**
	 * @return <code>true</code> if this widget has focus.
	 * @see Display#getFocusControl()
	 */
	public boolean isActive() {
		return syncExec(new BoolResult() {
			public Boolean run() {
				return display.getFocusControl() == widget;
			}
		});
	}

	/**
	 * Clicks on this widget.
	 * 
	 * @return itself.
	 */
	protected AbstractSWTBot<T> click() {
		throw new UnsupportedOperationException("This operation is not supported by this widget.");
	}

	/**
	 * Empty method stub, since it should be overridden by subclass#rightClick
	 * 
	 * @return itself.
	 */
	protected AbstractSWTBot<T> rightClick() {
		throw new UnsupportedOperationException("This operation is not supported by this widget.");
	}

	/**
	 * Perform a click action at the given coordinates
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param post Whether or not {@link Display#post} should be used
	 * @return itself.
	 */
	protected AbstractSWTBot<T> click(final int x, final int y, final boolean post) {
		if (post) {
			asyncExec(new VoidResult() {
				public void run() {
					moveMouse(x, y);
					mouseDown(x, y, 1);
					mouseUp(x, y, 1);
				}
			});
			sleep(500);
		} else
			clickXY(x, y);
		return this;
	}

	/**
	 * Perform a right-click action at the given coordinates
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param post Whether or not {@link Display#post} should be used
	 * @return itself.
	 */
	protected AbstractSWTBot<T> rightClick(final int x, final int y, final boolean post) {
		if (post) {
			syncExec(new VoidResult() {
				public void run() {
					moveMouse(x, y);
					mouseDown(x, y, 3);
					mouseUp(x, y, 3);
				}
			});
		} else
			rightClickXY(x, y);
		return this;
	}

	/**
	 * Post an SWT.MouseMove event
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	void moveMouse(final int x, final int y) {
		asyncExec(new VoidResult() {
			public void run() {
				Event event = createMouseEvent(x, y, 0, 0, 0);
				event.type = SWT.MouseMove;
				display.post(event);
			}
		});
	}

	/**
	 * Post an SWT.MouseDown event
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param button the mouse button to be pressed
	 */
	private void mouseDown(final int x, final int y, final int button) {
		asyncExec(new VoidResult() {
			public void run() {
				Event event = createMouseEvent(x, y, button, 0, 0);
				event.type = SWT.MouseDown;
				display.post(event);
			}
		});
	}

	/**
	 * Post an SWT.MouseUp event.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param button the mouse button to be pressed
	 */
	private void mouseUp(final int x, final int y, final int button) {
		asyncExec(new VoidResult() {
			public void run() {
				Event event = createMouseEvent(x, y, button, 0, 0);
				event.type = SWT.MouseUp;
				display.post(event);
			}
		});
	}

	/**
	 * @return the absolute location of the widget relative to the display.
	 */
	protected Rectangle absoluteLocation() {
		throw new UnsupportedOperationException("This operation is not supported by this widget.");
	}

	/**
	 * @return the keyboard to use to type on this widget.
	 */
	protected Keyboard keyboard() {
		if (keyboard == null)
			keyboard = KeyboardFactory.getDefaultKeyboard(widget, description);
		return keyboard;
	}
}

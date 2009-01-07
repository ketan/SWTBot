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
package org.eclipse.swtbot.swt.recorder;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.recorder.listeners.ActionList;
import org.eclipse.swtbot.swt.recorder.widgets.text.CheckBoxSelectionListener;
import org.eclipse.swtbot.swt.recorder.widgets.text.PushButtonSelectionListener;
import org.eclipse.swtbot.swt.recorder.widgets.text.RadioButtonSelectionListener;
import org.eclipse.swtbot.swt.recorder.widgets.text.ShellEventListener;
import org.eclipse.swtbot.swt.recorder.widgets.text.TabSelectionListener;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotRecorder {

	/**
	 * Maps event types to listeners that listen to the specified event.
	 * 
	 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
	 * @version $Id$
	 */
	public static class ListenerSet {

		private final int		eventType;
		private final Listener	listener;

		/**
		 * @param eventType the event type to listen to.
		 * @param listener the listener that should listen to the event.
		 */
		public ListenerSet(int eventType, Listener listener) {
			this.eventType = eventType;
			this.listener = listener;
		}

		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + eventType;
			result = prime * result + ((listener == null) ? 0 : listener.hashCode());
			return result;
		}

		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ListenerSet other = (ListenerSet) obj;
			if (eventType != other.eventType)
				return false;
			if (listener == null) {
				if (other.listener != null)
					return false;
			} else if (!listener.equals(other.listener))
				return false;
			return true;
		}

	}

	private final Display		display;
	final Set					registeredListeners;

	private final ActionList	eventList;

	private final Listener		shellEventListener;
	// private final Listener treeEventListener;
	// private final Listener keyEventListener;
	private final Listener		pushButtonListener;
	private final Listener		radioButtonListener;
	private final Listener		checkboxListener;
	// private final Listener listSelectionListener;
	// private final Listener comboSelectionListener;
	private final Listener		tabSelectionListener;
	// private final Listener menuSelectionListener;
	private boolean				running	= false;

	/**
	 * Constructs a recorder.
	 * 
	 * @param display the display on which events are recorded.
	 * @param bot a helper bot.
	 */
	public SWTBotRecorder(Display display, SWTBot bot) {
		this.display = display;
		registeredListeners = new HashSet();
		eventList = new ActionList();
		shellEventListener = new ShellEventListener(eventList, bot);
		// treeEventListener = new TreeEventListener(eventList, bot);
		// keyEventListener = new KeyEventListener(eventList, bot);
		pushButtonListener = new PushButtonSelectionListener(eventList, bot);
		radioButtonListener = new RadioButtonSelectionListener(eventList, bot);
		checkboxListener = new CheckBoxSelectionListener(eventList, bot);
		// listSelectionListener = new ListSelectionListener(eventList, bot);
		// comboSelectionListener = new ComboSelectionListener(eventList, bot);
		tabSelectionListener = new TabSelectionListener(eventList, bot);
		// menuSelectionListener = new MenuSelectionListener(eventList, bot);
	}

	/**
	 * Starts recording after clearing previously recorded events.
	 */
	public void start() {
		if (running)
			throw new IllegalStateException("Already recording"); //$NON-NLS-1$
		running = true;
		clear();
		hookListeners();
	}

	/**
	 * @return the list of recorded events.
	 */
	public ActionList stop() {
		if (!running)
			throw new IllegalStateException("Not recording"); //$NON-NLS-1$
		running = false;
		unregisterAllListeners();
		return getEvents();
	}

	/**
	 * @return <code>true</code> if the recorder is running, <code>false</code> otherwise.
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Clear the events recorded so far.
	 */
	public void clear() {
		eventList.clear();
	}

	/**
	 * This uses {@link #registerListener(int, Listener)}, so as to keep track of all registered listeners to
	 * un-register them conveniently when clients call {@link #stop()}
	 */
	protected void hookListeners() {
		registerShellListeners();
		//
		// registerTreeListeners();
		//
		// registerKeyListeners();

		registerPushButtonListeners();
		registerRadioButtonListeners();
		registerCheckboxListeners();

		//
		// registerListSelectionListeners();
		//
		// registerComboSelectionListeners();
		//
		registerTabSelectionListeners();
		//
		// registerMenuListeners();
	}

	// private void registerMenuListeners() {
	// registerListener(SWT.Selection, menuSelectionListener);
	// }
	//
	private void registerTabSelectionListeners() {
		registerListener(SWT.Selection, tabSelectionListener);
	}

	//
	// private void registerComboSelectionListeners() {
	// registerListener(SWT.Verify, comboSelectionListener);
	// }
	//
	// private void registerListSelectionListeners() {
	// registerListener(SWT.DefaultSelection, listSelectionListener);
	// registerListener(SWT.Selection, listSelectionListener);
	// }

	private void registerPushButtonListeners() {
		registerListener(SWT.DefaultSelection, pushButtonListener);
		registerListener(SWT.Selection, pushButtonListener);
	}

	private void registerRadioButtonListeners() {
		registerListener(SWT.DefaultSelection, radioButtonListener);
		registerListener(SWT.Selection, radioButtonListener);
	}

	private void registerCheckboxListeners() {
		registerListener(SWT.DefaultSelection, checkboxListener);
		registerListener(SWT.Selection, checkboxListener);
	}

	//
	// private void registerKeyListeners() {
	// registerListener(SWT.KeyDown, keyEventListener);
	// }
	//
	// private void registerTreeListeners() {
	// registerListener(SWT.Collapse, treeEventListener);
	// registerListener(SWT.Expand, treeEventListener);
	// }
	//
	private void registerShellListeners() {
		registerListener(SWT.Activate, shellEventListener);
		registerListener(SWT.Close, shellEventListener);
	}

	/**
	 * Add the specified listener as the display filter for the specified event type.
	 * <p>
	 * This is a convinience method to add filters, so that clients do not have to remove them explicitly.
	 * </p>
	 * 
	 * @see #unregisterAllListeners()
	 * @param eventType the event type
	 * @param listener the listener
	 */
	protected void registerListener(final int eventType, final Listener listener) {
		UIThreadRunnable.syncExec(display, new VoidResult() {
			public void run() {
				display.addFilter(eventType, listener);
			}
		});
		registeredListeners.add(new ListenerSet(eventType, listener));
	}

	/**
	 * @return the list of events captured so far.
	 */
	public ActionList getEvents() {
		return eventList;
	}

	/**
	 * Unregisters all listeners
	 * 
	 * @see #unregister(int, Listener)
	 */
	protected void unregisterAllListeners() {
		for (Iterator iterator = registeredListeners.iterator(); iterator.hasNext();) {
			ListenerSet set = (ListenerSet) iterator.next();
			unregister(set.eventType, set.listener);
		}
		registeredListeners.clear();
	}

	/**
	 * Removes the specified listener as the display filter for the specified event type.
	 * 
	 * @param eventType the event from which the listener should be unregistered.
	 * @param listener the listener to unregister.
	 */
	protected void unregister(final int eventType, final Listener listener) {
		UIThreadRunnable.syncExec(display, new VoidResult() {
			public void run() {
				display.removeFilter(eventType, listener);
			}
		});
	}
}

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
package org.eclipse.swtbot.swt.recorder.listeners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.swt.recorder.generators.SWTBotAction;


/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class ActionList {

	/** the list of actions. */
	protected List	actions	= new ArrayList();

	/**
	 * @param event the event to be added to the list.
	 */
	public void add(SWTBotAction event) {
		actions.add(event);
		if (shouldPrint())
			System.out.println(event);
	}

	private boolean shouldPrint() {
		return Boolean.getBoolean("org.eclipse.swtbot.recorder.actions.print"); //$NON-NLS-1$
	}

	/**
	 * Clears all events.
	 */
	public void clear() {
		actions.clear();
	}

	/**
	 * @param event removes the specified event from the list.
	 */
	public void remove(SWTBotAction event) {
		actions.remove(event);
	}

	/**
	 * @return the number of events in the list.
	 */
	public int size() {
		return actions.size();
	}

	public String toString() {
		return actions.toString();
	}

	/**
	 * @return <code>true</code> if the number of events is zero.
	 */
	public boolean isEmpty() {
		return actions.isEmpty();
	}

	/**
	 * @return the last action in the list or <code>null</code> if the list is empty.
	 */
	public SWTBotAction lastAction() {
		if (actions.isEmpty())
			return null;
		return (SWTBotAction) actions.get(actions.size() - 1);
	}

	/**
	 * @param index the index of the action to return.
	 * @return the index(th) action in the list.
	 */
	public SWTBotAction get(int index) {
		return (SWTBotAction) actions.get(index);
	}

	/**
	 * Returns a copy of the actions.
	 * 
	 * @return a copy of all the action, modifications to this copy will not affect this instance.
	 */
	public List getActions() {
		return new ArrayList(actions);
	}

}

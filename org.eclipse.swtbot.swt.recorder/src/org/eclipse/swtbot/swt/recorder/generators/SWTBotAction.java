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
package org.eclipse.swtbot.swt.recorder.generators;


import org.eclipse.core.runtime.Assert;
import org.eclipse.swtbot.swt.recorder.listeners.ActionList;

/**
 * Represents an SWTBot action.
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotAction {

	private final SWTBotAccessor	accessor;
	private final SWTBotEvent		event;

	/**
	 * @param accessor the accessor
	 * @param event the event
	 */
	public SWTBotAction(SWTBotAccessor accessor, SWTBotEvent event) {
		Assert.isNotNull(accessor);
		Assert.isNotNull(event);
		this.accessor = accessor;
		this.event = event;
	}

	/**
	 * @return the java representation of this action
	 */
	public String toJava() {
		return accessor.toJava() + "." + event.methodCall() + "(" + event.args() + ")" + ";";
	}

	public String toString() {
		return toJava();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SWTBotAction other = (SWTBotAction) obj;
		return accessor.equals(other.accessor) && event.equals(other.event);
	}

	/**
	 * @param actions the list into which this action should add itself.
	 */
	public void add(ActionList actions) {
		SWTBotAction lastAction = actions.lastAction();
		if (canCollate(lastAction))
			actions.remove(lastAction);
		actions.add(this);
	}

	private boolean canCollate(SWTBotAction lastAction) {
		if (lastAction != null)
			return lastAction.accessor.equals(accessor) && lastAction.event.canCollate(event);
		return false;
	}
}

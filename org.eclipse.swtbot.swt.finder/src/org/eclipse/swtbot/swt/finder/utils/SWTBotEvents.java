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
package org.eclipse.swtbot.swt.finder.utils;

import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ArmEvent;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;

/**
 * This maps SWT events to the Strings.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public abstract class SWTBotEvents {

	private static final BidiMap<String, Integer>	EVENTS	= new BidiMap<String, Integer>();
	static {
		EVENTS.put("Activate", SWT.Activate); //$NON-NLS-1$
		EVENTS.put("Arm", SWT.Arm); //$NON-NLS-1$
		EVENTS.put("Close", SWT.Close); //$NON-NLS-1$
		EVENTS.put("Collapse", SWT.Collapse); //$NON-NLS-1$
		EVENTS.put("Deactivate", SWT.Deactivate); //$NON-NLS-1$
		EVENTS.put("DefaultSelection", SWT.DefaultSelection); //$NON-NLS-1$
		EVENTS.put("Deiconify", SWT.Deiconify); //$NON-NLS-1$
		EVENTS.put("Dispose", SWT.Dispose); //$NON-NLS-1$
		EVENTS.put("DragDetect", SWT.DragDetect); //$NON-NLS-1$
		EVENTS.put("EraseItem", SWT.EraseItem); //$NON-NLS-1$
		EVENTS.put("Expand", SWT.Expand); //$NON-NLS-1$
		EVENTS.put("FocusIn", SWT.FocusIn); //$NON-NLS-1$
		EVENTS.put("FocusOut", SWT.FocusOut); //$NON-NLS-1$
		EVENTS.put("HardKeyDown", SWT.HardKeyDown); //$NON-NLS-1$
		EVENTS.put("HardKeyUp", SWT.HardKeyUp); //$NON-NLS-1$
		EVENTS.put("Help", SWT.Help); //$NON-NLS-1$
		EVENTS.put("Hide", SWT.Hide); //$NON-NLS-1$
		EVENTS.put("Iconify", SWT.Iconify); //$NON-NLS-1$
		EVENTS.put("KeyDown", SWT.KeyDown); //$NON-NLS-1$
		EVENTS.put("KeyUp", SWT.KeyUp); //$NON-NLS-1$
		EVENTS.put("MeasureItem", SWT.MeasureItem); //$NON-NLS-1$
		EVENTS.put("MenuDetect", SWT.MenuDetect); //$NON-NLS-1$
		EVENTS.put("Modify", SWT.Modify); //$NON-NLS-1$
		EVENTS.put("MouseDoubleClick", SWT.MouseDoubleClick); //$NON-NLS-1$
		EVENTS.put("MouseDown", SWT.MouseDown); //$NON-NLS-1$
		EVENTS.put("MouseEnter", SWT.MouseEnter); //$NON-NLS-1$
		EVENTS.put("MouseExit", SWT.MouseExit); //$NON-NLS-1$
		EVENTS.put("MouseHover", SWT.MouseHover); //$NON-NLS-1$
		EVENTS.put("MouseMove", SWT.MouseMove); //$NON-NLS-1$
		EVENTS.put("MouseUp", SWT.MouseUp); //$NON-NLS-1$
		EVENTS.put("MouseWheel", SWT.MouseWheel); //$NON-NLS-1$
		EVENTS.put("Move", SWT.Move); //$NON-NLS-1$
		EVENTS.put("Paint", SWT.Paint); //$NON-NLS-1$
		EVENTS.put("PaintItem", SWT.PaintItem); //$NON-NLS-1$
		EVENTS.put("Resize", SWT.Resize); //$NON-NLS-1$
		EVENTS.put("Selection", SWT.Selection); //$NON-NLS-1$
		EVENTS.put("SetData", SWT.SetData); //$NON-NLS-1$
		EVENTS.put("Settings", SWT.Settings); // note: this event only goes to Display //$NON-NLS-1$
		EVENTS.put("Show", SWT.Show); //$NON-NLS-1$
		EVENTS.put("Traverse", SWT.Traverse); //$NON-NLS-1$
		EVENTS.put("Verify", SWT.Verify); //$NON-NLS-1$
	}

	/**
	 * Converts the event to a string for display.
	 *
	 * @param event the event.
	 * @return the string representation of the display.
	 */
	public static String toString(int event) {
		return EVENTS.getKey(event);
	}

	/**
	 * Lists all the events.
	 *
	 * @return all the events.
	 */
	public static int[] events() {
		int[] events = new int[EVENTS.size()];
		int i = 0;

		for (Entry<String, Integer> entry : EVENTS) {
			events[i++] = entry.getValue();
		}

		return events;
	}

	/**
	 * @param event the event to be converted to a string.
	 * @return the typed event corresponding to the <code>event</code>.
	 */
	public static String toString(Event event) {
		String toString = toString(event.type) + " [" + event.type + "]: "; //$NON-NLS-1$ //$NON-NLS-2$
		switch (event.type) {
		case SWT.KeyDown:
		case SWT.KeyUp:
			toString += new KeyEvent(event).toString();
			break;
		case SWT.MouseDown:
		case SWT.MouseUp:
		case SWT.MouseMove:
		case SWT.MouseEnter:
		case SWT.MouseExit:
		case SWT.MouseDoubleClick:
		case SWT.MouseWheel:
		case SWT.MouseHover:
			toString += new MouseEvent(event).toString();
			break;
		case SWT.Paint:
			toString += new PaintEvent(event).toString();
			break;
		case SWT.Move:
		case SWT.Resize:
			toString += new ControlEvent(event).toString();
			break;
		case SWT.Dispose:
			toString += new DisposeEvent(event).toString();
			break;
		case SWT.Selection:
		case SWT.DefaultSelection:
			toString += new SelectionEvent(event).toString();
			break;
		case SWT.FocusIn:
		case SWT.FocusOut:
			toString += new FocusEvent(event).toString();
			break;
		case SWT.Expand:
		case SWT.Collapse:
			toString += new TreeEvent(event).toString();
			break;
		case SWT.Iconify:
		case SWT.Deiconify:
		case SWT.Close:
		case SWT.Activate:
		case SWT.Deactivate:
			toString += new ShellEvent(event).toString();
			break;
		case SWT.Show:
		case SWT.Hide:
			toString += event.widget instanceof Menu ? new MenuEvent(event).toString() : event.toString();
			break;
		case SWT.Modify:
			toString += new ModifyEvent(event).toString();
			break;
		case SWT.Verify:
			toString += new VerifyEvent(event).toString();
			break;
		case SWT.Help:
			toString += new HelpEvent(event).toString();
			break;
		case SWT.Arm:
			toString += new ArmEvent(event).toString();
			break;
		case SWT.Traverse:
			toString += new TraverseEvent(event).toString();
			break;
		case SWT.HardKeyDown:
		case SWT.HardKeyUp:
		case SWT.DragDetect:
		case SWT.MenuDetect:
		case SWT.SetData:
		default:
			toString += event.toString();
		}
		return toString;
	}
}

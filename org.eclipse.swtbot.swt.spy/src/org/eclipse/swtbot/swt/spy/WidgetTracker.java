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
package org.eclipse.swtbot.swt.spy;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tracker;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.finders.ControlFinder;
import org.eclipse.swtbot.swt.finder.finders.PathGenerator;
import org.eclipse.swtbot.swt.finder.resolvers.IChildrenResolver;
import org.eclipse.swtbot.swt.finder.resolvers.IParentResolver;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;

class WidgetTracker implements Runnable {
	protected IChildrenResolver	childrenResolver;

	protected ControlFinder		controlFinder;

	protected Display			display;

	protected IParentResolver	parentResolver;

	protected SWTSpy			view;

	WidgetTracker(SWTSpy view, IChildrenResolver childrenResolver, IParentResolver parentResolver) {
		this.view = view;
		this.childrenResolver = childrenResolver;
		this.parentResolver = parentResolver;
	}

	// public void clickOnToolbar(Control control) {
	// if (control instanceof ToolBar) {
	// ToolBar toolbar = (ToolBar) control;
	// Display display = toolbar.getDisplay();
	//
	// Point cursorLocation = Display.getCurrent().getCursorLocation();
	//
	// Event event = new Event();
	// event.display = display;
	//
	// event.button = 1;
	// event.type = SWT.MouseDown;
	//
	// event.doit = true;
	// event.x = cursorLocation.x;
	// event.y = cursorLocation.y;
	//
	// // display.post(event);
	//
	// event.type = SWT.MouseUp;
	// // display.post(event);
	// }

	// }

	public String getClassName(Object o) {
		if (o == null)
			return null;
		int i = o.getClass().getName().lastIndexOf('.');
		return o.getClass().getName().substring(i + 1);
	}

	public void getCompositeInformation(Control control, StringBuffer buf) {
		List children = childrenResolver.getChildren(control);
		buf.append("\nChildren: " + children.size() + "\n");
		for (Iterator iterator = children.iterator(); iterator.hasNext();)
			buf.append("\t" + iterator.next() + "\n");
		buf.append("\n");
	}

	public void getLayoutInformation(Control control, StringBuffer buf) {
		buf.append("Layout Information: \n");
		buf.append(control + " @ " + control.handle + "\n");
		buf.append("\tStyle: " + getStyle(control) + "\n");
		buf.append("\tLayout Data: " + control.getLayoutData() + "\n");
		buf.append("\tBounds: " + control.getBounds() + "\n");
		buf.append("\n");
	}

	public void getLocationInformation(Control control, StringBuffer buf) {
		TreePath path = controlFinder.getPath(control);
		String stringFromPath = new PathGenerator().getPathAsString(path);
		buf.append("Location: \n").append(stringFromPath).append("\n\n");
	}

	public void getParentInformation(Control control, StringBuffer buf) {
		Composite parent = control.getParent();
		if (parent != null) {
			buf.append("Parent Tree:\n");

			TreePath parents = controlFinder.getPath(control).getParentPath();

			int segmentCount = parents.getSegmentCount();
			for (int i = segmentCount - 1; i >= 0; i--) {
				String prefix = "";
				Widget segment = (Widget) parents.getSegment(i);
				for (int j = 0; j < segmentCount - i - 1; j++)
					prefix += "\t";

				buf.append(prefix + getClassName(segment) + "[" + SWTUtils.widgetIndex(segment) + "]" + "@" + "\n");
				if (segment instanceof Composite) {
					buf.append(prefix + "\t Layout: " + getClassName(((Composite) segment).getLayout()) + "\n");
					buf.append(prefix + "\t LayoutData: " + getClassName(((Control) segment).getLayoutData()) + "\n");
				}
			}
			buf.append("\n");
		}
	}

	public void getSiblingInformation(Control control, StringBuffer buf) {
		Widget[] siblings = SWTUtils.siblings(control);
		buf.append("Siblings: " + siblings.length + "\n");

		for (int i = 0; i < siblings.length; i++) {
			Widget sibling = siblings[i];
			if (sibling== control)
				buf.append("\t[*]");
			else
				buf.append("\t   ");
			if (sibling instanceof Control)
				buf.append(sibling + ": Layout Data: " + ((Control) sibling).getLayoutData()
						+ "\n");
			else
				buf.append(sibling + "\n");
		}
		buf.append("\n");
	}

	public void run() {
		if ((view.output == null) || view.output.isDisposed() || !view.actionMonitor.isChecked())
			return;

		display = view.output.getDisplay();
		controlFinder = new ControlFinder();

		Control control = display.getCursorControl();

		if (control == null) {
			view.output.setText("");
			view.lastWidget = 0;
		} else if (control.handle != view.lastWidget) {
			view.lastWidget = control.handle;

			StringBuffer buf = new StringBuffer();
			getInformation(control, buf);
			// clickOnToolbar(control);
			view.output.setText(buf.toString());
		}
		display.timerExec(100, view.trackWidgets);
	}

	private void getInformation(Control control, StringBuffer buf) {

		getLocationInformation(control, buf);

		getLayoutInformation(control, buf);

		getCompositeInformation(control, buf);

		getSiblingInformation(control, buf);

		getParentInformation(control, buf);

	}

	String getStyle(Widget w) {

		int style = w.getStyle();
		String result = "";
		if (style == SWT.DEFAULT)
			return "DEFAULT - bad!";
		if ((style & 1 << 1) != 0)
			if ((w instanceof CTabFolder) || (w instanceof StyledText) || (w instanceof List) || (w instanceof Text)
					|| (w instanceof Table) || (w instanceof Tree))
				result += "MULTI | ";
			else if (w instanceof Menu)
				result += "BAR | ";
			else if ((w instanceof Label) || (w instanceof MenuItem) || (w instanceof ToolItem))
				result += "SEPARATOR | ";
			else if (w instanceof Button)
				result += "TOGGLE | ";
			else if (w instanceof ProgressBar)
				result += "INDETERMINATE | ";
			else
				result += "BAR or SEPARATOR or TOGGLE or MULTI or INDETERMINATE or DBCS | ";
		if ((style & 1 << 2) != 0)
			if ((w instanceof Menu) || (w instanceof ToolItem) || (w instanceof CoolItem) || (w instanceof Combo))
				result += "DROP_DOWN | ";
			else if (w instanceof Button)
				result += "ARROW | ";
			else if ((w instanceof CTabFolder) || (w instanceof StyledText) || (w instanceof List)
					|| (w instanceof Text) || (w instanceof Table) || (w instanceof Tree))
				result += "SINGLE | ";
			else if ((w instanceof Label) || (w instanceof Group))
				result += "SHADOW_IN | ";
			else if (w instanceof Decorations)
				result += "TOOL | ";
			else
				result += "ALPHA or TOOL or SINGLE or ARROW or DROP_DOWN or SHADOW_IN | ";
		if ((style & 1 << 3) != 0)
			if (w instanceof Menu)
				result += "POP_UP | ";
			else if ((w instanceof Button) || (w instanceof MenuItem) || (w instanceof ToolItem))
				result += "PUSH | ";
			else if ((w instanceof Combo) || (w instanceof Text) || (w instanceof StyledText))
				result += "READ_ONLY | ";
			else if ((w instanceof Label) || (w instanceof Group) || (w instanceof ToolBar))
				result += "SHADOW_OUT | ";
			else if (w instanceof Decorations)
				result += "NO_TRIM | ";
			else
				result += "POP_UP or PUSH or READ_ONLY or SHADOW_OUT or NO_TRIM or NATIVE | ";
		if ((style & 1 << 4) != 0)
			if ((w instanceof Button) || (w instanceof MenuItem) || (w instanceof ToolItem))
				result += "RADIO | ";
			else if (w instanceof Group)
				result += "SHADOW_ETCHED_IN | ";
			else if ((w instanceof Decorations) || (w instanceof Tracker))
				result += "RESIZE | ";
			else
				result += "RESIZE or SHADOW_ETCHED_IN or RADIO or PHONETIC | ";
		if ((style & 1 << 5) != 0)
			if ((w instanceof Button) || (w instanceof MenuItem) || (w instanceof ToolItem) || (w instanceof Table)
					|| (w instanceof Tree))
				result += "CHECK | ";
			else if ((w instanceof Label) || (w instanceof Group))
				result += "SHADOW_NONE | ";
			else if (w instanceof Decorations)
				result += "TITLE | ";
			else
				result += "ROMAN or CHECK  or SHADOW_NONE or TITLE | ";
		if ((style & 1 << 6) != 0)
			if (w instanceof MenuItem)
				result += "CASCADE | ";
			else if ((w instanceof StyledText) || (w instanceof Label) || (w instanceof Text) || (w instanceof ToolBar))
				result += "WRAP | ";
			else if (w instanceof Combo)
				result += "SIMPLE | ";
			else if (w instanceof Group)
				result += "SHADOW_ETCHED_OUT | ";
			else if ((w instanceof Decorations) || (w instanceof CTabFolder) || (w instanceof CTabItem))
				result += "CLOSE | ";
			else
				result += "CLOSE or MENU or CASCADE or WRAP or SIMPLE or SHADOW_ETCHED_OUT | ";
		if ((style & 1 << 7) != 0)
			if (w instanceof Decorations)
				result += "MIN | ";
			else if ((w instanceof Button) || (w instanceof Tracker))
				result += "UP | ";
			else if (w instanceof CTabFolder)
				result += "TOP | ";
			else
				result += "MIN or UP or TOP | ";
		if ((style & 1 << 8) != 0)
			result += "HORIZONTAL | ";
		if ((style & 1 << 9) != 0)
			result += "VERTICAL | ";
		if ((style & 1 << 10) != 0)
			if (w instanceof Decorations)
				result += "MAX | ";
			else if ((w instanceof Button) || (w instanceof Tracker))
				result += "DOWN | ";
			else if (w instanceof CTabFolder)
				result += "BOTTOM | ";
			else
				result += "MAX or DOWN or BOTTOM | ";
		if ((style & 1 << 11) != 0)
			result += "BORDER | ";
		if ((style & 1 << 12) != 0)
			result += "CLIP_CHILDREN | ";
		if ((style & 1 << 13) != 0)
			result += "CLIP_SIBLINGS | ";
		if ((style & 1 << 14) != 0)
			result += "ON_TOP or LEAD or LEFT | ";
		if ((style & 1 << 15) != 0)
			if (w instanceof Shell)
				result += "PRIMARY_MODAL | ";
			else if (w instanceof Table)
				result += "HIDE_SELECTION | ";
			else
				result += "PRIMARY_MODAL or HIDE_SELECTION | ";
		if ((style & 1 << 16) != 0)
			if ((w instanceof StyledText) || (w instanceof Table))
				result += "FULL_SELECTION | ";
			else if (w instanceof Shell)
				result += "APPLICATION_MODAL | ";
			else if (w instanceof ProgressBar)
				result += "SMOOTH | ";
			else
				result += "FULL_SELECTION or SMOOTH or APPLICATION_MODAL | ";
		if ((style & 1 << 17) != 0)
			if (w instanceof Shell)
				result += "SYSTEM_MODAL | ";
			else if ((w instanceof Button) || (w instanceof Label) || (w instanceof TableColumn)
					|| (w instanceof Tracker) || (w instanceof ToolBar))
				result += "TRAIL | ";
			else
				result += "SYSTEM_MODAL or TRAIL or RIGHT | ";
		if ((style & 1 << 18) != 0)
			result += "NO_BACKGROUND | ";
		if ((style & 1 << 19) != 0)
			result += "NO_FOCUS | ";
		if ((style & 1 << 20) != 0)
			result += "NO_REDRAW_RESIZE | ";
		if ((style & 1 << 21) != 0)
			result += "NO_MERGE_PAINTS | ";
		if ((style & 1 << 22) != 0)
			if (w instanceof Text)
				result += "PASSWORD | ";
			else if (w instanceof Composite)
				result += "NO_RADIO_GROUP | ";
			else
				result += "NO_RADIO_GROUP or PASSWORD | ";
		if ((style & 1 << 23) != 0)
			result += "FLAT | ";
		if ((style & 1 << 24) != 0)
			if ((w instanceof Button) || (w instanceof Label) || (w instanceof TableColumn))
				result += "CENTER | ";
			else
				result += "EMBEDDED or CENTER | ";
		if ((style & 1 << 25) != 0)
			result += "LEFT_TO_RIGHT | ";
		if ((style & 1 << 26) != 0)
			result += "RIGHT_TO_LEFT | ";
		if ((style & 1 << 27) != 0)
			result += "MIRRORED | ";
		if ((style & 1 << 28) != 0)
			result += "VIRTUAL | ";
		int lastOr = result.lastIndexOf("|");
		if (lastOr == result.length() - 2)
			result = result.substring(0, result.length() - 2);
		return result;
	}
}

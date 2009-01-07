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
package org.eclipse.swtbot.eclipse.spy;

import java.util.Iterator;
import java.util.List;

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
import org.eclipse.swtbot.swt.finder.utils.TreePath;

class EclipseWidgetTracker implements Runnable {
	protected IChildrenResolver	childrenResolver;

	protected ControlFinder		controlFinder;

	protected Display			display;

	protected IParentResolver	parentResolver;

	protected EclipseSpy		view;

	EclipseWidgetTracker(EclipseSpy view, IChildrenResolver childrenResolver, IParentResolver parentResolver) {
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
		buf.append("\nChildren: " + children.size() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		for (Iterator iterator = children.iterator(); iterator.hasNext();)
			buf.append("\t" + iterator.next() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buf.append("\n"); //$NON-NLS-1$
	}

	public void getLayoutInformation(Control control, StringBuffer buf) {
		buf.append("Layout Information: \n"); //$NON-NLS-1$
		buf.append(SWTUtils.toString(control) + " @ " + control.handle + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buf.append("\tStyle: " + getStyle(control) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buf.append("\tLayout Data: " + control.getLayoutData() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buf.append("\tBounds: " + control.getBounds() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buf.append("\n"); //$NON-NLS-1$
	}

	public void getLocationInformation(Control control, StringBuffer buf) {
		TreePath path = controlFinder.getPath(control);
		String stringFromPath = new PathGenerator().getPathAsString(path);
		buf.append("Location: \n").append(stringFromPath).append("\n\n"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void getParentInformation(Control control, StringBuffer buf) {
		Composite parent = control.getParent();
		if (parent != null) {
			buf.append("Parent Tree:\n"); //$NON-NLS-1$

			TreePath parents = controlFinder.getPath(control).getParentPath();

			int segmentCount = parents.getSegmentCount();
			for (int i = segmentCount - 1; i >= 0; i--) {
				String prefix = ""; //$NON-NLS-1$
				Widget segment = (Widget) parents.getSegment(i);
				for (int j = 0; j < segmentCount - i - 1; j++)
					prefix += "\t"; //$NON-NLS-1$

				buf.append(prefix + renderWidget(segment) + "[" + SWTUtils.widgetIndex(segment) + "]" + "@" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				if (segment instanceof Composite) {
					buf.append(prefix + "\t Layout: " + getClassName(((Composite) segment).getLayout()) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
					buf.append(prefix + "\t LayoutData: " + getClassName(((Control) segment).getLayoutData()) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
			buf.append("\n"); //$NON-NLS-1$
		}
	}

	public void getSiblingInformation(Control control, StringBuffer buf) {
		Widget[] siblings = SWTUtils.siblings(control);
		buf.append("Siblings: " + siblings.length + "\n"); //$NON-NLS-1$ //$NON-NLS-2$

		for (int i = 0; i < siblings.length; i++) {
			Widget sibling = siblings[i];
			if (sibling == control)
				buf.append("\t[*]"); //$NON-NLS-1$
			else
				buf.append("\t   "); //$NON-NLS-1$
			if (sibling instanceof Control)
				buf.append(renderWidget(sibling) + ": Layout Data: " //$NON-NLS-1$
						+ ((Control) sibling).getLayoutData() + "\n"); //$NON-NLS-1$
			else
				buf.append(renderWidget(sibling) +  "\n"); //$NON-NLS-1$
		}
		buf.append("\n"); //$NON-NLS-1$
	}

	private String renderWidget(Widget sibling) {
		return SWTUtils.toString(sibling);
	}

	public void run() {
		if ((view.output == null) || view.output.isDisposed() || !view.actionMonitor.isChecked())
			return;

		display = view.output.getDisplay();
		controlFinder = new ControlFinder();

		Control control = display.getCursorControl();
		display.getCursorLocation();

		if (control == null) {
			view.output.setText(""); //$NON-NLS-1$
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
		String result = ""; //$NON-NLS-1$
		if (style == SWT.DEFAULT)
			return "DEFAULT - bad!"; //$NON-NLS-1$
		if ((style & 1 << 1) != 0)
			if ((w instanceof CTabFolder) || (w instanceof StyledText) || (w instanceof List) || (w instanceof Text)
					|| (w instanceof Table) || (w instanceof Tree))
				result += "MULTI | "; //$NON-NLS-1$
			else if (w instanceof Menu)
				result += "BAR | "; //$NON-NLS-1$
			else if ((w instanceof Label) || (w instanceof MenuItem) || (w instanceof ToolItem))
				result += "SEPARATOR | "; //$NON-NLS-1$
			else if (w instanceof Button)
				result += "TOGGLE | "; //$NON-NLS-1$
			else if (w instanceof ProgressBar)
				result += "INDETERMINATE | "; //$NON-NLS-1$
			else
				result += "BAR or SEPARATOR or TOGGLE or MULTI or INDETERMINATE or DBCS | "; //$NON-NLS-1$
		if ((style & 1 << 2) != 0)
			if ((w instanceof Menu) || (w instanceof ToolItem) || (w instanceof CoolItem) || (w instanceof Combo))
				result += "DROP_DOWN | "; //$NON-NLS-1$
			else if (w instanceof Button)
				result += "ARROW | "; //$NON-NLS-1$
			else if ((w instanceof CTabFolder) || (w instanceof StyledText) || (w instanceof List)
					|| (w instanceof Text) || (w instanceof Table) || (w instanceof Tree))
				result += "SINGLE | "; //$NON-NLS-1$
			else if ((w instanceof Label) || (w instanceof Group))
				result += "SHADOW_IN | "; //$NON-NLS-1$
			else if (w instanceof Decorations)
				result += "TOOL | "; //$NON-NLS-1$
			else
				result += "ALPHA or TOOL or SINGLE or ARROW or DROP_DOWN or SHADOW_IN | "; //$NON-NLS-1$
		if ((style & 1 << 3) != 0)
			if (w instanceof Menu)
				result += "POP_UP | "; //$NON-NLS-1$
			else if ((w instanceof Button) || (w instanceof MenuItem) || (w instanceof ToolItem))
				result += "PUSH | "; //$NON-NLS-1$
			else if ((w instanceof Combo) || (w instanceof Text) || (w instanceof StyledText))
				result += "READ_ONLY | "; //$NON-NLS-1$
			else if ((w instanceof Label) || (w instanceof Group) || (w instanceof ToolBar))
				result += "SHADOW_OUT | "; //$NON-NLS-1$
			else if (w instanceof Decorations)
				result += "NO_TRIM | "; //$NON-NLS-1$
			else
				result += "POP_UP or PUSH or READ_ONLY or SHADOW_OUT or NO_TRIM or NATIVE | "; //$NON-NLS-1$
		if ((style & 1 << 4) != 0)
			if ((w instanceof Button) || (w instanceof MenuItem) || (w instanceof ToolItem))
				result += "RADIO | "; //$NON-NLS-1$
			else if (w instanceof Group)
				result += "SHADOW_ETCHED_IN | "; //$NON-NLS-1$
			else if ((w instanceof Decorations) || (w instanceof Tracker))
				result += "RESIZE | "; //$NON-NLS-1$
			else
				result += "RESIZE or SHADOW_ETCHED_IN or RADIO or PHONETIC | "; //$NON-NLS-1$
		if ((style & 1 << 5) != 0)
			if ((w instanceof Button) || (w instanceof MenuItem) || (w instanceof ToolItem) || (w instanceof Table)
					|| (w instanceof Tree))
				result += "CHECK | "; //$NON-NLS-1$
			else if ((w instanceof Label) || (w instanceof Group))
				result += "SHADOW_NONE | "; //$NON-NLS-1$
			else if (w instanceof Decorations)
				result += "TITLE | "; //$NON-NLS-1$
			else
				result += "ROMAN or CHECK  or SHADOW_NONE or TITLE | "; //$NON-NLS-1$
		if ((style & 1 << 6) != 0)
			if (w instanceof MenuItem)
				result += "CASCADE | "; //$NON-NLS-1$
			else if ((w instanceof StyledText) || (w instanceof Label) || (w instanceof Text) || (w instanceof ToolBar))
				result += "WRAP | "; //$NON-NLS-1$
			else if (w instanceof Combo)
				result += "SIMPLE | "; //$NON-NLS-1$
			else if (w instanceof Group)
				result += "SHADOW_ETCHED_OUT | "; //$NON-NLS-1$
			else if ((w instanceof Decorations) || (w instanceof CTabFolder) || (w instanceof CTabItem))
				result += "CLOSE | "; //$NON-NLS-1$
			else
				result += "CLOSE or MENU or CASCADE or WRAP or SIMPLE or SHADOW_ETCHED_OUT | "; //$NON-NLS-1$
		if ((style & 1 << 7) != 0)
			if (w instanceof Decorations)
				result += "MIN | "; //$NON-NLS-1$
			else if ((w instanceof Button) || (w instanceof Tracker))
				result += "UP | "; //$NON-NLS-1$
			else if (w instanceof CTabFolder)
				result += "TOP | "; //$NON-NLS-1$
			else
				result += "MIN or UP or TOP | "; //$NON-NLS-1$
		if ((style & 1 << 8) != 0)
			result += "HORIZONTAL | "; //$NON-NLS-1$
		if ((style & 1 << 9) != 0)
			result += "VERTICAL | "; //$NON-NLS-1$
		if ((style & 1 << 10) != 0)
			if (w instanceof Decorations)
				result += "MAX | "; //$NON-NLS-1$
			else if ((w instanceof Button) || (w instanceof Tracker))
				result += "DOWN | "; //$NON-NLS-1$
			else if (w instanceof CTabFolder)
				result += "BOTTOM | "; //$NON-NLS-1$
			else
				result += "MAX or DOWN or BOTTOM | "; //$NON-NLS-1$
		if ((style & 1 << 11) != 0)
			result += "BORDER | "; //$NON-NLS-1$
		if ((style & 1 << 12) != 0)
			result += "CLIP_CHILDREN | "; //$NON-NLS-1$
		if ((style & 1 << 13) != 0)
			result += "CLIP_SIBLINGS | "; //$NON-NLS-1$
		if ((style & 1 << 14) != 0)
			result += "ON_TOP or LEAD or LEFT | "; //$NON-NLS-1$
		if ((style & 1 << 15) != 0)
			if (w instanceof Shell)
				result += "PRIMARY_MODAL | "; //$NON-NLS-1$
			else if (w instanceof Table)
				result += "HIDE_SELECTION | "; //$NON-NLS-1$
			else
				result += "PRIMARY_MODAL or HIDE_SELECTION | "; //$NON-NLS-1$
		if ((style & 1 << 16) != 0)
			if ((w instanceof StyledText) || (w instanceof Table))
				result += "FULL_SELECTION | "; //$NON-NLS-1$
			else if (w instanceof Shell)
				result += "APPLICATION_MODAL | "; //$NON-NLS-1$
			else if (w instanceof ProgressBar)
				result += "SMOOTH | "; //$NON-NLS-1$
			else
				result += "FULL_SELECTION or SMOOTH or APPLICATION_MODAL | "; //$NON-NLS-1$
		if ((style & 1 << 17) != 0)
			if (w instanceof Shell)
				result += "SYSTEM_MODAL | "; //$NON-NLS-1$
			else if ((w instanceof Button) || (w instanceof Label) || (w instanceof TableColumn)
					|| (w instanceof Tracker) || (w instanceof ToolBar))
				result += "TRAIL | "; //$NON-NLS-1$
			else
				result += "SYSTEM_MODAL or TRAIL or RIGHT | "; //$NON-NLS-1$
		if ((style & 1 << 18) != 0)
			result += "NO_BACKGROUND | "; //$NON-NLS-1$
		if ((style & 1 << 19) != 0)
			result += "NO_FOCUS | "; //$NON-NLS-1$
		if ((style & 1 << 20) != 0)
			result += "NO_REDRAW_RESIZE | "; //$NON-NLS-1$
		if ((style & 1 << 21) != 0)
			result += "NO_MERGE_PAINTS | "; //$NON-NLS-1$
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

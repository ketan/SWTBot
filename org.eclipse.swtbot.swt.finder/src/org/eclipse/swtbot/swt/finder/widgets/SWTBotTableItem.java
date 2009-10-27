/*******************************************************************************
 * Copyright (c) 2008 http://www.inria.fr/ and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     http://www.inria.fr/ - initial API and implementation
 *     Cédric Chabanois - bug 269164
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.StringResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.hamcrest.SelfDescribing;

/**
 * @author Vincent MAHE &lt;vmahe [at] free [dot]fr&gt;
 * @author Cédric Chabanois
 * @version $Id$
 * @since 1.3
 */
public class SWTBotTableItem extends AbstractSWTBot<TableItem> {

	private Table	table;

	/**
	 * @param tableItem the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotTableItem(final TableItem tableItem) throws WidgetNotFoundException {
		this(tableItem, null);
	}

	/**
	 * @param tableItem the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotTableItem(final TableItem tableItem, SelfDescribing description) throws WidgetNotFoundException {
		super(tableItem, description);
		this.table = syncExec(new WidgetResult<Table>() {
			public Table run() {
				return tableItem.getParent();
			}
		});
	}

	/**
	 * Selects the current table item.
	 * 
	 * @return the current node.
	 */
	public SWTBotTableItem select() {
		assertEnabled();
		syncExec(new VoidResult() {
			public void run() {
				table.setFocus();
				table.setSelection(widget);
			}
		});
		notifySelect();
		return this;
	}

	/**
	 * Click on the table at given coordinates
	 * 
	 * @param x the x co-ordinate of the click
	 * @param y the y co-ordinate of the click
	 */
	protected void clickXY(int x, int y) {
		log.debug(MessageFormat.format("Clicking on {0}", this)); //$NON-NLS-1$
		notifyTable(SWT.MouseEnter);
		notifyTable(SWT.MouseMove);
		notifyTable(SWT.Activate);
		notifyTable(SWT.FocusIn);
		notifyTable(SWT.MouseDown, createMouseEvent(x, y, 1, SWT.BUTTON1, 1));
		notifyTable(SWT.MouseUp);
		notifyTable(SWT.Selection, createEvent());
		notifyTable(SWT.MouseHover);
		notifyTable(SWT.MouseMove);
		notifyTable(SWT.MouseExit);
		notifyTable(SWT.Deactivate);
		notifyTable(SWT.FocusOut);
		log.debug(MessageFormat.format("Clicked on {0}", this)); //$NON-NLS-1$
	}

	private void notifyTable(int eventType, Event event) {
		notify(eventType, event, table);
	}

	private void notifyTable(int event) {
		notifyTable(event, createEvent());
	}

	/**
	 * Clicks on this node.
	 * 
	 * @return the current node.
	 */
	public SWTBotTableItem click() {
		assertEnabled();
		Rectangle cellBounds = syncExec(new Result<Rectangle>() {
			public Rectangle run() {
				return widget.getBounds();
			}
		});
		clickXY(cellBounds.x + (cellBounds.width / 2), cellBounds.y + (cellBounds.height / 2));
		return this;
	}

	public String getText() {
		return syncExec(new StringResult() {
			public String run() {
				return widget.getText();
			}
		});
	}

	public String getText(final int index) {
		return syncExec(new StringResult() {
			public String run() {
				return widget.getText(index);
			}
		});
	}
	
	public SWTBotMenu contextMenu(String text) throws WidgetNotFoundException {
		new SWTBotTable(table).assertEnabled();
		select();
		notifyTable(SWT.MenuDetect);
		return super.contextMenu(table, text);
	}

	/**
	 * Toggle the table item.
	 */
	public void toggleCheck() {
		setChecked(!isChecked());
	}

	/**
	 * Check the table item.
	 */
	public void check() {
		setChecked(true);
	}

	/**
	 * Uncheck the table item.
	 */
	public void uncheck() {
		setChecked(false);
	}

	/**
	 * Gets if the checkbox button is checked.
	 * 
	 * @return <code>true</code> if the checkbox is checked. Otherwise <code>false</code>.
	 */
	public boolean isChecked() {
		assertIsCheck();
		return syncExec(new BoolResult() {
			public Boolean run() {
				return widget.getChecked();
			}
		});
	}

	/**
	 * Gets if the checkbox button is grayed.
	 * 
	 * @return <code>true</code> if the checkbox is grayed, <code>false</code> otherwise.
	 */
	public boolean isGrayed() {
		assertIsCheck();
		return syncExec(new BoolResult() {
			public Boolean run() {
				return widget.getGrayed();
			}
		});
	}

	/**
	 * Creates an event for CheckboxTableItem case.
	 * 
	 * @return an event that encapsulates {@link #widget} and {@link #display}.
	 */
	private Event createCheckEvent() {
		Event event = createEvent();
		event.detail = SWT.CHECK;
		return event;
	}

	protected Event createEvent() {
		Event event = super.createEvent();
		event.widget = table;
		event.item = widget;
		return event;
	}

	private void setChecked(final boolean checked) {
		assertEnabled();
		assertIsCheck();
		syncExec(new VoidResult() {
			public void run() {
				TableItem item = widget;
				log.debug(MessageFormat.format("Setting state to {0} on: {1}", (checked ? "checked" : "unchecked"), item.getText())); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				item.setChecked(checked);
			}
		});
		notifyCheck();
	}

	private void assertIsCheck() {
		Assert.isLegal(hasStyle(table, SWT.CHECK), "The table does not have the style SWT.CHECK"); //$NON-NLS-1$
	}

	/**
	 * notify listeners about checkbox state change.
	 * 
	 * @since 1.3
	 */
	private void notifyCheck() {
		syncExec(new VoidResult() {
			public void run() {
				table.notifyListeners(SWT.Selection, createCheckEvent());
			}
		});
	}

	private void notifySelect() {
		syncExec(new VoidResult() {
			public void run() {
				table.notifyListeners(SWT.Selection, createSelectionEvent());
			}
		});
	}

	protected void assertEnabled() {
		new SWTBotTable(table).assertEnabled();
	}

	private Event createSelectionEvent() {
		Event event = createEvent();
		event.item = widget;
		event.widget = table;
		return event;
	}

//	protected Rectangle absoluteLocation() {
//		return syncExec(new Result<Rectangle>() {
//			public Rectangle run() {
//				return display.map(widget.getParent(), null, widget.getBounds());
//			}
//		});
//	}
//	
//	/**
//	 * Click on the center of the widget.
//	 * 
//	 * @param post
//	 * 				Whether or not {@link Display#post} should be used
//	 */
//	private SWTBotTableItem click(final boolean post) {
//		if (post) {
//			Rectangle location = absoluteLocation();
//			click(location.x, location.y, true);
//		}
//		else
//			click();
//		return this;
//	}
//	
//	/**
//	 * Right click on the center of the widget.
//	 * 
//	 * @param post
//	 * 				Whether or not {@link Display#post} should be used
//	 */
//	private SWTBotTableItem rightClick(final boolean post) {
//		if (post) {
//			Rectangle location = absoluteLocation();
//			rightClick(location.x, location.y, true);
//		}
//		else
//			rightClick();
//		return this;
//	}
//
//	/**
//	 * Moves the cursor to the center of the widget
//	 */
//	private SWTBotTableItem moveMouseToWidget() {
//		syncExec(new VoidResult() {
//			public void run() {
//				Rectangle location = absoluteLocation();
//				moveMouse(location.x, location.y);
//			}
//		});
//		return this;
//	}
}

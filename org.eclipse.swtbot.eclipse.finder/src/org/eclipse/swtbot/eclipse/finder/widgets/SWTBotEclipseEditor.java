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
package org.eclipse.swtbot.eclipse.finder.widgets;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swtbot.eclipse.finder.SWTEclipseBot;
import org.eclipse.swtbot.eclipse.finder.exceptions.QuickFixNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.ListResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.Position;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.eclipse.swtbot.swt.finder.widgets.WidgetNotFoundException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;

/**
 * This represents an eclipse editor item.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotEclipseEditor extends SWTBotWorkbenchPart<IEditorReference> {

	private final SWTBotStyledText	styledText;

	/**
	 * The StyledText widget inside the editor
	 *
	 * @since 2.0
	 */
	public final StyledText			widget;

	/**
	 * Constructs an instance of the given object.
	 *
	 * @param editorReference the editor reference.
	 * @param bot the instance of {@link SWTEclipseBot} which will be used to drive operations on behalf of this object.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 2.0
	 */
	public SWTBotEclipseEditor(IEditorReference editorReference, SWTEclipseBot bot) throws WidgetNotFoundException {
		super(editorReference, bot);
		this.styledText = new SWTBotStyledText((StyledText) findWidget(widgetOfType(StyledText.class)));
		this.widget = this.styledText.widget;
	}

	/**
	 * Applys a quick fix item at the given index.
	 *
	 * @param quickFixIndex the index of the quickfix item to apply.
	 * @throws QuickFixNotFoundException if the quickfix could not be found.
	 * @throws WidgetNotFoundException if the quickfix could not be found.
	 */
	public void quickfix(int quickFixIndex) throws QuickFixNotFoundException, WidgetNotFoundException {
		SWTBotShell quickFixShell = activateQuickFixShell();
		SWTBotTable quickFixTable = getQuickFixTable(quickFixShell);
		applyQuickFix(quickFixTable, quickFixIndex);
	}

	/**
	 * Applys a quick fix item with the given name.
	 *
	 * @param quickFixName the name of the quick fix to apply.
	 * @throws QuickFixNotFoundException if the quickfix could not be found.
	 */
	public void quickfix(String quickFixName) throws QuickFixNotFoundException {
		int retries = 10;
		SWTBotShell quickFixShell = activateQuickFixShell();
		SWTBotTable quickFixTable = getQuickFixTable(quickFixShell);
		applyQuickFix(quickFixTable, quickFixName, retries);
	}

	/**
	 * Finds all the quickfixes in the quickfix list.
	 *
	 * @return the list of all available quickfixes.
	 * @throws QuickFixNotFoundException if the quickfix could not be found.
	 * @since 1.2
	 */
	public List<String> getQuickFixes() throws QuickFixNotFoundException {
		SWTBotShell quickFixShell = activateQuickFixShell();
		SWTBotTable quickFixTable = getQuickFixTable(quickFixShell);
		int rowCount = quickFixTable.rowCount();
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < rowCount; i++)
			result.add(quickFixTable.cell(i, 0));
		return result;
	}

	/**
	 * Gets the quick fix item count.
	 *
	 * @return the number of quickfix items in the quickfix proposals.
	 * @throws QuickFixNotFoundException if the quickfix could not be found.
	 * @since 1.2
	 */
	public int getQuickfixListItemCount() throws QuickFixNotFoundException {
		SWTBotShell quickFixShell = activateQuickFixShell();
		SWTBotTable quickFixTable = getQuickFixTable(quickFixShell);
		return quickFixTable.rowCount();
	}

	/**
	 * Attempst to applys the quick fix.
	 * <p>
	 * FIXME: this needs a lot of optimization.
	 * </p>
	 *
	 * @param quickFixTable the table containing the quickfix.
	 * @param quickFixName the name of the quickfix to apply.
	 * @param retries the number of retries, before giving up.
	 * @throws QuickFixNotFoundException if the quickfix could not be found.
	 */
	protected void applyQuickFix(SWTBotTable quickFixTable, String quickFixName, int retries) throws QuickFixNotFoundException {
		log.debug("Trying to activate quickfix shell.");
		int quickFixIndex = quickFixTable.indexOf(quickFixName);
		if (quickFixIndex >= 0) {
			applyQuickFix(quickFixTable, quickFixIndex);
			return;
		}
		throw new QuickFixNotFoundException("Quickfix options not found. Giving up.");
	}

	/**
	 * Gets the quick fix table.
	 *
	 * @param quickFixShell the shell containing the quickfixes.
	 * @return the table containing the quickfix.
	 * @throws QuickFixNotFoundException if the table could not be found.
	 */
	protected SWTBotTable getQuickFixTable(SWTBotShell quickFixShell) throws QuickFixNotFoundException {
		try {
			Table table = (Table) bot.widget(widgetOfType(Table.class), quickFixShell.widget);
			return new SWTBotTable(table);
		} catch (Exception e) {
			throw new QuickFixNotFoundException("Quickfix options not found. Giving up.", e);
		}
	}

	/**
	 * Applies the specified quickfix.
	 *
	 * @param quickFixTable the table containing the quickfix
	 * @param quickFixIndex the index of the quickfix.
	 */
	protected void applyQuickFix(final SWTBotTable quickFixTable, final int quickFixIndex) {
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				Table table = quickFixTable.widget;
				log.debug(MessageFormat.format("Selecting row [{0}] {1} in {2}", quickFixIndex, table.getItem(quickFixIndex).getText(),
						table));
				table.setSelection(quickFixIndex);
				Event event = new Event();
				event.type = SWT.Selection;
				event.widget = table;
				event.item = table.getItem(quickFixIndex);
				table.notifyListeners(SWT.Selection, event);
				table.notifyListeners(SWT.DefaultSelection, event);
			}
		});
		bot.sleep(1000);
		// quickFixTable.select(quickFixIndex);
	}

	/**
	 * Gets teh active quick fix shell.
	 *
	 * @return the quickfix shell.
	 * @throws QuickFixNotFoundException if the quickfix shell is not found.
	 */
	protected SWTBotShell activateQuickFixShell() throws QuickFixNotFoundException {
		styledText.notifyKeyboardEvent(SWT.MOD1, '1');
		return activatePopupShell();
	}

	/**
	 * This activates the popup shell.
	 *
	 * @return The shell.
	 * @throws QuickFixNotFoundException Throw if a quick fix problem occurs.
	 */
	private SWTBotShell activatePopupShell() throws QuickFixNotFoundException {
		try {
			SWTBotShell shell = bot.shell("", 0);
			shell.activate();
			log.debug("Activated quickfix shell.");
			return shell;
		} catch (Exception e) {
			throw new QuickFixNotFoundException("Quickfix popup not found. Giving up.", e);
		}
	}

	/**
	 * Gets the auto completion proposal matching the given text..
	 *
	 * @param insertText the proposal text to type before auto completing
	 * @return the list of proposals
	 * @throws QuickFixNotFoundException if the autocomplete proposal could not be found.
	 * @throws TimeoutException if the autocomplete shell did not close in time.
	 * @since 1.2
	 */
	public List<String> getAutoCompleteProposals(String insertText) throws QuickFixNotFoundException, TimeoutException {
		styledText.typeText(insertText);
		bot.sleep(1000);
		SWTBotShell autoCompleteShell = activateAutoCompleteShell();
		final SWTBotTable autoCompleteTable = getQuickFixTable(autoCompleteShell);
		List<String> result = UIThreadRunnable.syncExec(new ListResult<String>() {
			public List<String> run() {
				TableItem[] items = autoCompleteTable.widget.getItems();
				ArrayList<String> result = new ArrayList<String>();
				for (int i = 0; i < items.length; i++) {
					TableItem tableItem = items[i];
					result.add(tableItem.getText());
				}
				return result;
			}
		});
		// makeProposalsDisappear();
		autoCompleteShell.close();
		return result;
	}

	/**
	 * Auto completes the given proposal.
	 *
	 * @param insertText the text to be inserted before activating the auto-complete.
	 * @param proposalText the auto-completion proposal to select from the list.
	 * @throws QuickFixNotFoundException if the auto-complete proposal could not be found.
	 */
	public void autoCompleteProposal(String insertText, String proposalText) throws QuickFixNotFoundException {
		styledText.typeText(insertText);

		autoComplete(proposalText);
	}

	/**
	 * This performs the auto complete.
	 *
	 * @param proposalText The text to propose.
	 * @throws QuickFixNotFoundException Thrown if the quick fix error occurs.
	 */
	private void autoComplete(String proposalText) throws QuickFixNotFoundException {
		int retries = 10;
		SWTBotShell autoCompleteShell = activateAutoCompleteShell();
		SWTBotTable autoCompleteTable = getQuickFixTable(autoCompleteShell);
		applyQuickFix(autoCompleteTable, proposalText, retries);
	}

	/**
	 * Gets the active auto complete shell.
	 *
	 * @return The shell.
	 * @throws QuickFixNotFoundException Thrown if a qaick fix error occurs.
	 */
	private SWTBotShell activateAutoCompleteShell() throws QuickFixNotFoundException {
		styledText.notifyKeyboardEvent(SWT.CTRL, ' ');
		return activatePopupShell();
	}

	/**
	 * Gets the context menu in the editor.
	 *
	 * @param text the context menu item.
	 * @return the menu
	 * @throws WidgetNotFoundException if the menu with the specified text could not be found.
	 * @see org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot#contextMenu(java.lang.String)
	 */
	public SWTBotMenu contextMenu(String text) throws WidgetNotFoundException {
		return styledText.contextMenu(text);
	}

	/**
	 * Gets the current cursor position.
	 *
	 * @return the position of the cursor.
	 * @see SWTBotStyledText#cursorPosition()
	 */
	public Position cursorPosition() {
		return styledText.cursorPosition();
	}

	/**
	 * Gets if the object's widget is enabled.
	 *
	 * @return <code>true</code> if the widget is enabled.
	 * @see org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot#isEnabled()
	 */
	public boolean isEnabled() {
		return styledText.isEnabled();
	}

	/**
	 * Gets the current selection.
	 *
	 * @return The selected string.
	 */
	public String getSelection() {
		return styledText.getSelection();
	}

	/**
	 * Gets the style text.
	 *
	 * @param line the line number.
	 * @param column the column number.
	 * @return the {@link StyleRange} at the specified location
	 * @see SWTBotStyledText#getStyle(int, int)
	 */
	public StyleRange getStyle(int line, int column) {
		return styledText.getStyle(line, column);
	}

	/**
	 * Gets the text of this object's widget.
	 *
	 * @return the text on the styledtext.
	 * @see org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot#getText()
	 */
	public String getText() {
		return styledText.getText();
	}

	/**
	 * @param line the line number.
	 * @param column the column number.
	 * @param text the text to be typed at the specified location
	 * @see SWTBotStyledText#typeText(int, int, java.lang.String)
	 * @since 1.0
	 */
	public void typeText(int line, int column, String text) {
		styledText.typeText(line, column, text);
	}

	/**
	 * @param text the text to be typed at the location of the caret. *
	 * @see SWTBotStyledText#typeText(java.lang.String)
	 * @since 1.0
	 */
	public void typeText(String text) {
		styledText.typeText(text);
	}

	/**
	 * @param line the line number.
	 * @param column the column number.
	 * @param text the text to be inserted at the specified location
	 * @see SWTBotStyledText#insertText(int, int, java.lang.String)
	 */
	public void insertText(int line, int column, String text) {
		styledText.insertText(line, column, text);
	}

	/**
	 * @param text the text to be inserted at the location of the caret.
	 * @see SWTBotStyledText#insertText(java.lang.String)
	 */
	public void insertText(String text) {
		styledText.insertText(text);
	}

	/**
	 * @param text the text to be typed at the location of the caret.
	 * @param interval the interval between consecutive key strokes.
	 * @see SWTBotStyledText#typeText(java.lang.String, int)
	 * @since 1.0
	 */
	public void typeText(String text, int interval) {
		styledText.typeText(text, interval);
	}

	/**
	 * @param line the line numnber.
	 * @param column the column number.
	 * @see SWTBotStyledText#navigateTo(int, int)
	 */
	public void navigateTo(int line, int column) {
		styledText.navigateTo(line, column);
	}

	/**
	 * @param modificationKey the modification key.
	 * @param c the character.
	 * @see Event#character
	 * @see Event#stateMask
	 * @see SWTBotStyledText#notifyKeyboardEvent(int, char)
	 */
	public void notifyKeyboardEvent(int modificationKey, char c) {
		styledText.notifyKeyboardEvent(modificationKey, c);
	}

	/**
	 * @param line the line number.
	 * @param column the column number.
	 * @param length the length of the selection.
	 * @see SWTBotStyledText#selectRange(int, int, int)
	 */
	public void selectRange(int line, int column, int length) {
		styledText.selectRange(line, column, length);
	}

	/**
	 * @param line the line number to select.
	 * @see SWTBotStyledText#selectLine(int)
	 * @since 1.1
	 */
	public void selectLine(int line) {
		styledText.selectLine(line);
	}

	/**
	 * Selects the text on the current line.
	 *
	 * @see SWTBotStyledText#selectCurrentLine()
	 * @since 1.1
	 */
	public void selectCurrentLine() {
		styledText.selectCurrentLine();
	}

	/**
	 * @see SWTBotStyledText#setFocus()
	 */
	public void setFocus() {
		styledText.setFocus();
	}

	/**
	 * @param text the text to set.
	 * @see SWTBotStyledText#setText(java.lang.String)
	 */
	public void setText(String text) {
		styledText.setText(text);
	}

	/**
	 * Save the editor and close it.
	 */
	public void saveAndClose() {
		save();
		close();
	}

	public void close() {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				partReference.getPage().closeEditor(partReference.getEditor(false), false);
			}
		});
	}

	/**
	 * Save the editor.
	 */
	public void save() {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				partReference.getEditor(false).doSave(null);
			}
		});
	}

	/**
	 * @return the bullet on the current line.
	 * @see SWTBotStyledText#getBulletOnCurrentLine()
	 */
	public Bullet getBulletOnCurrentLine() {
		return styledText.getBulletOnCurrentLine();
	}

	/**
	 * @param line the line number.
	 * @return the bullet on the given line.
	 * @see SWTBotStyledText#getBulletOnLine(int)
	 */
	public Bullet getBulletOnLine(int line) {
		return styledText.getBulletOnLine(line);
	}

	/**
	 * @param line the line number.
	 * @param column the column number.
	 * @param length the length.
	 * @return the styles in the specified range.
	 * @see SWTBotStyledText#getStyles(int, int, int)
	 */
	public StyleRange[] getStyles(int line, int column, int length) {
		return styledText.getStyles(line, column, length);
	}

	/**
	 * @return the text on the current line, without the line delimiters.
	 * @see SWTBotStyledText#getTextOnCurrentLine()
	 */
	public String getTextOnCurrentLine() {
		return styledText.getTextOnCurrentLine();
	}

	/**
	 * @param line the line number.
	 * @return the text on the given line number, without the line delimiters.
	 * @see SWTBotStyledText#getTextOnLine(int)
	 */
	public String getTextOnLine(int line) {
		return styledText.getTextOnLine(line);
	}

	/**
	 * @return <code>true</code> if the styledText has a bullet on the given line, <code>false</code> otherwise.
	 * @see SWTBotStyledText#hasBulletOnCurrentLine()
	 */
	public boolean hasBulletOnCurrentLine() {
		return styledText.hasBulletOnCurrentLine();
	}

	/**
	 * @param line the line number.
	 * @return <code>true</code> if the styledText has a bullet on the given line, <code>false</code> otherwise.
	 * @see SWTBotStyledText#hasBulletOnLine(int)
	 */
	public boolean hasBulletOnLine(int line) {
		return styledText.hasBulletOnLine(line);
	}

	/**
	 * @param modificationKey the modification key.
	 * @param c the character.
	 * @param keyCode the keycode.
	 * @see SWTBotStyledText#notifyKeyboardEvent(int, char, int)
	 */
	public void notifyKeyboardEvent(int modificationKey, char c, int keyCode) {
		styledText.notifyKeyboardEvent(modificationKey, c, keyCode);
	}

	/**
	 * Shows the editor if it is visible.
	 */
	public void show() {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				IEditorPart editor = partReference.getEditor(true);
				partReference.getPage().activate(editor);
			}
		});
	}

	/**
	 * Gets the background color of the widget.
	 *
	 * @return the background color on the widget, or <code>null</code> if the widget is not an instance of
	 *         {@link Control}.
	 * @since 1.3
	 * @see org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot#backgroundColor()
	 */
	public Color backgroundColor() {
		return styledText.backgroundColor();
	}

	/**
	 * Gets the foreground color of the widget.
	 *
	 * @return the foreground color on the widget, or <code>null</code> if the widget is not an instance of
	 *         {@link Control}.
	 * @since 1.3
	 * @see org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot#foregroundColor()
	 */
	public Color foregroundColor() {
		return styledText.foregroundColor();
	}

	/**
	 * Gets the color of the background on the specified line.
	 *
	 * @param lineNumber the line number.
	 * @return the RGB of the line background color of the specified line.
	 * @since 1.3
	 * @see SWTBotStyledText#getLineBackground(int)
	 */
	public RGB getLineBackground(int lineNumber) {
		return styledText.getLineBackground(lineNumber);
	}

	/**
	 * Gets the tooltip of this object's widget.
	 *
	 * @return the tooltip on the widget.
	 * @see org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot#getToolTipText()
	 * @since 1.3
	 */
	public String getToolTipText() {
		return styledText.getToolTipText();
	}

}

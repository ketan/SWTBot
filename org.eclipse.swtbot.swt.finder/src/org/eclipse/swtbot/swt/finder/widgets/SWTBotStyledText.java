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

import java.text.MessageFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.StyledTextContent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.ArrayResult;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.StringResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.Position;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.hamcrest.SelfDescribing;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = StyledText.class, preferredName = "styledText", referenceBy = { ReferenceBy.LABEL, ReferenceBy.TEXT })
public class SWTBotStyledText extends AbstractSWTBot<StyledText> {

	/**
	 * Constructs a new instance of this object.
	 *
	 * @param styledText the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 2.0
	 */
	public SWTBotStyledText(StyledText styledText) throws WidgetNotFoundException {
		this(styledText, null);
	}

	/**
	 * Constructs a new instance of this object.
	 *
	 * @param styledText the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 2.0
	 */
	public SWTBotStyledText(StyledText styledText, SelfDescribing description) throws WidgetNotFoundException {
		super(styledText, description);
	}

	/**
	 * Sets the text into the styled text.
	 *
	 * @param text the text to set.
	 */
	public void setText(final String text) {
		assertEnabled();
		asyncExec(new VoidResult() {
			public void run() {
				widget.setText(text);
			}
		});
	}

	/**
	 * Notifies of the keyboard event.
	 * <p>
	 * FIXME need some work for CTRL|SHIFT + 1 the 1 is to be sent as '!' in this case.
	 * </p>
	 *
	 * @param modificationKey the modification key.
	 * @param c the character.
	 * @see Event#character
	 * @see Event#stateMask
	 */
	public void notifyKeyboardEvent(int modificationKey, char c) {
		notifyKeyboardEvent(modificationKey, c, 0);
	}

	/**
	 * Notifies of keyboard event.
	 *
	 * @param modificationKey the modification key.
	 * @param c the character.
	 * @param keyCode the keycode -- not used.
	 * @see Event#keyCode
	 * @see Event#character
	 * @see Event#stateMask
	 * @deprecated use {@link #notifyKeyboardEvent(int, char)} instead. This api will be removed.
	 */
	public void notifyKeyboardEvent(int modificationKey, char c, int keyCode) {
		log.debug(MessageFormat.format("Enquing keyboard notification: {0}", toString(modificationKey, c))); //$NON-NLS-1$
		assertEnabled();
		setFocus();
		keyboard().pressShortcut(modificationKey, c);
	}

	/**
	 * Converts the given data to a string.
	 *
	 * @param modificationKey The modification key.
	 * @param c The character.
	 * @return The string.
	 */
	private String toString(int modificationKey, char c) {
		String mod = ""; //$NON-NLS-1$
		if ((modificationKey & SWT.CTRL) != 0)
			mod += "SWT.CTRL + "; //$NON-NLS-1$
		if ((modificationKey & SWT.SHIFT) != 0)
			mod += "SWT.SHIFT + "; //$NON-NLS-1$
		int lastPlus = mod.lastIndexOf(" + "); //$NON-NLS-1$
		if (lastPlus == (mod.length() - 3))
			mod = mod.substring(0, mod.length() - 3) + " + "; //$NON-NLS-1$
		mod = mod + c;
		return mod;
	}

	/**
	 * Sets the caret at the specified location.
	 *
	 * @param line the line number, 0 based.
	 * @param column the column number, 0 based.
	 */
	public void navigateTo(final int line, final int column) {
		log.debug(MessageFormat.format("Enquing navigation to location {0}, {1} in {2}", line, column, this)); //$NON-NLS-1$
		assertEnabled();
		setFocus();
		asyncExec(new VoidResult() {
			public void run() {
				log.debug(MessageFormat.format("Navigating to location {0}, {1} in {2}", line, column, widget)); //$NON-NLS-1$
				widget.setSelection(offset(line, column));
			}
		});
	}

	/**
	 * Sets the caret at the specified location.
	 *
	 * @param position the position of the caret.
	 */
	public void navigateTo(Position position) {
		navigateTo(position.line, position.column);
	}

	/**
	 * Gets the current position of the cursor. The returned position will contain a 0-based line and column.
	 *
	 * @return the position of the cursor in the styled text.
	 */
	public Position cursorPosition() {
		return syncExec(new Result<Position>() {
			public Position run() {
				widget.setFocus();
				int offset = widget.getSelectionRange().x;
				int line = widget.getContent().getLineAtOffset(offset);
				int offsetAtLine = widget.getContent().getOffsetAtLine(line);
				int column = offset - offsetAtLine;
				return new Position(line, column);
			}
		});
	}

	/**
	 * Types the text at the given location.
	 *
	 * @param line the line number, 0 based.
	 * @param column the column number, 0 based.
	 * @param text the text to be typed at the specified location
	 * @since 1.0
	 */
	public void typeText(int line, int column, String text) {
		navigateTo(line, column);
		typeText(text);
	}

	/**
	 * Inserts text at the given location.
	 *
	 * @param line the line number, 0 based.
	 * @param column the column number, 0 based.
	 * @param text the text to be inserted at the specified location
	 */
	public void insertText(int line, int column, String text) {
		navigateTo(line, column);
		insertText(text);
	}

	/**
	 * Inserts text at the end.
	 * <p>
	 * FIXME handle line endings
	 * </p>
	 *
	 * @param text the text to be inserted at the location of the caret.
	 */
	public void insertText(final String text) {
		assertEnabled();
		syncExec(new VoidResult() {
			public void run() {
				widget.insert(text);
			}
		});
	}

	/**
	 * Types the text.
	 * <p>
	 * FIXME handle line endings
	 * </p>
	 *
	 * @param text the text to be typed at the location of the caret.
	 * @since 1.0
	 */
	public void typeText(final String text) {
		typeText(text, SWTBotPreferences.TYPE_INTERVAL);
	}

	/**
	 * Types the text.
	 * <p>
	 * FIXME handle line endings
	 * </p>
	 *
	 * @param text the text to be typed at the location of the caret.
	 * @param interval the interval between consecutive key strokes.
	 * @since 1.0
	 */
	public void typeText(final String text, int interval) {
		log.debug(MessageFormat.format("Inserting text:{0} into styledtext{1}", text, this)); //$NON-NLS-1$
		setFocus();
		keyboard().typeText(text);
	}

	/**
	 * Gets the style for the given line.
	 *
	 * @param line the line number, 0 based.
	 * @param column the column number, 0 based.
	 * @return the {@link StyleRange} at the specified location
	 */
	public StyleRange getStyle(final int line, final int column) {
		return syncExec(new Result<StyleRange>() {
			public StyleRange run() {
				return widget.getStyleRangeAtOffset(offset(line, column));
			}
		});
	}

	/**
	 * Gets the offset.
	 *
	 * @param line the line number, 0 based.
	 * @param column the column number, 0 based.
	 * @return the character offset at the specified location in the styledtext.
	 * @see StyledTextContent#getOffsetAtLine(int)
	 */
	protected int offset(final int line, final int column) {
		return widget.getContent().getOffsetAtLine(line) + column;
	}

	/**
	 * Selects the range.
	 *
	 * @param line the line number, 0 based.
	 * @param column the column number, 0 based.
	 * @param length the length of the selection.
	 */
	public void selectRange(final int line, final int column, final int length) {
		assertEnabled();
		asyncExec(new VoidResult() {
			public void run() {
				int offset = offset(line, column);
				widget.setSelection(offset, offset + length);
			}
		});
		notify(SWT.Selection);
	}

	/**
	 * Gets the current selection text.
	 *
	 * @return the selection in the styled text
	 */
	public String getSelection() {
		return syncExec(new StringResult() {
			public String run() {
				return widget.getSelectionText();
			}
		});
	}

	/**
	 * Gets the style information.
	 *
	 * @param line the line number, 0 based.
	 * @param column the column number, 0 based.
	 * @param length the length.
	 * @return the styles in the specified range.
	 * @see StyledText#getStyleRanges(int, int)
	 */
	public StyleRange[] getStyles(final int line, final int column, final int length) {
		return syncExec(new ArrayResult<StyleRange>() {
			public StyleRange[] run() {
				return widget.getStyleRanges(offset(line, column), length);
			}

		});
	}

	/**
	 * Gets the text on the current line.
	 *
	 * @return the text on the current line, without the line delimiters.
	 * @see SWTBotStyledText#getTextOnLine(int)
	 */
	public String getTextOnCurrentLine() {
		final Position currentPosition = cursorPosition();
		final int line = currentPosition.line;
		return getTextOnLine(line);
	}

	/**
	 * Gets the text on the line.
	 * <p>
	 * TODO: throw exception if the line is out of range.
	 * </p>
	 *
	 * @param line the line number, 0 based.
	 * @return the text on the given line number, without the line delimiters.
	 */
	public String getTextOnLine(final int line) {
		return syncExec(new StringResult() {
			public String run() {
				return widget.getContent().getLine(line);
			}
		});
	}

	/**
	 * Checks if this has a bullet on the current line.
	 *
	 * @return <code>true</code> if the styledText has a bullet on the given line, <code>false</code> otherwise.
	 * @see StyledText#getLineBullet(int)
	 */
	public boolean hasBulletOnCurrentLine() {
		return hasBulletOnLine(cursorPosition().line);
	}

	/**
	 * Gets if this has a bullet on the specific line.
	 *
	 * @param line the line number, 0 based.
	 * @return <code>true</code> if the styledText has a bullet on the given line, <code>false</code> otherwise.
	 * @see StyledText#getLineBullet(int)
	 */
	public boolean hasBulletOnLine(final int line) {
		return getBulletOnLine(line) != null;
	}

	/**
	 * Gets the bullet on the current line.
	 *
	 * @return the bullet on the current line.
	 * @see StyledText#getLineBullet(int)
	 */
	public Bullet getBulletOnCurrentLine() {
		return getBulletOnLine(cursorPosition().line);
	}

	/**
	 * Gets the bullet on the given line.
	 *
	 * @param line the line number, 0 based.
	 * @return the bullet on the given line.
	 * @see StyledText#getLineBullet(int)
	 */
	public Bullet getBulletOnLine(final int line) {
		return syncExec(new Result<Bullet>() {
			public Bullet run() {
				return widget.getLineBullet(line);
			}
		});
	}

	/**
	 * Selects the text on the specified line.
	 *
	 * @param line the line number, 0 based.
	 * @since 1.1
	 */
	public void selectLine(int line) {
		selectRange(line, 0, getTextOnLine(line).length());
	}

	/**
	 * Selects the text on the current line.
	 *
	 * @since 1.1
	 */
	public void selectCurrentLine() {
		selectLine(cursorPosition().line);
	}

	/**
	 * Gets the color of the background on the specified line.
	 *
	 * @param line the line number, 0 based.
	 * @return the RGB of the line background color of the specified line.
	 * @since 1.3
	 */
	public RGB getLineBackground(final int line) {
		return syncExec(new Result<RGB>() {
			public RGB run() {
				return widget.getLineBackground(line).getRGB();
			}
		});
	}
}

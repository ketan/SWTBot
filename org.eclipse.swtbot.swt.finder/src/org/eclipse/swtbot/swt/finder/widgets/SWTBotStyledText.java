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
import org.eclipse.swtbot.swt.finder.results.ArrayResult;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.StringResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.Position;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotStyledText.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
@SWTBotWidget(clasz = StyledText.class, preferredName = "styledText", referenceBy = { ReferenceBy.LABEL, ReferenceBy.TEXT })
public class SWTBotStyledText extends AbstractSWTBot<StyledText> {

	private static final int	TYPE_INTERVAL	= 50;

	/**
	 * Constructs a new instance of this object.
	 *
	 * @param styledText the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 2.0
	 */
	public SWTBotStyledText(StyledText styledText) throws WidgetNotFoundException {
		super(styledText);
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
		setFocus();
		notifyKeyboardEvent(modificationKey, c, 0);
	}

	/**
	 * Notifies of keyboard event.
	 *
	 * @param modificationKey the modification key.
	 * @param c the character.
	 * @param keyCode the keycode.
	 * @see Event#keyCode
	 * @see Event#character
	 * @see Event#stateMask
	 */
	public void notifyKeyboardEvent(int modificationKey, char c, int keyCode) {
		log.debug(MessageFormat.format("Enquing keyboard notification: {0}", toString(modificationKey, c)));
		assertEnabled();
		notify(SWT.KeyDown, keyEvent(modificationKey, c, keyCode));
		notify(SWT.KeyUp, keyEvent(modificationKey, c, keyCode));
	}

	/**
	 * Converts the given data to a string.
	 *
	 * @param modificationKey The modification key.
	 * @param c The character.
	 * @return The string.
	 */
	private String toString(int modificationKey, char c) {
		String mod = "";
		if ((modificationKey & SWT.CTRL) != 0)
			mod += "SWT.CTRL + ";
		if ((modificationKey & SWT.SHIFT) != 0)
			mod += "SWT.SHIFT + ";
		int lastPlus = mod.lastIndexOf(" + ");
		if (lastPlus == (mod.length() - 3))
			mod = mod.substring(0, mod.length() - 3) + " + ";
		mod = mod + c;
		return mod;
	}

	/**
	 * Gets the key event.
	 *
	 * @param c the character.
	 * @param modificationKey the modification key.
	 * @return a key event with the specified keys.
	 * @see Event#character
	 * @see Event#stateMask
	 */
	protected Event keyEvent(int modificationKey, char c) {
		return keyEvent(modificationKey, c, c);
	}

	/**
	 * Gets the key event.
	 *
	 * @param c the character.
	 * @param modificationKey the modification key.
	 * @param keyCode the keycode.
	 * @return a key event with the specified keys.
	 * @see Event#keyCode
	 * @see Event#character
	 * @see Event#stateMask
	 */
	protected Event keyEvent(int modificationKey, char c, int keyCode) {
		Event keyEvent = createEvent();
		keyEvent.stateMask = modificationKey;
		keyEvent.character = c;
		keyEvent.keyCode = keyCode;
		return keyEvent;
	}

	/**
	 * Sets the caret at the specified location.
	 *
	 * @param line the line numnber.
	 * @param column the column number.
	 */
	public void navigateTo(final int line, final int column) {
		log.debug(MessageFormat.format("Enquing navigation to location {0}, {1} in {2}", line, column, this));
		assertEnabled();
		setFocus();
		asyncExec(new VoidResult() {
			public void run() {
				log.debug(MessageFormat.format("Navigating to location {0}, {1} in {2}", line, column, widget));
				widget.setSelection(offset(line, column));
			}
		});
	}

	/**
	 * Gets the current position of the cursor.
	 *
	 * @return the position of the cursor in the styled text.
	 */
	public Position cursorPosition() {
		return syncExec(new Result<Position>() {
			public Position run() {
				widget.setFocus();
				int offset = widget.getSelectionRange().x;
				int lineNumber = widget.getContent().getLineAtOffset(offset);
				int offsetAtLine = widget.getContent().getOffsetAtLine(lineNumber);
				int columnNumber = offset - offsetAtLine;
				return new Position(lineNumber, columnNumber);
			}
		});
	}

	/**
	 * Types the text at the given location.
	 *
	 * @param line the line number.
	 * @param column the column number.
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
	 * @param line the line number.
	 * @param column the column number.
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
		typeText(text, TYPE_INTERVAL);
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
		log.debug(MessageFormat.format("Inserting text:{0} into styledtext{1}", text, this));
		setFocus();
		for (int i = 0; i < text.length(); i++) {
			notifyKeyboardEvent(SWT.NONE, text.charAt(i));
			sleep(interval);
		}
	}

	/**
	 * Gets the style for the given line.
	 *
	 * @param line the line number.
	 * @param column the column number.
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
	 * @param line the line number.
	 * @param column the column number.
	 * @return the character offset at the specified location in the styledtext.
	 * @see StyledTextContent#getOffsetAtLine(int)
	 */
	protected int offset(final int line, final int column) {
		return widget.getContent().getOffsetAtLine(line) + column;
	}

	/**
	 * Selects the range.
	 *
	 * @param line the line number.
	 * @param column the column number.
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
	 * @param line the line number.
	 * @param column the column number.
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
	 * @param line the line number.
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
	 * @param line the line number.
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
	 * @param line the line number.
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
	 * @param line the line number.
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
	 * @param lineNumber the line number.
	 * @return the RGB of the line background color of the specified line.
	 * @since 1.3
	 */
	public RGB getLineBackground(final int lineNumber) {
		return syncExec(new Result<RGB>() {
			public RGB run() {
				return widget.getLineBackground(lineNumber).getRGB();
			}
		});
	}
}

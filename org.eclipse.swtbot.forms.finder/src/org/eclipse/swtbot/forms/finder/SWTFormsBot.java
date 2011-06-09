// Generated source. DO NOT MODIFY.
// To add new widgets, please see README file in the generator plugin.
package org.eclipse.swtbot.forms.finder;


import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.forms.finder.widgets.SWTBotHyperlink;
import org.eclipse.swtbot.forms.finder.widgets.SWTBotImageHyperlink;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.ChildrenControlFinder;
import org.eclipse.swtbot.swt.finder.finders.ControlFinder;
import org.eclipse.swtbot.swt.finder.finders.Finder;
import org.eclipse.swtbot.swt.finder.finders.MenuFinder;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.hamcrest.Matcher;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.inGroup;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withId;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;


/**
 * SWTFormsBot is a {@link SWTBot} with capabilities for testing eclipse forms.
 *
 * @see {@link SWTBot} - SWTBot for usage examples.
 */
public class SWTFormsBot extends SWTBot {

	/**
	 * Constructs a bot.
	 */
	public SWTFormsBot() {
		this(new ControlFinder(), new MenuFinder());
	}

	/**
	 * Constructs a bot that will match the contents of the given parentWidget.
	 * 
	 * @param parent the parent
	 */
	public SWTFormsBot(Widget parent) {
		this(new ChildrenControlFinder(parent), new MenuFinder());
	}
	/**
	 * Constructs an instance of the bot using the given control finder and menu finder.
	 * 
	 * @param controlFinder the {@link ControlFinder} used to identify and find controls.
	 * @param menuFinder the {@link MenuFinder} used to find menu items.
	 */
	public SWTFormsBot(ControlFinder controlFinder, MenuFinder menuFinder) {
		this(new Finder(controlFinder, menuFinder));
	}

	/**
	 * Constructs a bot with the given finder.
	 * 
	 * @param finder the finder.
	 */
	public SWTFormsBot(Finder finder) {
		super(finder);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotHyperlink} with the specified <code>mnemonicText</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	public SWTBotHyperlink hyperlink(String mnemonicText) {
		return hyperlink(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotHyperlink} with the specified <code>mnemonicText</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public SWTBotHyperlink hyperlink(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(Hyperlink.class), withMnemonic(mnemonicText));
		return new SWTBotHyperlink((Hyperlink) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotHyperlink} with the specified <code>key/value</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	public SWTBotHyperlink hyperlinkWithId(String key, String value) {
		return hyperlinkWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotHyperlink} with the specified <code>key/value</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public SWTBotHyperlink hyperlinkWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(Hyperlink.class), withId(key, value));
		return new SWTBotHyperlink((Hyperlink) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotHyperlink} with the specified <code>value</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	public SWTBotHyperlink hyperlinkWithId(String value) {
		return hyperlinkWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotHyperlink} with the specified <code>value</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public SWTBotHyperlink hyperlinkWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(Hyperlink.class), withId(value));
		return new SWTBotHyperlink((Hyperlink) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotHyperlink} with the specified <code>inGroup</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	public SWTBotHyperlink hyperlinkInGroup(String inGroup) {
		return hyperlinkInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotHyperlink} with the specified <code>inGroup</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public SWTBotHyperlink hyperlinkInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Hyperlink.class), inGroup(inGroup));
		return new SWTBotHyperlink((Hyperlink) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotHyperlink} with the specified <code>none</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	public SWTBotHyperlink hyperlink() {
		return hyperlink(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotHyperlink} with the specified <code>none</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public SWTBotHyperlink hyperlink(int index) {
		Matcher matcher = allOf(widgetOfType(Hyperlink.class));
		return new SWTBotHyperlink((Hyperlink) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotHyperlink} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	public SWTBotHyperlink hyperlinkInGroup(String mnemonicText, String inGroup) {
		return hyperlinkInGroup(mnemonicText, inGroup, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotHyperlink} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public SWTBotHyperlink hyperlinkInGroup(String mnemonicText, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Hyperlink.class), withMnemonic(mnemonicText), inGroup(inGroup));
		return new SWTBotHyperlink((Hyperlink) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>mnemonicText</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	public SWTBotImageHyperlink imageHyperlink(String mnemonicText) {
		return imageHyperlink(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>mnemonicText</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public SWTBotImageHyperlink imageHyperlink(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(ImageHyperlink.class), withMnemonic(mnemonicText));
		return new SWTBotImageHyperlink((ImageHyperlink) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>key/value</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	public SWTBotImageHyperlink imageHyperlinkWithId(String key, String value) {
		return imageHyperlinkWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>key/value</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public SWTBotImageHyperlink imageHyperlinkWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(ImageHyperlink.class), withId(key, value));
		return new SWTBotImageHyperlink((ImageHyperlink) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>value</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	public SWTBotImageHyperlink imageHyperlinkWithId(String value) {
		return imageHyperlinkWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>value</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public SWTBotImageHyperlink imageHyperlinkWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(ImageHyperlink.class), withId(value));
		return new SWTBotImageHyperlink((ImageHyperlink) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>inGroup</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	public SWTBotImageHyperlink imageHyperlinkInGroup(String inGroup) {
		return imageHyperlinkInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>inGroup</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public SWTBotImageHyperlink imageHyperlinkInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(ImageHyperlink.class), inGroup(inGroup));
		return new SWTBotImageHyperlink((ImageHyperlink) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>none</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	public SWTBotImageHyperlink imageHyperlink() {
		return imageHyperlink(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>none</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public SWTBotImageHyperlink imageHyperlink(int index) {
		Matcher matcher = allOf(widgetOfType(ImageHyperlink.class));
		return new SWTBotImageHyperlink((ImageHyperlink) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	public SWTBotImageHyperlink imageHyperlinkInGroup(String mnemonicText, String inGroup) {
		return imageHyperlinkInGroup(mnemonicText, inGroup, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 * @throws WidgetNotFoundException if the widget is not found or is disposed.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public SWTBotImageHyperlink imageHyperlinkInGroup(String mnemonicText, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(ImageHyperlink.class), withMnemonic(mnemonicText), inGroup(inGroup));
		return new SWTBotImageHyperlink((ImageHyperlink) widget(matcher, index), matcher);
	}



}

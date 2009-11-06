// Generated source.
package org.eclipse.swtbot.swt.finder;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.finders.ChildrenControlFinder;
import org.eclipse.swtbot.swt.finder.finders.ControlFinder;
import org.eclipse.swtbot.swt.finder.finders.Finder;
import org.eclipse.swtbot.swt.finder.finders.MenuFinder;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCLabel;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCTabItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotDateTime;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotLabel;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotLink;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotList;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotRadio;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotSlider;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotSpinner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTabItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToggleButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarPushButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarToggleButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarDropDownButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarRadioButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.hamcrest.Matcher;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.inGroup;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withId;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withLabel;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withStyle;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withTooltip;


/**
 * This class contains convenience API to find widgets in SWTBot.
 * Most users would start off as follows: 
 * 
 * <pre>
 *    SWTBot bot = new SWTBot();
 *    
 *    bot.button(&quot;hello world&quot;).click();
 *    
 *    // in case you have two edit buttons in two different groups
 *    // say an edit button in the &quot;Address&quot; section,
 *    // and another in &quot;Bank Account&quot; section, you can do the following
 *    // to click on the &quot;Edit&quot; button on the &quot;Bank Account&quot; section.
 *    // This is the recommended way to use SWTBot, instead of finding widgets based on its index.
 *    bot.buttonInGroup(&quot;Edit&quot;, &quot;Bank Account&quot;).click();
 * </pre>
 * 
 * For finding widgets using custom matchers:
 * 
 * <pre>
 *    SWTBot bot = new SWTBot();
 *    //
 *    // find a button within the currently active shell:
 *    //
 *    SWTBotButton button = new SWTBotButton((Button) bot.widget(aMatcher)); // or
 *    SWTBotButton button = new SWTBotButton((Button)bot.widget(aMatcher, 3)); // for the 4th widget
 *    //
 *    // to find a button within a particular parent composite:
 *    //
 *    SWTBotButton button = new SWTBotButton((Button) bot.widget(aMatcher, parentComposite)); //or
 *    SWTBotButton button = new SWTBotButton((Button) bot.widget(aMatcher, parentComposite, 3)); //for the 4th widget
 * </pre>
 *
 * @version $Id$
 */
public class SWTBot extends SWTBotFactory {

	/**
	 * Constructs a bot.
	 */
	public SWTBot() {
		this(new ControlFinder(), new MenuFinder());
	}

	/**
	 * Constructs a bot that will match the contents of the given parentWidget.
	 * 
	 * @param parent the parent
	 */
	public SWTBot(Widget parent) {
		this(new ChildrenControlFinder(parent), new MenuFinder());
	}
	/**
	 * Constructs an instance of the bot using the given control finder and menu finder.
	 * 
	 * @param controlFinder the {@link ControlFinder} used to identify and find controls.
	 * @param menuFinder the {@link MenuFinder} used to find menu items.
	 */
	public SWTBot(ControlFinder controlFinder, MenuFinder menuFinder) {
		this(new Finder(controlFinder, menuFinder));
	}

	/**
	 * Constructs a bot with the given finder.
	 * 
	 * @param finder the finder.
	 */
	public SWTBot(Finder finder) {
		super(finder);
	}

	/**
	 * @param label the label on the widget.
	 * @return a {@link SWTBotButton} with the specified <code>label</code>.
	 */
	public SWTBotButton buttonWithLabel(String label) {
		return buttonWithLabel(label, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotButton} with the specified <code>label</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotButton buttonWithLabel(String label, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withLabel(label), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotButton} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotButton button(String mnemonicText) {
		return button(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotButton} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotButton button(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withMnemonic(mnemonicText), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @return a {@link SWTBotButton} with the specified <code>tooltip</code>.
	 */
	public SWTBotButton buttonWithTooltip(String tooltip) {
		return buttonWithTooltip(tooltip, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotButton} with the specified <code>tooltip</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotButton buttonWithTooltip(String tooltip, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withTooltip(tooltip), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotButton} with the specified <code>key/value</code>.
	 */
	public SWTBotButton buttonWithId(String key, String value) {
		return buttonWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotButton} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotButton buttonWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withId(key, value), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotButton} with the specified <code>value</code>.
	 */
	public SWTBotButton buttonWithId(String value) {
		return buttonWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotButton} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotButton buttonWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withId(value), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotButton} with the specified <code>inGroup</code>.
	 */
	public SWTBotButton buttonInGroup(String inGroup) {
		return buttonInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotButton} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotButton buttonInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), inGroup(inGroup), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotButton} with the specified <code>none</code>.
	 */
	public SWTBotButton button() {
		return button(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotButton} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotButton button(int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotButton} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotButton buttonWithLabelInGroup(String label, String inGroup) {
		return buttonWithLabelInGroup(label, inGroup, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotButton} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotButton buttonWithLabelInGroup(String label, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withLabel(label), inGroup(inGroup), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotButton} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotButton buttonInGroup(String mnemonicText, String inGroup) {
		return buttonInGroup(mnemonicText, inGroup, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotButton} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotButton buttonInGroup(String mnemonicText, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withMnemonic(mnemonicText), inGroup(inGroup), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotButton} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotButton buttonWithTooltipInGroup(String tooltip, String inGroup) {
		return buttonWithTooltipInGroup(tooltip, inGroup, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotButton} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotButton buttonWithTooltipInGroup(String tooltip, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withTooltip(tooltip), inGroup(inGroup), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>label</code>.
	 */
	public SWTBotCheckBox checkBoxWithLabel(String label) {
		return checkBoxWithLabel(label, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>label</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCheckBox checkBoxWithLabel(String label, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withLabel(label), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotCheckBox((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotCheckBox checkBox(String mnemonicText) {
		return checkBox(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCheckBox checkBox(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withMnemonic(mnemonicText), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotCheckBox((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>tooltip</code>.
	 */
	public SWTBotCheckBox checkBoxWithTooltip(String tooltip) {
		return checkBoxWithTooltip(tooltip, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>tooltip</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCheckBox checkBoxWithTooltip(String tooltip, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withTooltip(tooltip), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotCheckBox((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotCheckBox} with the specified <code>key/value</code>.
	 */
	public SWTBotCheckBox checkBoxWithId(String key, String value) {
		return checkBoxWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCheckBox checkBoxWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withId(key, value), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotCheckBox((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotCheckBox} with the specified <code>value</code>.
	 */
	public SWTBotCheckBox checkBoxWithId(String value) {
		return checkBoxWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCheckBox checkBoxWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withId(value), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotCheckBox((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>inGroup</code>.
	 */
	public SWTBotCheckBox checkBoxInGroup(String inGroup) {
		return checkBoxInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCheckBox checkBoxInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), inGroup(inGroup), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotCheckBox((Button) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotCheckBox} with the specified <code>none</code>.
	 */
	public SWTBotCheckBox checkBox() {
		return checkBox(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCheckBox checkBox(int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotCheckBox((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotCheckBox checkBoxWithLabelInGroup(String label, String inGroup) {
		return checkBoxWithLabelInGroup(label, inGroup, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCheckBox checkBoxWithLabelInGroup(String label, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withLabel(label), inGroup(inGroup), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotCheckBox((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotCheckBox checkBoxInGroup(String mnemonicText, String inGroup) {
		return checkBoxInGroup(mnemonicText, inGroup, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCheckBox checkBoxInGroup(String mnemonicText, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withMnemonic(mnemonicText), inGroup(inGroup), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotCheckBox((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotCheckBox checkBoxWithTooltipInGroup(String tooltip, String inGroup) {
		return checkBoxWithTooltipInGroup(tooltip, inGroup, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCheckBox} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCheckBox checkBoxWithTooltipInGroup(String tooltip, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withTooltip(tooltip), inGroup(inGroup), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotCheckBox((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>label</code>.
	 */
	public SWTBotRadio radioWithLabel(String label) {
		return radioWithLabel(label, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>label</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotRadio radioWithLabel(String label, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withLabel(label), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotRadio((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotRadio radio(String mnemonicText) {
		return radio(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotRadio radio(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withMnemonic(mnemonicText), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotRadio((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>tooltip</code>.
	 */
	public SWTBotRadio radioWithTooltip(String tooltip) {
		return radioWithTooltip(tooltip, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>tooltip</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotRadio radioWithTooltip(String tooltip, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withTooltip(tooltip), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotRadio((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotRadio} with the specified <code>key/value</code>.
	 */
	public SWTBotRadio radioWithId(String key, String value) {
		return radioWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotRadio radioWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withId(key, value), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotRadio((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotRadio} with the specified <code>value</code>.
	 */
	public SWTBotRadio radioWithId(String value) {
		return radioWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotRadio radioWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withId(value), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotRadio((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>inGroup</code>.
	 */
	public SWTBotRadio radioInGroup(String inGroup) {
		return radioInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotRadio radioInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), inGroup(inGroup), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotRadio((Button) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotRadio} with the specified <code>none</code>.
	 */
	public SWTBotRadio radio() {
		return radio(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotRadio radio(int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotRadio((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotRadio radioWithLabelInGroup(String label, String inGroup) {
		return radioWithLabelInGroup(label, inGroup, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotRadio radioWithLabelInGroup(String label, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withLabel(label), inGroup(inGroup), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotRadio((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotRadio radioInGroup(String mnemonicText, String inGroup) {
		return radioInGroup(mnemonicText, inGroup, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotRadio radioInGroup(String mnemonicText, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withMnemonic(mnemonicText), inGroup(inGroup), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotRadio((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotRadio radioWithTooltipInGroup(String tooltip, String inGroup) {
		return radioWithTooltipInGroup(tooltip, inGroup, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotRadio} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotRadio radioWithTooltipInGroup(String tooltip, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withTooltip(tooltip), inGroup(inGroup), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotRadio((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>label</code>.
	 */
	public SWTBotToggleButton toggleButtonWithLabel(String label) {
		return toggleButtonWithLabel(label, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>label</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToggleButton toggleButtonWithLabel(String label, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withLabel(label), withStyle(SWT.TOGGLE, "SWT.TOGGLE"));
		return new SWTBotToggleButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotToggleButton toggleButton(String mnemonicText) {
		return toggleButton(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToggleButton toggleButton(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withMnemonic(mnemonicText), withStyle(SWT.TOGGLE, "SWT.TOGGLE"));
		return new SWTBotToggleButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>tooltip</code>.
	 */
	public SWTBotToggleButton toggleButtonWithTooltip(String tooltip) {
		return toggleButtonWithTooltip(tooltip, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>tooltip</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToggleButton toggleButtonWithTooltip(String tooltip, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withTooltip(tooltip), withStyle(SWT.TOGGLE, "SWT.TOGGLE"));
		return new SWTBotToggleButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotToggleButton} with the specified <code>key/value</code>.
	 */
	public SWTBotToggleButton toggleButtonWithId(String key, String value) {
		return toggleButtonWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToggleButton toggleButtonWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withId(key, value), withStyle(SWT.TOGGLE, "SWT.TOGGLE"));
		return new SWTBotToggleButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotToggleButton} with the specified <code>value</code>.
	 */
	public SWTBotToggleButton toggleButtonWithId(String value) {
		return toggleButtonWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToggleButton toggleButtonWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withId(value), withStyle(SWT.TOGGLE, "SWT.TOGGLE"));
		return new SWTBotToggleButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>inGroup</code>.
	 */
	public SWTBotToggleButton toggleButtonInGroup(String inGroup) {
		return toggleButtonInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToggleButton toggleButtonInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), inGroup(inGroup), withStyle(SWT.TOGGLE, "SWT.TOGGLE"));
		return new SWTBotToggleButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotToggleButton} with the specified <code>none</code>.
	 */
	public SWTBotToggleButton toggleButton() {
		return toggleButton(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToggleButton toggleButton(int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withStyle(SWT.TOGGLE, "SWT.TOGGLE"));
		return new SWTBotToggleButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotToggleButton toggleButtonWithLabelInGroup(String label, String inGroup) {
		return toggleButtonWithLabelInGroup(label, inGroup, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToggleButton toggleButtonWithLabelInGroup(String label, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withLabel(label), inGroup(inGroup), withStyle(SWT.TOGGLE, "SWT.TOGGLE"));
		return new SWTBotToggleButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotToggleButton toggleButtonInGroup(String mnemonicText, String inGroup) {
		return toggleButtonInGroup(mnemonicText, inGroup, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToggleButton toggleButtonInGroup(String mnemonicText, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withMnemonic(mnemonicText), inGroup(inGroup), withStyle(SWT.TOGGLE, "SWT.TOGGLE"));
		return new SWTBotToggleButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotToggleButton toggleButtonWithTooltipInGroup(String tooltip, String inGroup) {
		return toggleButtonWithTooltipInGroup(tooltip, inGroup, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToggleButton} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToggleButton toggleButtonWithTooltipInGroup(String tooltip, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Button.class), withTooltip(tooltip), inGroup(inGroup), withStyle(SWT.TOGGLE, "SWT.TOGGLE"));
		return new SWTBotToggleButton((Button) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @return a {@link SWTBotTree} with the specified <code>label</code>.
	 */
	public SWTBotTree treeWithLabel(String label) {
		return treeWithLabel(label, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTree} with the specified <code>label</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTree treeWithLabel(String label, int index) {
		Matcher matcher = allOf(widgetOfType(Tree.class), withLabel(label));
		return new SWTBotTree((Tree) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotTree} with the specified <code>key/value</code>.
	 */
	public SWTBotTree treeWithId(String key, String value) {
		return treeWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTree} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTree treeWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(Tree.class), withId(key, value));
		return new SWTBotTree((Tree) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotTree} with the specified <code>value</code>.
	 */
	public SWTBotTree treeWithId(String value) {
		return treeWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTree} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTree treeWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(Tree.class), withId(value));
		return new SWTBotTree((Tree) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotTree} with the specified <code>inGroup</code>.
	 */
	public SWTBotTree treeInGroup(String inGroup) {
		return treeInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTree} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTree treeInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Tree.class), inGroup(inGroup));
		return new SWTBotTree((Tree) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotTree} with the specified <code>none</code>.
	 */
	public SWTBotTree tree() {
		return tree(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTree} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTree tree(int index) {
		Matcher matcher = allOf(widgetOfType(Tree.class));
		return new SWTBotTree((Tree) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotTree} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotTree treeWithLabelInGroup(String label, String inGroup) {
		return treeWithLabelInGroup(label, inGroup, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTree} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTree treeWithLabelInGroup(String label, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Tree.class), withLabel(label), inGroup(inGroup));
		return new SWTBotTree((Tree) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @return a {@link SWTBotText} with the specified <code>label</code>.
	 */
	public SWTBotText textWithLabel(String label) {
		return textWithLabel(label, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotText} with the specified <code>label</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotText textWithLabel(String label, int index) {
		Matcher matcher = allOf(widgetOfType(Text.class), withLabel(label));
		return new SWTBotText((Text) widget(matcher, index), matcher);
	}

	/**
	 * @param text the text on the widget.
	 * @return a {@link SWTBotText} with the specified <code>text</code>.
	 */
	public SWTBotText text(String text) {
		return text(text, 0);
	}

	/**
	 * @param text the text on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotText} with the specified <code>text</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotText text(String text, int index) {
		Matcher matcher = allOf(widgetOfType(Text.class), withText(text));
		return new SWTBotText((Text) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @return a {@link SWTBotText} with the specified <code>tooltip</code>.
	 */
	public SWTBotText textWithTooltip(String tooltip) {
		return textWithTooltip(tooltip, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotText} with the specified <code>tooltip</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotText textWithTooltip(String tooltip, int index) {
		Matcher matcher = allOf(widgetOfType(Text.class), withTooltip(tooltip));
		return new SWTBotText((Text) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotText} with the specified <code>key/value</code>.
	 */
	public SWTBotText textWithId(String key, String value) {
		return textWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotText} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotText textWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(Text.class), withId(key, value));
		return new SWTBotText((Text) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotText} with the specified <code>value</code>.
	 */
	public SWTBotText textWithId(String value) {
		return textWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotText} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotText textWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(Text.class), withId(value));
		return new SWTBotText((Text) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotText} with the specified <code>inGroup</code>.
	 */
	public SWTBotText textInGroup(String inGroup) {
		return textInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotText} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotText textInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Text.class), inGroup(inGroup));
		return new SWTBotText((Text) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotText} with the specified <code>none</code>.
	 */
	public SWTBotText text() {
		return text(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotText} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotText text(int index) {
		Matcher matcher = allOf(widgetOfType(Text.class));
		return new SWTBotText((Text) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotText} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotText textWithLabelInGroup(String label, String inGroup) {
		return textWithLabelInGroup(label, inGroup, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotText} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotText textWithLabelInGroup(String label, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Text.class), withLabel(label), inGroup(inGroup));
		return new SWTBotText((Text) widget(matcher, index), matcher);
	}

	/**
	 * @param text the text on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotText} with the specified <code>text</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotText textInGroup(String text, String inGroup) {
		return textInGroup(text, inGroup, 0);
	}

	/**
	 * @param text the text on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotText} with the specified <code>text</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotText textInGroup(String text, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Text.class), withText(text), inGroup(inGroup));
		return new SWTBotText((Text) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotText} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotText textWithTooltipInGroup(String tooltip, String inGroup) {
		return textWithTooltipInGroup(tooltip, inGroup, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotText} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotText textWithTooltipInGroup(String tooltip, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Text.class), withTooltip(tooltip), inGroup(inGroup));
		return new SWTBotText((Text) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @return a {@link SWTBotCombo} with the specified <code>label</code>.
	 */
	public SWTBotCombo comboBoxWithLabel(String label) {
		return comboBoxWithLabel(label, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCombo} with the specified <code>label</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCombo comboBoxWithLabel(String label, int index) {
		Matcher matcher = allOf(widgetOfType(Combo.class), withLabel(label));
		return new SWTBotCombo((Combo) widget(matcher, index), matcher);
	}

	/**
	 * @param text the text on the widget.
	 * @return a {@link SWTBotCombo} with the specified <code>text</code>.
	 */
	public SWTBotCombo comboBox(String text) {
		return comboBox(text, 0);
	}

	/**
	 * @param text the text on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCombo} with the specified <code>text</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCombo comboBox(String text, int index) {
		Matcher matcher = allOf(widgetOfType(Combo.class), withText(text));
		return new SWTBotCombo((Combo) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotCombo} with the specified <code>key/value</code>.
	 */
	public SWTBotCombo comboBoxWithId(String key, String value) {
		return comboBoxWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCombo} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCombo comboBoxWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(Combo.class), withId(key, value));
		return new SWTBotCombo((Combo) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotCombo} with the specified <code>value</code>.
	 */
	public SWTBotCombo comboBoxWithId(String value) {
		return comboBoxWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCombo} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCombo comboBoxWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(Combo.class), withId(value));
		return new SWTBotCombo((Combo) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotCombo} with the specified <code>inGroup</code>.
	 */
	public SWTBotCombo comboBoxInGroup(String inGroup) {
		return comboBoxInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCombo} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCombo comboBoxInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Combo.class), inGroup(inGroup));
		return new SWTBotCombo((Combo) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotCombo} with the specified <code>none</code>.
	 */
	public SWTBotCombo comboBox() {
		return comboBox(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCombo} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCombo comboBox(int index) {
		Matcher matcher = allOf(widgetOfType(Combo.class));
		return new SWTBotCombo((Combo) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotCombo} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotCombo comboBoxWithLabelInGroup(String label, String inGroup) {
		return comboBoxWithLabelInGroup(label, inGroup, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCombo} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCombo comboBoxWithLabelInGroup(String label, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Combo.class), withLabel(label), inGroup(inGroup));
		return new SWTBotCombo((Combo) widget(matcher, index), matcher);
	}

	/**
	 * @param text the text on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotCombo} with the specified <code>text</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotCombo comboBoxInGroup(String text, String inGroup) {
		return comboBoxInGroup(text, inGroup, 0);
	}

	/**
	 * @param text the text on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCombo} with the specified <code>text</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCombo comboBoxInGroup(String text, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Combo.class), withText(text), inGroup(inGroup));
		return new SWTBotCombo((Combo) widget(matcher, index), matcher);
	}

	/**
	 * @param text the text on the widget.
	 * @return a {@link SWTBotCCombo} with the specified <code>text</code>.
	 */
	public SWTBotCCombo ccomboBox(String text) {
		return ccomboBox(text, 0);
	}

	/**
	 * @param text the text on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCCombo} with the specified <code>text</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCCombo ccomboBox(String text, int index) {
		Matcher matcher = allOf(widgetOfType(CCombo.class), withText(text));
		return new SWTBotCCombo((CCombo) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @return a {@link SWTBotCCombo} with the specified <code>label</code>.
	 */
	public SWTBotCCombo ccomboBoxWithLabel(String label) {
		return ccomboBoxWithLabel(label, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCCombo} with the specified <code>label</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCCombo ccomboBoxWithLabel(String label, int index) {
		Matcher matcher = allOf(widgetOfType(CCombo.class), withLabel(label));
		return new SWTBotCCombo((CCombo) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotCCombo} with the specified <code>key/value</code>.
	 */
	public SWTBotCCombo ccomboBoxWithId(String key, String value) {
		return ccomboBoxWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCCombo} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCCombo ccomboBoxWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(CCombo.class), withId(key, value));
		return new SWTBotCCombo((CCombo) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotCCombo} with the specified <code>value</code>.
	 */
	public SWTBotCCombo ccomboBoxWithId(String value) {
		return ccomboBoxWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCCombo} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCCombo ccomboBoxWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(CCombo.class), withId(value));
		return new SWTBotCCombo((CCombo) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotCCombo} with the specified <code>inGroup</code>.
	 */
	public SWTBotCCombo ccomboBoxInGroup(String inGroup) {
		return ccomboBoxInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCCombo} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCCombo ccomboBoxInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(CCombo.class), inGroup(inGroup));
		return new SWTBotCCombo((CCombo) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotCCombo} with the specified <code>none</code>.
	 */
	public SWTBotCCombo ccomboBox() {
		return ccomboBox(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCCombo} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCCombo ccomboBox(int index) {
		Matcher matcher = allOf(widgetOfType(CCombo.class));
		return new SWTBotCCombo((CCombo) widget(matcher, index), matcher);
	}

	/**
	 * @param text the text on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotCCombo} with the specified <code>text</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotCCombo ccomboBoxInGroup(String text, String inGroup) {
		return ccomboBoxInGroup(text, inGroup, 0);
	}

	/**
	 * @param text the text on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCCombo} with the specified <code>text</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCCombo ccomboBoxInGroup(String text, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(CCombo.class), withText(text), inGroup(inGroup));
		return new SWTBotCCombo((CCombo) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotCCombo} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotCCombo ccomboBoxWithLabelInGroup(String label, String inGroup) {
		return ccomboBoxWithLabelInGroup(label, inGroup, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCCombo} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCCombo ccomboBoxWithLabelInGroup(String label, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(CCombo.class), withLabel(label), inGroup(inGroup));
		return new SWTBotCCombo((CCombo) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotCLabel} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotCLabel clabel(String mnemonicText) {
		return clabel(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCLabel} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCLabel clabel(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(CLabel.class), withMnemonic(mnemonicText));
		return new SWTBotCLabel((CLabel) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotCLabel} with the specified <code>key/value</code>.
	 */
	public SWTBotCLabel clabelWithId(String key, String value) {
		return clabelWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCLabel} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCLabel clabelWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(CLabel.class), withId(key, value));
		return new SWTBotCLabel((CLabel) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotCLabel} with the specified <code>value</code>.
	 */
	public SWTBotCLabel clabelWithId(String value) {
		return clabelWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCLabel} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCLabel clabelWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(CLabel.class), withId(value));
		return new SWTBotCLabel((CLabel) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotCLabel} with the specified <code>inGroup</code>.
	 */
	public SWTBotCLabel clabelInGroup(String inGroup) {
		return clabelInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCLabel} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCLabel clabelInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(CLabel.class), inGroup(inGroup));
		return new SWTBotCLabel((CLabel) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotCLabel} with the specified <code>none</code>.
	 */
	public SWTBotCLabel clabel() {
		return clabel(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCLabel} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCLabel clabel(int index) {
		Matcher matcher = allOf(widgetOfType(CLabel.class));
		return new SWTBotCLabel((CLabel) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotCLabel} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotCLabel clabelInGroup(String mnemonicText, String inGroup) {
		return clabelInGroup(mnemonicText, inGroup, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCLabel} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCLabel clabelInGroup(String mnemonicText, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(CLabel.class), withMnemonic(mnemonicText), inGroup(inGroup));
		return new SWTBotCLabel((CLabel) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotLabel} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotLabel label(String mnemonicText) {
		return label(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotLabel} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotLabel label(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(Label.class), withMnemonic(mnemonicText));
		return new SWTBotLabel((Label) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotLabel} with the specified <code>key/value</code>.
	 */
	public SWTBotLabel labelWithId(String key, String value) {
		return labelWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotLabel} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotLabel labelWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(Label.class), withId(key, value));
		return new SWTBotLabel((Label) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotLabel} with the specified <code>value</code>.
	 */
	public SWTBotLabel labelWithId(String value) {
		return labelWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotLabel} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotLabel labelWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(Label.class), withId(value));
		return new SWTBotLabel((Label) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotLabel} with the specified <code>inGroup</code>.
	 */
	public SWTBotLabel labelInGroup(String inGroup) {
		return labelInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotLabel} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotLabel labelInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Label.class), inGroup(inGroup));
		return new SWTBotLabel((Label) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotLabel} with the specified <code>none</code>.
	 */
	public SWTBotLabel label() {
		return label(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotLabel} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotLabel label(int index) {
		Matcher matcher = allOf(widgetOfType(Label.class));
		return new SWTBotLabel((Label) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotLabel} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotLabel labelInGroup(String mnemonicText, String inGroup) {
		return labelInGroup(mnemonicText, inGroup, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotLabel} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotLabel labelInGroup(String mnemonicText, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Label.class), withMnemonic(mnemonicText), inGroup(inGroup));
		return new SWTBotLabel((Label) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @return a {@link SWTBotList} with the specified <code>label</code>.
	 */
	public SWTBotList listWithLabel(String label) {
		return listWithLabel(label, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotList} with the specified <code>label</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotList listWithLabel(String label, int index) {
		Matcher matcher = allOf(widgetOfType(List.class), withLabel(label));
		return new SWTBotList((List) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotList} with the specified <code>key/value</code>.
	 */
	public SWTBotList listWithId(String key, String value) {
		return listWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotList} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotList listWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(List.class), withId(key, value));
		return new SWTBotList((List) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotList} with the specified <code>value</code>.
	 */
	public SWTBotList listWithId(String value) {
		return listWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotList} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotList listWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(List.class), withId(value));
		return new SWTBotList((List) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotList} with the specified <code>inGroup</code>.
	 */
	public SWTBotList listInGroup(String inGroup) {
		return listInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotList} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotList listInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(List.class), inGroup(inGroup));
		return new SWTBotList((List) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotList} with the specified <code>none</code>.
	 */
	public SWTBotList list() {
		return list(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotList} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotList list(int index) {
		Matcher matcher = allOf(widgetOfType(List.class));
		return new SWTBotList((List) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotList} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotList listWithLabelInGroup(String label, String inGroup) {
		return listWithLabelInGroup(label, inGroup, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotList} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotList listWithLabelInGroup(String label, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(List.class), withLabel(label), inGroup(inGroup));
		return new SWTBotList((List) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @return a {@link SWTBotTable} with the specified <code>label</code>.
	 */
	public SWTBotTable tableWithLabel(String label) {
		return tableWithLabel(label, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTable} with the specified <code>label</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTable tableWithLabel(String label, int index) {
		Matcher matcher = allOf(widgetOfType(Table.class), withLabel(label));
		return new SWTBotTable((Table) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotTable} with the specified <code>key/value</code>.
	 */
	public SWTBotTable tableWithId(String key, String value) {
		return tableWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTable} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTable tableWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(Table.class), withId(key, value));
		return new SWTBotTable((Table) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotTable} with the specified <code>value</code>.
	 */
	public SWTBotTable tableWithId(String value) {
		return tableWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTable} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTable tableWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(Table.class), withId(value));
		return new SWTBotTable((Table) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotTable} with the specified <code>inGroup</code>.
	 */
	public SWTBotTable tableInGroup(String inGroup) {
		return tableInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTable} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTable tableInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Table.class), inGroup(inGroup));
		return new SWTBotTable((Table) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotTable} with the specified <code>none</code>.
	 */
	public SWTBotTable table() {
		return table(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTable} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTable table(int index) {
		Matcher matcher = allOf(widgetOfType(Table.class));
		return new SWTBotTable((Table) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotTable} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotTable tableWithLabelInGroup(String label, String inGroup) {
		return tableWithLabelInGroup(label, inGroup, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTable} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTable tableWithLabelInGroup(String label, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Table.class), withLabel(label), inGroup(inGroup));
		return new SWTBotTable((Table) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotTabItem} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotTabItem tabItem(String mnemonicText) {
		return tabItem(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTabItem} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTabItem tabItem(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(TabItem.class), withMnemonic(mnemonicText));
		return new SWTBotTabItem((TabItem) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotTabItem} with the specified <code>key/value</code>.
	 */
	public SWTBotTabItem tabItemWithId(String key, String value) {
		return tabItemWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTabItem} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTabItem tabItemWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(TabItem.class), withId(key, value));
		return new SWTBotTabItem((TabItem) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotTabItem} with the specified <code>value</code>.
	 */
	public SWTBotTabItem tabItemWithId(String value) {
		return tabItemWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTabItem} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTabItem tabItemWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(TabItem.class), withId(value));
		return new SWTBotTabItem((TabItem) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotTabItem} with the specified <code>inGroup</code>.
	 */
	public SWTBotTabItem tabItemInGroup(String inGroup) {
		return tabItemInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTabItem} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTabItem tabItemInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(TabItem.class), inGroup(inGroup));
		return new SWTBotTabItem((TabItem) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotTabItem} with the specified <code>none</code>.
	 */
	public SWTBotTabItem tabItem() {
		return tabItem(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTabItem} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTabItem tabItem(int index) {
		Matcher matcher = allOf(widgetOfType(TabItem.class));
		return new SWTBotTabItem((TabItem) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotTabItem} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotTabItem tabItemInGroup(String mnemonicText, String inGroup) {
		return tabItemInGroup(mnemonicText, inGroup, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTabItem} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTabItem tabItemInGroup(String mnemonicText, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(TabItem.class), withMnemonic(mnemonicText), inGroup(inGroup));
		return new SWTBotTabItem((TabItem) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotCTabItem} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotCTabItem cTabItem(String mnemonicText) {
		return cTabItem(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCTabItem} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCTabItem cTabItem(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(CTabItem.class), withMnemonic(mnemonicText));
		return new SWTBotCTabItem((CTabItem) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotCTabItem} with the specified <code>key/value</code>.
	 */
	public SWTBotCTabItem cTabItemWithId(String key, String value) {
		return cTabItemWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCTabItem} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCTabItem cTabItemWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(CTabItem.class), withId(key, value));
		return new SWTBotCTabItem((CTabItem) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotCTabItem} with the specified <code>value</code>.
	 */
	public SWTBotCTabItem cTabItemWithId(String value) {
		return cTabItemWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCTabItem} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCTabItem cTabItemWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(CTabItem.class), withId(value));
		return new SWTBotCTabItem((CTabItem) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotCTabItem} with the specified <code>inGroup</code>.
	 */
	public SWTBotCTabItem cTabItemInGroup(String inGroup) {
		return cTabItemInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCTabItem} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCTabItem cTabItemInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(CTabItem.class), inGroup(inGroup));
		return new SWTBotCTabItem((CTabItem) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotCTabItem} with the specified <code>none</code>.
	 */
	public SWTBotCTabItem cTabItem() {
		return cTabItem(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCTabItem} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCTabItem cTabItem(int index) {
		Matcher matcher = allOf(widgetOfType(CTabItem.class));
		return new SWTBotCTabItem((CTabItem) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotCTabItem} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotCTabItem cTabItemInGroup(String mnemonicText, String inGroup) {
		return cTabItemInGroup(mnemonicText, inGroup, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotCTabItem} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotCTabItem cTabItemInGroup(String mnemonicText, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(CTabItem.class), withMnemonic(mnemonicText), inGroup(inGroup));
		return new SWTBotCTabItem((CTabItem) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @return a {@link SWTBotStyledText} with the specified <code>label</code>.
	 */
	public SWTBotStyledText styledTextWithLabel(String label) {
		return styledTextWithLabel(label, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotStyledText} with the specified <code>label</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotStyledText styledTextWithLabel(String label, int index) {
		Matcher matcher = allOf(widgetOfType(StyledText.class), withLabel(label));
		return new SWTBotStyledText((StyledText) widget(matcher, index), matcher);
	}

	/**
	 * @param text the text on the widget.
	 * @return a {@link SWTBotStyledText} with the specified <code>text</code>.
	 */
	public SWTBotStyledText styledText(String text) {
		return styledText(text, 0);
	}

	/**
	 * @param text the text on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotStyledText} with the specified <code>text</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotStyledText styledText(String text, int index) {
		Matcher matcher = allOf(widgetOfType(StyledText.class), withText(text));
		return new SWTBotStyledText((StyledText) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotStyledText} with the specified <code>key/value</code>.
	 */
	public SWTBotStyledText styledTextWithId(String key, String value) {
		return styledTextWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotStyledText} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotStyledText styledTextWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(StyledText.class), withId(key, value));
		return new SWTBotStyledText((StyledText) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotStyledText} with the specified <code>value</code>.
	 */
	public SWTBotStyledText styledTextWithId(String value) {
		return styledTextWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotStyledText} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotStyledText styledTextWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(StyledText.class), withId(value));
		return new SWTBotStyledText((StyledText) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotStyledText} with the specified <code>inGroup</code>.
	 */
	public SWTBotStyledText styledTextInGroup(String inGroup) {
		return styledTextInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotStyledText} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotStyledText styledTextInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(StyledText.class), inGroup(inGroup));
		return new SWTBotStyledText((StyledText) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotStyledText} with the specified <code>none</code>.
	 */
	public SWTBotStyledText styledText() {
		return styledText(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotStyledText} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotStyledText styledText(int index) {
		Matcher matcher = allOf(widgetOfType(StyledText.class));
		return new SWTBotStyledText((StyledText) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotStyledText} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotStyledText styledTextWithLabelInGroup(String label, String inGroup) {
		return styledTextWithLabelInGroup(label, inGroup, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotStyledText} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotStyledText styledTextWithLabelInGroup(String label, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(StyledText.class), withLabel(label), inGroup(inGroup));
		return new SWTBotStyledText((StyledText) widget(matcher, index), matcher);
	}

	/**
	 * @param text the text on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotStyledText} with the specified <code>text</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotStyledText styledTextInGroup(String text, String inGroup) {
		return styledTextInGroup(text, inGroup, 0);
	}

	/**
	 * @param text the text on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotStyledText} with the specified <code>text</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotStyledText styledTextInGroup(String text, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(StyledText.class), withText(text), inGroup(inGroup));
		return new SWTBotStyledText((StyledText) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @return a {@link SWTBotDateTime} with the specified <code>label</code>.
	 */
	public SWTBotDateTime dateTimeWithLabel(String label) {
		return dateTimeWithLabel(label, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotDateTime} with the specified <code>label</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotDateTime dateTimeWithLabel(String label, int index) {
		Matcher matcher = allOf(widgetOfType(DateTime.class), withLabel(label));
		return new SWTBotDateTime((DateTime) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotDateTime} with the specified <code>key/value</code>.
	 */
	public SWTBotDateTime dateTimeWithId(String key, String value) {
		return dateTimeWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotDateTime} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotDateTime dateTimeWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(DateTime.class), withId(key, value));
		return new SWTBotDateTime((DateTime) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotDateTime} with the specified <code>value</code>.
	 */
	public SWTBotDateTime dateTimeWithId(String value) {
		return dateTimeWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotDateTime} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotDateTime dateTimeWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(DateTime.class), withId(value));
		return new SWTBotDateTime((DateTime) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotDateTime} with the specified <code>inGroup</code>.
	 */
	public SWTBotDateTime dateTimeInGroup(String inGroup) {
		return dateTimeInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotDateTime} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotDateTime dateTimeInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(DateTime.class), inGroup(inGroup));
		return new SWTBotDateTime((DateTime) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotDateTime} with the specified <code>none</code>.
	 */
	public SWTBotDateTime dateTime() {
		return dateTime(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotDateTime} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotDateTime dateTime(int index) {
		Matcher matcher = allOf(widgetOfType(DateTime.class));
		return new SWTBotDateTime((DateTime) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotDateTime} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotDateTime dateTimeWithLabelInGroup(String label, String inGroup) {
		return dateTimeWithLabelInGroup(label, inGroup, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotDateTime} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotDateTime dateTimeWithLabelInGroup(String label, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(DateTime.class), withLabel(label), inGroup(inGroup));
		return new SWTBotDateTime((DateTime) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotToolbarButton} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotToolbarButton toolbarButton(String mnemonicText) {
		return toolbarButton(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarButton} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarButton toolbarButton(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withMnemonic(mnemonicText), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotToolbarPushButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @return a {@link SWTBotToolbarButton} with the specified <code>tooltip</code>.
	 */
	public SWTBotToolbarButton toolbarButtonWithTooltip(String tooltip) {
		return toolbarButtonWithTooltip(tooltip, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarButton} with the specified <code>tooltip</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarButton toolbarButtonWithTooltip(String tooltip, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withTooltip(tooltip), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotToolbarPushButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotToolbarButton} with the specified <code>key/value</code>.
	 */
	public SWTBotToolbarButton toolbarButtonWithId(String key, String value) {
		return toolbarButtonWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarButton} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarButton toolbarButtonWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withId(key, value), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotToolbarPushButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotToolbarButton} with the specified <code>value</code>.
	 */
	public SWTBotToolbarButton toolbarButtonWithId(String value) {
		return toolbarButtonWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarButton} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarButton toolbarButtonWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withId(value), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotToolbarPushButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotToolbarButton} with the specified <code>inGroup</code>.
	 */
	public SWTBotToolbarButton toolbarButtonInGroup(String inGroup) {
		return toolbarButtonInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarButton} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarButton toolbarButtonInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), inGroup(inGroup), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotToolbarPushButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotToolbarButton} with the specified <code>none</code>.
	 */
	public SWTBotToolbarButton toolbarButton() {
		return toolbarButton(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarButton} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarButton toolbarButton(int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotToolbarPushButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotToolbarButton} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotToolbarButton toolbarButtonInGroup(String mnemonicText, String inGroup) {
		return toolbarButtonInGroup(mnemonicText, inGroup, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarButton} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarButton toolbarButtonInGroup(String mnemonicText, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withMnemonic(mnemonicText), inGroup(inGroup), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotToolbarPushButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotToolbarButton} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotToolbarButton toolbarButtonWithTooltipInGroup(String tooltip, String inGroup) {
		return toolbarButtonWithTooltipInGroup(tooltip, inGroup, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarButton} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarButton toolbarButtonWithTooltipInGroup(String tooltip, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withTooltip(tooltip), inGroup(inGroup), withStyle(SWT.PUSH, "SWT.PUSH"));
		return new SWTBotToolbarPushButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotToolbarToggleButton} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotToolbarToggleButton toolbarToggleButton(String mnemonicText) {
		return toolbarToggleButton(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarToggleButton} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarToggleButton toolbarToggleButton(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withMnemonic(mnemonicText), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotToolbarToggleButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @return a {@link SWTBotToolbarToggleButton} with the specified <code>tooltip</code>.
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonWithTooltip(String tooltip) {
		return toolbarToggleButtonWithTooltip(tooltip, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarToggleButton} with the specified <code>tooltip</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarToggleButton toolbarToggleButtonWithTooltip(String tooltip, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withTooltip(tooltip), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotToolbarToggleButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotToolbarToggleButton} with the specified <code>key/value</code>.
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonWithId(String key, String value) {
		return toolbarToggleButtonWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarToggleButton} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarToggleButton toolbarToggleButtonWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withId(key, value), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotToolbarToggleButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotToolbarToggleButton} with the specified <code>value</code>.
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonWithId(String value) {
		return toolbarToggleButtonWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarToggleButton} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarToggleButton toolbarToggleButtonWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withId(value), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotToolbarToggleButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotToolbarToggleButton} with the specified <code>inGroup</code>.
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonInGroup(String inGroup) {
		return toolbarToggleButtonInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarToggleButton} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarToggleButton toolbarToggleButtonInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), inGroup(inGroup), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotToolbarToggleButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotToolbarToggleButton} with the specified <code>none</code>.
	 */
	public SWTBotToolbarToggleButton toolbarToggleButton() {
		return toolbarToggleButton(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarToggleButton} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarToggleButton toolbarToggleButton(int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotToolbarToggleButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotToolbarToggleButton} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonInGroup(String mnemonicText, String inGroup) {
		return toolbarToggleButtonInGroup(mnemonicText, inGroup, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarToggleButton} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarToggleButton toolbarToggleButtonInGroup(String mnemonicText, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withMnemonic(mnemonicText), inGroup(inGroup), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotToolbarToggleButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotToolbarToggleButton} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonWithTooltipInGroup(String tooltip, String inGroup) {
		return toolbarToggleButtonWithTooltipInGroup(tooltip, inGroup, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarToggleButton} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarToggleButton toolbarToggleButtonWithTooltipInGroup(String tooltip, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withTooltip(tooltip), inGroup(inGroup), withStyle(SWT.CHECK, "SWT.CHECK"));
		return new SWTBotToolbarToggleButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotToolbarDropDownButton} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButton(String mnemonicText) {
		return toolbarDropDownButton(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarDropDownButton} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarDropDownButton toolbarDropDownButton(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withMnemonic(mnemonicText), withStyle(SWT.DROP_DOWN, "SWT.DROP_DOWN"));
		return new SWTBotToolbarDropDownButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @return a {@link SWTBotToolbarDropDownButton} with the specified <code>tooltip</code>.
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithTooltip(String tooltip) {
		return toolbarDropDownButtonWithTooltip(tooltip, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarDropDownButton} with the specified <code>tooltip</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithTooltip(String tooltip, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withTooltip(tooltip), withStyle(SWT.DROP_DOWN, "SWT.DROP_DOWN"));
		return new SWTBotToolbarDropDownButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotToolbarDropDownButton} with the specified <code>key/value</code>.
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithId(String key, String value) {
		return toolbarDropDownButtonWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarDropDownButton} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withId(key, value), withStyle(SWT.DROP_DOWN, "SWT.DROP_DOWN"));
		return new SWTBotToolbarDropDownButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotToolbarDropDownButton} with the specified <code>value</code>.
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithId(String value) {
		return toolbarDropDownButtonWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarDropDownButton} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withId(value), withStyle(SWT.DROP_DOWN, "SWT.DROP_DOWN"));
		return new SWTBotToolbarDropDownButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotToolbarDropDownButton} with the specified <code>inGroup</code>.
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonInGroup(String inGroup) {
		return toolbarDropDownButtonInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarDropDownButton} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarDropDownButton toolbarDropDownButtonInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), inGroup(inGroup), withStyle(SWT.DROP_DOWN, "SWT.DROP_DOWN"));
		return new SWTBotToolbarDropDownButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotToolbarDropDownButton} with the specified <code>none</code>.
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButton() {
		return toolbarDropDownButton(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarDropDownButton} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarDropDownButton toolbarDropDownButton(int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withStyle(SWT.DROP_DOWN, "SWT.DROP_DOWN"));
		return new SWTBotToolbarDropDownButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotToolbarDropDownButton} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonInGroup(String mnemonicText, String inGroup) {
		return toolbarDropDownButtonInGroup(mnemonicText, inGroup, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarDropDownButton} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarDropDownButton toolbarDropDownButtonInGroup(String mnemonicText, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withMnemonic(mnemonicText), inGroup(inGroup), withStyle(SWT.DROP_DOWN, "SWT.DROP_DOWN"));
		return new SWTBotToolbarDropDownButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotToolbarDropDownButton} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithTooltipInGroup(String tooltip, String inGroup) {
		return toolbarDropDownButtonWithTooltipInGroup(tooltip, inGroup, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarDropDownButton} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithTooltipInGroup(String tooltip, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withTooltip(tooltip), inGroup(inGroup), withStyle(SWT.DROP_DOWN, "SWT.DROP_DOWN"));
		return new SWTBotToolbarDropDownButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotToolbarRadioButton} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotToolbarRadioButton toolbarRadioButton(String mnemonicText) {
		return toolbarRadioButton(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarRadioButton} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarRadioButton toolbarRadioButton(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withMnemonic(mnemonicText), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotToolbarRadioButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @return a {@link SWTBotToolbarRadioButton} with the specified <code>tooltip</code>.
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonWithTooltip(String tooltip) {
		return toolbarRadioButtonWithTooltip(tooltip, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarRadioButton} with the specified <code>tooltip</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarRadioButton toolbarRadioButtonWithTooltip(String tooltip, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withTooltip(tooltip), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotToolbarRadioButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotToolbarRadioButton} with the specified <code>key/value</code>.
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonWithId(String key, String value) {
		return toolbarRadioButtonWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarRadioButton} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarRadioButton toolbarRadioButtonWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withId(key, value), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotToolbarRadioButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotToolbarRadioButton} with the specified <code>value</code>.
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonWithId(String value) {
		return toolbarRadioButtonWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarRadioButton} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarRadioButton toolbarRadioButtonWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withId(value), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotToolbarRadioButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotToolbarRadioButton} with the specified <code>inGroup</code>.
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonInGroup(String inGroup) {
		return toolbarRadioButtonInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarRadioButton} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarRadioButton toolbarRadioButtonInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), inGroup(inGroup), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotToolbarRadioButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotToolbarRadioButton} with the specified <code>none</code>.
	 */
	public SWTBotToolbarRadioButton toolbarRadioButton() {
		return toolbarRadioButton(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarRadioButton} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarRadioButton toolbarRadioButton(int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotToolbarRadioButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotToolbarRadioButton} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonInGroup(String mnemonicText, String inGroup) {
		return toolbarRadioButtonInGroup(mnemonicText, inGroup, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarRadioButton} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarRadioButton toolbarRadioButtonInGroup(String mnemonicText, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withMnemonic(mnemonicText), inGroup(inGroup), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotToolbarRadioButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotToolbarRadioButton} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonWithTooltipInGroup(String tooltip, String inGroup) {
		return toolbarRadioButtonWithTooltipInGroup(tooltip, inGroup, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotToolbarRadioButton} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotToolbarRadioButton toolbarRadioButtonWithTooltipInGroup(String tooltip, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(ToolItem.class), withTooltip(tooltip), inGroup(inGroup), withStyle(SWT.RADIO, "SWT.RADIO"));
		return new SWTBotToolbarRadioButton((ToolItem) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotLink} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotLink link(String mnemonicText) {
		return link(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotLink} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotLink link(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(Link.class), withMnemonic(mnemonicText));
		return new SWTBotLink((Link) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotLink} with the specified <code>key/value</code>.
	 */
	public SWTBotLink linkWithId(String key, String value) {
		return linkWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotLink} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotLink linkWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(Link.class), withId(key, value));
		return new SWTBotLink((Link) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotLink} with the specified <code>value</code>.
	 */
	public SWTBotLink linkWithId(String value) {
		return linkWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotLink} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotLink linkWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(Link.class), withId(value));
		return new SWTBotLink((Link) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotLink} with the specified <code>inGroup</code>.
	 */
	public SWTBotLink linkInGroup(String inGroup) {
		return linkInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotLink} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotLink linkInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Link.class), inGroup(inGroup));
		return new SWTBotLink((Link) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotLink} with the specified <code>none</code>.
	 */
	public SWTBotLink link() {
		return link(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotLink} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotLink link(int index) {
		Matcher matcher = allOf(widgetOfType(Link.class));
		return new SWTBotLink((Link) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotLink} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotLink linkInGroup(String mnemonicText, String inGroup) {
		return linkInGroup(mnemonicText, inGroup, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotLink} with the specified <code>mnemonicText</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotLink linkInGroup(String mnemonicText, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Link.class), withMnemonic(mnemonicText), inGroup(inGroup));
		return new SWTBotLink((Link) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>label</code>.
	 */
	public SWTBotSpinner spinnerWithLabel(String label) {
		return spinnerWithLabel(label, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>label</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSpinner spinnerWithLabel(String label, int index) {
		Matcher matcher = allOf(widgetOfType(Spinner.class), withLabel(label));
		return new SWTBotSpinner((Spinner) widget(matcher, index), matcher);
	}

	/**
	 * @param text the text on the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>text</code>.
	 */
	public SWTBotSpinner spinner(String text) {
		return spinner(text, 0);
	}

	/**
	 * @param text the text on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>text</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSpinner spinner(String text, int index) {
		Matcher matcher = allOf(widgetOfType(Spinner.class), withText(text));
		return new SWTBotSpinner((Spinner) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>tooltip</code>.
	 */
	public SWTBotSpinner spinnerWithTooltip(String tooltip) {
		return spinnerWithTooltip(tooltip, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>tooltip</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSpinner spinnerWithTooltip(String tooltip, int index) {
		Matcher matcher = allOf(widgetOfType(Spinner.class), withTooltip(tooltip));
		return new SWTBotSpinner((Spinner) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotSpinner} with the specified <code>key/value</code>.
	 */
	public SWTBotSpinner spinnerWithId(String key, String value) {
		return spinnerWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSpinner spinnerWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(Spinner.class), withId(key, value));
		return new SWTBotSpinner((Spinner) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotSpinner} with the specified <code>value</code>.
	 */
	public SWTBotSpinner spinnerWithId(String value) {
		return spinnerWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSpinner spinnerWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(Spinner.class), withId(value));
		return new SWTBotSpinner((Spinner) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>inGroup</code>.
	 */
	public SWTBotSpinner spinnerInGroup(String inGroup) {
		return spinnerInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSpinner spinnerInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Spinner.class), inGroup(inGroup));
		return new SWTBotSpinner((Spinner) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotSpinner} with the specified <code>none</code>.
	 */
	public SWTBotSpinner spinner() {
		return spinner(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSpinner spinner(int index) {
		Matcher matcher = allOf(widgetOfType(Spinner.class));
		return new SWTBotSpinner((Spinner) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotSpinner spinnerWithLabelInGroup(String label, String inGroup) {
		return spinnerWithLabelInGroup(label, inGroup, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSpinner spinnerWithLabelInGroup(String label, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Spinner.class), withLabel(label), inGroup(inGroup));
		return new SWTBotSpinner((Spinner) widget(matcher, index), matcher);
	}

	/**
	 * @param text the text on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>text</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotSpinner spinnerInGroup(String text, String inGroup) {
		return spinnerInGroup(text, inGroup, 0);
	}

	/**
	 * @param text the text on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>text</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSpinner spinnerInGroup(String text, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Spinner.class), withText(text), inGroup(inGroup));
		return new SWTBotSpinner((Spinner) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotSpinner spinnerWithTooltipInGroup(String tooltip, String inGroup) {
		return spinnerWithTooltipInGroup(tooltip, inGroup, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSpinner} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSpinner spinnerWithTooltipInGroup(String tooltip, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Spinner.class), withTooltip(tooltip), inGroup(inGroup));
		return new SWTBotSpinner((Spinner) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>label</code>.
	 */
	public SWTBotSlider sliderWithLabel(String label) {
		return sliderWithLabel(label, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>label</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSlider sliderWithLabel(String label, int index) {
		Matcher matcher = allOf(widgetOfType(Slider.class), withLabel(label));
		return new SWTBotSlider((Slider) widget(matcher, index), matcher);
	}

	/**
	 * @param text the text on the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>text</code>.
	 */
	public SWTBotSlider slider(String text) {
		return slider(text, 0);
	}

	/**
	 * @param text the text on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>text</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSlider slider(String text, int index) {
		Matcher matcher = allOf(widgetOfType(Slider.class), withText(text));
		return new SWTBotSlider((Slider) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>tooltip</code>.
	 */
	public SWTBotSlider sliderWithTooltip(String tooltip) {
		return sliderWithTooltip(tooltip, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>tooltip</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSlider sliderWithTooltip(String tooltip, int index) {
		Matcher matcher = allOf(widgetOfType(Slider.class), withTooltip(tooltip));
		return new SWTBotSlider((Slider) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotSlider} with the specified <code>key/value</code>.
	 */
	public SWTBotSlider sliderWithId(String key, String value) {
		return sliderWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSlider sliderWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(Slider.class), withId(key, value));
		return new SWTBotSlider((Slider) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotSlider} with the specified <code>value</code>.
	 */
	public SWTBotSlider sliderWithId(String value) {
		return sliderWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSlider sliderWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(Slider.class), withId(value));
		return new SWTBotSlider((Slider) widget(matcher, index), matcher);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>inGroup</code>.
	 */
	public SWTBotSlider sliderInGroup(String inGroup) {
		return sliderInGroup(inGroup, 0);
	}

	/**
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSlider sliderInGroup(String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Slider.class), inGroup(inGroup));
		return new SWTBotSlider((Slider) widget(matcher, index), matcher);
	}

	/**
	 * @return a {@link SWTBotSlider} with the specified <code>none</code>.
	 */
	public SWTBotSlider slider() {
		return slider(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSlider slider(int index) {
		Matcher matcher = allOf(widgetOfType(Slider.class));
		return new SWTBotSlider((Slider) widget(matcher, index), matcher);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotSlider sliderWithLabelInGroup(String label, String inGroup) {
		return sliderWithLabelInGroup(label, inGroup, 0);
	}

	/**
	 * @param label the label on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>label</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSlider sliderWithLabelInGroup(String label, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Slider.class), withLabel(label), inGroup(inGroup));
		return new SWTBotSlider((Slider) widget(matcher, index), matcher);
	}

	/**
	 * @param text the text on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>text</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotSlider sliderInGroup(String text, String inGroup) {
		return sliderInGroup(text, inGroup, 0);
	}

	/**
	 * @param text the text on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>text</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSlider sliderInGroup(String text, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Slider.class), withText(text), inGroup(inGroup));
		return new SWTBotSlider((Slider) widget(matcher, index), matcher);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	public SWTBotSlider sliderWithTooltipInGroup(String tooltip, String inGroup) {
		return sliderWithTooltipInGroup(tooltip, inGroup, 0);
	}

	/**
	 * @param tooltip the tooltip on the widget.
	 * @param inGroup the inGroup on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSlider} with the specified <code>tooltip</code> with the specified <code>inGroup</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSlider sliderWithTooltipInGroup(String tooltip, String inGroup, int index) {
		Matcher matcher = allOf(widgetOfType(Slider.class), withTooltip(tooltip), inGroup(inGroup));
		return new SWTBotSlider((Slider) widget(matcher, index), matcher);
	}

}

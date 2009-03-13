// Generated source.
package org.eclipse.swtbot.swt.finder.matchers;

public abstract class WidgetMatcherFactory {

  /**
   * Matches a widget that has the specified Label.
   * 
   * @param labelText the label.
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher<? extends org.eclipse.swt.widgets.Widget> withLabel(java.lang.String labelText) {
    return org.eclipse.swtbot.swt.finder.matchers.WithLabel.withLabel(labelText);
  }

  /**
   * Matches a widget that has the specified text, after striping the mnemonics "&"
   * 
   * @param text the text.
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher<? extends org.eclipse.swt.widgets.Widget> withMnemonic(java.lang.String text) {
    return org.eclipse.swtbot.swt.finder.matchers.WithMnemonic.withMnemonic(text);
  }

  /**
   * Matches a widget that has the specified regex.
   * 
   * @param regex the label.
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher<? extends org.eclipse.swt.widgets.Widget> withRegex(java.lang.String regex) {
    return org.eclipse.swtbot.swt.finder.matchers.WithRegex.withRegex(regex);
  }

  /**
   * Matches a widget that has the specified exact text.
   * 
   * @param text the label.
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher<? extends org.eclipse.swt.widgets.Widget> withText(java.lang.String text) {
    return org.eclipse.swtbot.swt.finder.matchers.WithText.withText(text);
  }

  /**
   * Matches a widget that has the specified text, ignoring case considerations.
   * 
   * @param text the label.
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher<? extends org.eclipse.swt.widgets.Widget> withTextIgnoringCase(java.lang.String text) {
    return org.eclipse.swtbot.swt.finder.matchers.WithText.withTextIgnoringCase(text);
  }

  /**
   * Matches a widget that has the specified style bit set.
   * 
   * @param style the style bits.
   * @param styleDescription the description of the style bits.
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher<? extends org.eclipse.swt.widgets.Widget> withStyle(int style, java.lang.String styleDescription) {
    return org.eclipse.swtbot.swt.finder.matchers.WithStyle.withStyle(style, styleDescription);
  }

  /**
   * Matches a widget that has the specified exact tooltip.
   * 
   * @param text the label.
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher<? extends org.eclipse.swt.widgets.Widget> withTooltip(java.lang.String text) {
    return org.eclipse.swtbot.swt.finder.matchers.WithTooltip.withTooltip(text);
  }

  /**
   * Matches a widget that has the specified tooltip, ignoring case considerations.
   * 
   * @param text the label.
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher<? extends org.eclipse.swt.widgets.Widget> withTooltipIgoringCase(java.lang.String text) {
    return org.eclipse.swtbot.swt.finder.matchers.WithTooltip.withTooltipIgoringCase(text);
  }

  /**
   * Matches a widget that has the specified Key/Value pair set as data into it.
   * 
   * @see org.eclipse.swt.widgets.Widget#setData(String, Object)
   * @param key the key
   * @param value the value
   * @return a matcher.
   */
  public static org.hamcrest.Matcher<? extends org.eclipse.swt.widgets.Widget> withId(java.lang.String key, java.lang.String value) {
    return org.eclipse.swtbot.swt.finder.matchers.WithId.withId(key, value);
  }

  /**
   * Matches a widget that has the specified value set for the key
   * {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
   * 
   * @see org.eclipse.swt.widgets.Widget#setData(String, Object)
   * @param value the value
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher<? extends org.eclipse.swt.widgets.Widget> withId(java.lang.String value) {
    return org.eclipse.swtbot.swt.finder.matchers.WithId.withId(value);
  }

  /**
   * Returns a matcher that matches objects containing an item that matches the matcher.
   * <p>
   * <strong>Note:</strong> This invokes getItems method on the object and expects to see an array as a return value.
   * </p>
   * 
   * @param matcher the matcher.
   * @return a matcher.
   */
  public static <T extends org.eclipse.swt.widgets.Item> org.hamcrest.Matcher<T> withItem(org.hamcrest.Matcher<?> matcher) {
    return org.eclipse.swtbot.swt.finder.matchers.WithItem.withItem(matcher);
  }

  /**
   * Matches a widget that belongs to the specified group
   * 
   * @param labelText the label.
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher<? extends org.eclipse.swt.widgets.Widget> inGroup(java.lang.String labelText) {
    return org.eclipse.swtbot.swt.finder.matchers.InGroup.inGroup(labelText);
  }

  /**
   * Matches a widget in a group, if the matcher evaluates to true for the group.
   * 
   * @param matcher the matcher.
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher<? extends org.eclipse.swt.widgets.Widget> inGroup(org.hamcrest.Matcher<?> matcher) {
    return org.eclipse.swtbot.swt.finder.matchers.InGroup.inGroup(matcher);
  }

  /**
   * Matches a widget that has the specified type
   * 
   * @param type the type of the widget.
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher<? extends org.eclipse.swt.widgets.Widget> widgetOfType(java.lang.Class<? extends org.eclipse.swt.widgets.Widget> type) {
    return org.eclipse.swtbot.swt.finder.matchers.WidgetOfType.widgetOfType(type);
  }

  /**
   * Matches another matcher in the context of the UI thread. Useful if you want to make a matcher UI thread safe.
   * 
   * @param matcher the matcher
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher<? extends org.eclipse.swt.widgets.Widget> inUIThread(org.hamcrest.Matcher<?> matcher) {
    return org.eclipse.swtbot.swt.finder.matchers.InUIThread.inUIThread(matcher);
  }

  /**
   * Evaluates to true only if ALL of the passed in matchers evaluate to true.
   * 
   * @return a matcher.
   */
  public static <T> org.hamcrest.Matcher<T> allOf(org.hamcrest.Matcher<? extends T>... matchers) {
    return org.eclipse.swtbot.swt.finder.matchers.AllOf.allOf(matchers);
  }

}

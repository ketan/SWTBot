// Generated source.
package org.eclipse.swtbot.eclipse.finder.matchers;

public abstract class WidgetMatcherFactory extends org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory {

  /**
   * Matches a workbench part (view/editor) with the specfied name.
   * 
   * @param text the label of the part.
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher<? extends org.eclipse.ui.IWorkbenchPartReference> withPartName(java.lang.String text) {
    return org.eclipse.swtbot.eclipse.finder.matchers.WithPartName.withPartName(text);
  }

}

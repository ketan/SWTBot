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
  public static <T extends org.eclipse.ui.IWorkbenchPartReference> org.hamcrest.Matcher<T> withPartName(java.lang.String text) {
    return org.eclipse.swtbot.eclipse.finder.matchers.WithPartName.withPartName(text);
  }

  /**
   * Matches a workbench part (view/editor) with the specified name.
   * 
   * @param nameMatcher the part name matcher.
   * @return a matcher.
   * @since 2.0
   */
  public static <T extends org.eclipse.ui.IWorkbenchPartReference> org.hamcrest.Matcher<T> withPartName(org.hamcrest.Matcher<java.lang.String> nameMatcher) {
    return org.eclipse.swtbot.eclipse.finder.matchers.WithPartName.withPartName(nameMatcher);
  }

  /**
   * Matches a workbench part (view/editor) with the specified id.
   * 
   * @param id the id of the part.
   * @return a matcher.
   * @since 2.0
   */
  public static <T extends org.eclipse.ui.IWorkbenchPartReference> org.hamcrest.Matcher<T> withPartId(java.lang.String id) {
    return org.eclipse.swtbot.eclipse.finder.matchers.WithPartId.withPartId(id);
  }

  /**
   * Matches a workbench part (view/editor) with the specified id.
   * 
   * @param idMatcher the part id matcher.
   * @return a matcher.
   * @since 2.0
   */
  public static <T extends org.eclipse.ui.IWorkbenchPartReference> org.hamcrest.Matcher<T> withPartId(org.hamcrest.Matcher<java.lang.String> idMatcher) {
    return org.eclipse.swtbot.eclipse.finder.matchers.WithPartId.withPartId(idMatcher);
  }

  /**
   * Matches a perspective with the specified id.
   * 
   * @param id the id of the perspective.
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher withPerspectiveId(java.lang.String id) {
    return org.eclipse.swtbot.eclipse.finder.matchers.WithPerspectiveId.withPerspectiveId(id);
  }

  /**
   * Matches a perspective with the specified id.
   * 
   * @param idMatcher the matcher that matches the id of the perspective.
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher withPerspectiveId(org.hamcrest.Matcher<java.lang.String> idMatcher) {
    return org.eclipse.swtbot.eclipse.finder.matchers.WithPerspectiveId.withPerspectiveId(idMatcher);
  }

  /**
   * Matches a perspective with the specified label.
   * 
   * @param label the label of the perspective.
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher withPerspectiveLabel(java.lang.String label) {
    return org.eclipse.swtbot.eclipse.finder.matchers.WithPerspectiveLabel.withPerspectiveLabel(label);
  }

  /**
   * Matches a perspective with the specified label.
   * 
   * @param labelMatcher the matcher that matches the perspective label.
   * @return a matcher.
   * @since 2.0
   */
  public static org.hamcrest.Matcher withPerspectiveLabel(org.hamcrest.Matcher<java.lang.String> labelMatcher) {
    return org.eclipse.swtbot.eclipse.finder.matchers.WithPerspectiveLabel.withPerspectiveLabel(labelMatcher);
  }

}

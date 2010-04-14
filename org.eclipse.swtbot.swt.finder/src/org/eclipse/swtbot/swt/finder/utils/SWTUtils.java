/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Hans Schwaebli - http://swtbot.org/bugzilla/show_bug.cgi?id=108
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.utils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.eclipse.swtbot.swt.finder.utils.internal.NextWidgetFinder;
import org.eclipse.swtbot.swt.finder.utils.internal.PreviousWidgetFinder;
import org.eclipse.swtbot.swt.finder.utils.internal.ReflectionInvoker;
import org.eclipse.swtbot.swt.finder.utils.internal.SiblingFinder;
import org.eclipse.swtbot.swt.finder.utils.internal.WidgetIndexFinder;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public abstract class SWTUtils {

	/** The logger. */
	private static Logger	log	= Logger.getLogger(SWTUtils.class);

	/**
	 * The display used for the GUI.
	 */
	private static Display	display;

	/**
	 * @param w the widget
	 * @return the siblings of the widget, or an empty array, if there are none.
	 */
	public static Widget[] siblings(final Widget w) {
		if ((w == null) || w.isDisposed())
			return new Widget[] {};
		return UIThreadRunnable.syncExec(w.getDisplay(), new SiblingFinder(w));
	}

	/**
	 * Gets the index of the given widget in its current container.
	 * 
	 * @param w the widget
	 * @return -1 if the the widget is <code>null</code> or if the widget does not have a parent; a number greater than
	 *         or equal to zero indicating the index of the widget among its siblings
	 */
	public static int widgetIndex(final Widget w) {
		if ((w == null) || w.isDisposed())
			return -1;
		return UIThreadRunnable.syncExec(w.getDisplay(), new WidgetIndexFinder(w));
	}

	/**
	 * Gets the previous sibling of the passed in widget.
	 * 
	 * @param w the widget
	 * @return the previous sibling of w
	 */
	public static Widget previousWidget(final Widget w) {
		if ((w == null) || w.isDisposed())
			return null;
		return UIThreadRunnable.syncExec(w.getDisplay(), new PreviousWidgetFinder(w));
	}

	/**
	 * Gets the next sibling of this passed in widget.
	 * 
	 * @param w the widget.
	 * @return the sibling of the specified widget, or <code>null</code> if it has none.
	 */
	public static Widget nextWidget(Widget w) {
		if ((w == null) || w.isDisposed())
			return null;
		return UIThreadRunnable.syncExec(w.getDisplay(), new NextWidgetFinder(w));
	}

	/**
	 * Gets the text of the given object.
	 * 
	 * @param obj the object which should be a widget.
	 * @return the result of invocation of Widget#getText()
	 */
	public static String getText(final Object obj) {
		if ((obj instanceof Widget) && !((Widget) obj).isDisposed()) {
			Widget widget = (Widget) obj;
			String text = UIThreadRunnable.syncExec(widget.getDisplay(), new ReflectionInvoker(obj, "getText")); //$NON-NLS-1$
			text = text.replaceAll(Text.DELIMITER, "\n"); //$NON-NLS-1$
			return text;
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Gets the tooltip text for the given object.
	 * 
	 * @param obj the object which should be a widget.
	 * @return the result of invocation of Widget#getToolTipText()
	 * @since 1.0
	 */
	public static String getToolTipText(final Object obj) {
		if ((obj instanceof Widget) && !((Widget) obj).isDisposed()) {
			Widget widget = (Widget) obj;
			return UIThreadRunnable.syncExec(widget.getDisplay(), new ReflectionInvoker(obj, "getToolTipText")); //$NON-NLS-1$
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Converts the given widget to a string.
	 * 
	 * @param w the widget.
	 * @return the string representation of the widget.
	 */
	public static String toString(final Widget w) {
		if ((w == null) || w.isDisposed())
			return ""; //$NON-NLS-1$
		return toString(w.getDisplay(), w);
	}

	/**
	 * Convers the display and object to a string.
	 * 
	 * @param display the display on which the object should be evaluated.
	 * @param o the object to evaluate.
	 * @return the string representation of the object when evaluated in the display thread.
	 */
	public static String toString(Display display, final Object o) {
		return ClassUtils.simpleClassName(o) + " {" + trimToLength(getText(o), 20) + "}"; //$NON-NLS-1$ //$NON-NLS-2$

	}

	/**
	 * Trims the string to a given length, adds an ellipsis("...") if the string is trimmed.
	 * 
	 * @param result The string to limit.
	 * @param maxLength The length to limit it to.
	 * @return The resulting string.
	 */
	private static String trimToLength(String result, int maxLength) {
		if (result.length() > maxLength)
			result = result.substring(0, maxLength) + "..."; //$NON-NLS-1$
		return result;
	}

	/**
	 * Checks if the widget has the given style.
	 * 
	 * @param w the widget.
	 * @param style the style.
	 * @return <code>true</code> if the widget has the specified style bit set. Otherwise <code>false</code>.
	 */
	public static boolean hasStyle(final Widget w, final int style) {
		if ((w == null) || w.isDisposed())
			return false;
		if (style == SWT.NONE)
			return true;
		return UIThreadRunnable.syncExec(w.getDisplay(), new BoolResult() {
			public Boolean run() {
				return (w.getStyle() & style) != 0;
			}
		});
	}

	/**
	 * Sleeps for the given number of milliseconds.
	 * 
	 * @param millis the time in milliseconds to sleep.
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException("Could not sleep", e); //$NON-NLS-1$
		}
	}

	/**
	 * Gets all the thread in the VM.
	 * 
	 * @return all the threads in the VM.
	 */
	public static Thread[] allThreads() {
		ThreadGroup threadGroup = primaryThreadGroup();

		Thread[] threads = new Thread[64];
		int enumerate = threadGroup.enumerate(threads, true);

		Thread[] result = new Thread[enumerate];
		System.arraycopy(threads, 0, result, 0, enumerate);

		return result;
	}

	/**
	 * Gets the primary thread group.
	 * 
	 * @return the top level thread group.
	 */
	public static ThreadGroup primaryThreadGroup() {
		ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
		while (threadGroup.getParent() != null)
			threadGroup = threadGroup.getParent();
		return threadGroup;
	}

	/**
	 * Caches the display for later use.
	 * 
	 * @return the display.
	 */
	public static Display display() {
		if ((display == null) || display.isDisposed()) {
			display = null;
			Thread[] allThreads = allThreads();
			for (Thread thread : allThreads) {
				Display d = Display.findDisplay(thread);
				if (d != null)
					display = d;
			}
			if (display == null)
				throw new IllegalStateException("Could not find a display"); //$NON-NLS-1$
		}
		return display;
	}

	/**
	 * Checks if the widget text is an empty string.
	 * 
	 * @param w the widget
	 * @return <code>true</code> if the widget does not have any text set on it. Othrewise <code>false</code>.
	 */
	// TODO recommend changing the name to isEmptyText since null isn't being check and if getText returned a null an
	// exception would be thrown.
	public static boolean isEmptyOrNullText(Widget w) {
		return getText(w).trim().equals(""); //$NON-NLS-1$
	}

	/**
	 * Invokes the specified methodName on the object, and returns the result, or <code>null</code> if the method
	 * returns void.
	 * 
	 * @param object the object
	 * @param methodName the method name
	 * @return the result of invoking the method on the object
	 * @throws NoSuchMethodException if the method methodName does not exist.
	 * @throws IllegalAccessException if the java access control does not allow invocation.
	 * @throws InvocationTargetException if the method methodName throws an exception.
	 * @see Method#invoke(Object, Object[])
	 * @since 1.0
	 */
	public static Object invokeMethod(final Object object, String methodName) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		final Method method = object.getClass().getMethod(methodName, new Class[0]);
		Widget widget = null;
		final Object result;
		if (object instanceof Widget) {
			widget = (Widget) object;
			result = UIThreadRunnable.syncExec(widget.getDisplay(), new Result<Object>() {
				public Object run() {
					try {
						return method.invoke(object, new Object[0]);
					} catch (Exception niceTry) {
					}
					return null;
				}
			});
		} else
			result = method.invoke(object, new Object[0]);

		return result;
	}

	/**
	 * This captures a screen shot and saves it to the given file.
	 * 
	 * @param fileName the filename to save screenshot to.
	 * @return <code>true</code> if the screenshot was created and saved, <code>false</code> otherwise.
	 * @since 1.0
	 */
	public static boolean captureScreenshot(final String fileName) {
		new ImageFormatConverter().imageTypeOf(fileName.substring(fileName.lastIndexOf('.') + 1));
		return UIThreadRunnable.syncExec(new BoolResult() {
			public Boolean run() {
				return captureScreenshotInternal(fileName);
			}
		});
	}

	/**
	 * This captures a screen shot of a widget and saves it to the given file.
	 * 
	 * @param fileName the filename to save screenshot to.
	 * @param control the control
	 * @return <code>true</code> if the screenshot was created and saved, <code>false</code> otherwise.
	 * @since 2.0
	 */
	public static boolean captureScreenshot(final String fileName, final Control control) {
		new ImageFormatConverter().imageTypeOf(fileName.substring(fileName.lastIndexOf('.') + 1));
		return UIThreadRunnable.syncExec(new BoolResult() {
			public Boolean run() {
				if (control instanceof Shell)
					return captureScreenshotInternal(fileName, control.getBounds());
				Display display = control.getDisplay();
				Rectangle bounds = control.getBounds();
				Rectangle mappedToDisplay = display.map(control.getParent(), null, bounds);

				return captureScreenshotInternal(fileName, mappedToDisplay);
			}
		});
	}

	/**
	 * This captures a screen shot of an area and saves it to the given file.
	 * 
	 * @param fileName the filename to save screenshot to.
	 * @param bounds the area to capture.
	 * @return <code>true</code> if the screenshot was created and saved, <code>false</code> otherwise.
	 * @since 2.0
	 */
	public static boolean captureScreenshot(final String fileName, final Rectangle bounds) {
		new ImageFormatConverter().imageTypeOf(fileName.substring(fileName.lastIndexOf('.') + 1));
		return UIThreadRunnable.syncExec(new BoolResult() {
			public Boolean run() {
				return captureScreenshotInternal(fileName, bounds);
			}
		});
	}

	/**
	 * Captures a screen shot. Used internally.
	 * <p>
	 * NOTE: This method is not thread safe. Clients must ensure that they do invoke this from a UI thread.
	 * </p>
	 * 
	 * @param fileName the filename to save screenshot to.
	 * @return <code>true</code> if the screenshot was created and saved, <code>false</code> otherwise.
	 */
	private static boolean captureScreenshotInternal(final String fileName) {
		return captureScreenshotInternal(fileName, display.getBounds());
	}

	/**
	 * Captures a screen shot. Used internally.
	 * <p>
	 * NOTE: This method is not thread safe. Clients must ensure that they do invoke this from a UI thread.
	 * </p>
	 * 
	 * @param fileName the filename to save screenshot to.
	 * @param bounds the area relative to the display that should be captured.
	 * @return <code>true</code> if the screenshot was created and saved, <code>false</code> otherwise.
	 */
	private static boolean captureScreenshotInternal(final String fileName, Rectangle bounds) {
		GC gc = new GC(display);
		Image image = null;
		new File(fileName).getParentFile().mkdirs();
		try {
			log.debug(MessageFormat.format("Capturing screenshot ''{0}''", fileName)); //$NON-NLS-1$

			image = new Image(display, bounds.width, bounds.height);
			gc.copyArea(image, bounds.x, bounds.y);
			ImageLoader imageLoader = new ImageLoader();
			imageLoader.data = new ImageData[] { image.getImageData() };
			imageLoader.save(fileName, new ImageFormatConverter().imageTypeOf(fileName.substring(fileName.lastIndexOf('.') + 1)));
			return true;
		} catch (Exception e) {
			log.warn("Could not capture screenshot: " + fileName + "'", e); //$NON-NLS-1$ //$NON-NLS-2$
			File brokenImage = new File(fileName).getAbsoluteFile();
			if (brokenImage.exists()) {
				try {
					log.trace(MessageFormat.format("Broken screenshot set to be deleted on exit: {0}", fileName)); //$NON-NLS-1$
					brokenImage.deleteOnExit();
				} catch (Exception ex) {
					log.info(MessageFormat.format("Could not set broken screenshot to be deleted on exit: {0}", fileName), ex); //$NON-NLS-1$
				}
			}
			return false;
		} finally {
			gc.dispose();
			if (image != null) {
				image.dispose();
			}
		}
	}

	/**
	 * Waits until a display appears.
	 * 
	 * @throws TimeoutException if the condition does not evaluate to true after {@link SWTBotPreferences#TIMEOUT}
	 *             milliseconds.
	 */
	public static void waitForDisplayToAppear() {
		waitForDisplayToAppear(SWTBotPreferences.TIMEOUT);
	}

	/**
	 * Waits until a display appears.
	 * 
	 * @param timeout the timeout in ms.
	 * @throws TimeoutException if the condition does not evaluate to true after {@code timeout}ms milliseconds.
	 */
	public static void waitForDisplayToAppear(long timeout) {
		waitUntil(new DefaultCondition() {

			public String getFailureMessage() {
				return "Could not find a display"; //$NON-NLS-1$
			}

			public boolean test() throws Exception {
				return SWTUtils.display() != null;
			}

		}, timeout, 500);
	}

	private static void waitUntil(ICondition condition, long timeout, long interval) throws TimeoutException {
		Assert.isTrue(interval >= 0, "interval value is negative"); //$NON-NLS-1$
		Assert.isTrue(timeout >= 0, "timeout value is negative"); //$NON-NLS-1$
		long limit = System.currentTimeMillis() + timeout;
		while (true) {
			try {
				if (condition.test())
					return;
			} catch (Throwable e) {
				// do nothing
			}
			sleep(interval);
			if (System.currentTimeMillis() > limit)
				throw new TimeoutException("Timeout after: " + timeout + " ms.: " + condition.getFailureMessage()); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Return true if the current thread is the UI thread.
	 * 
	 * @param display the display
	 * @return <code>true</code> if the current thread is the UI thread, <code>false</code> otherwise.
	 */
	public static boolean isUIThread(Display display) {
		return display.getThread() == Thread.currentThread();
	}

	/**
	 * Return true if the current thread is the UI thread.
	 * 
	 * @return <code>true</code> if this instance is running in the UI thread, <code>false</code> otherwise.
	 */
	public static boolean isUIThread() {
		return isUIThread(display);
	}
}

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
package org.eclipse.swtbot.swt.finder.finders;

import static org.eclipse.swtbot.swt.finder.utils.SWTUtils.display;
import static org.eclipse.swtbot.swt.finder.utils.SWTUtils.isUIThread;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.swt.finder.results.ArrayResult;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.VoidResult;

/**
 * Performs operations in the UI thread. If the {@link #run()} method of this class is called from an non-UI thread, the
 * instance ensures that it runs in the UI thread by invoking {@link Display#syncExec(Runnable)}, else it executes in
 * the UI thread. All operations are blocking operations.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public abstract class UIThreadRunnable implements Runnable {

	private static final Logger	log	= Logger.getLogger(UIThreadRunnable.class);

	/** the display on which runnables must be executed. */
	protected final Display		display;
	/**
	 * A flag to denote if the runnable should execute asynchronously.
	 */
	private final boolean		async;

	/**
	 * Runs synchronously in the UI thread.
	 * 
	 * @param display The display to be used.
	 */
	private UIThreadRunnable(Display display) {
		this(display, false);
	}

	/**
	 * A private contructor use to create this object.
	 * 
	 * @param display The display to use.
	 * @param async if the thread should run asynchronously or not.
	 * @see Display#syncExec(Runnable)
	 * @see Display#asyncExec(Runnable)
	 */
	private UIThreadRunnable(Display display, boolean async) {
		this.display = display;
		this.async = async;
	}

	/**
	 * This method is intelligent to execute in the UI thread.
	 */
	public void run() {
		if ((display == null) || display.isDisposed())
			return;

		if (!isUIThread(display)) {
			if (async)
				display.asyncExec(runnable());
			else
				display.syncExec(runnable());
		} else
			doRun();
	}

	/**
	 * A runnable instance that is used internally.
	 * 
	 * @return The runnable instance.
	 */
	private Runnable runnable() {
		final Runnable runnable = new Runnable() {
			public void run() {
				doRun();
				dispatchAllEvents();
			}
		};
		return runnable;
	}

	/**
	 * This dispatched events in the UI thread.
	 * <p>
	 * This must be called in the UI thread only. This method does not execute in a syncexec/asyncexec block
	 * </p>
	 */
	private void dispatchAllEvents() {
		display.wake();
		// while (true)
		// if (!display.readAndDispatch())
		// break;
	}

	/**
	 * Performs the run in the UI Thread.
	 * <p>
	 * This MUST be invoked in the UI thread.
	 * </p>
	 */
	protected abstract void doRun();

	/**
	 * Executes the {@code toExecute} on the UI thread, and blocks the calling thread.
	 * 
	 * @param <T> the type of the result.
	 * @param toExecute the runnable to execute.
	 * @return the result of executing result on the UI thread.
	 */
	public static <T> T syncExec(final Result<T> toExecute) {
		return syncExec(display(), toExecute);
	}

	/**
	 * Executes the {@code toExecute} on the display thread, and blocks the calling thread.
	 * 
	 * @param <T> the type of the result.
	 * @param display the display on which toExecute must be executed.
	 * @param toExecute the runnable to execute.
	 * @return the object result of execution on the UI thread.
	 */
	public static <T> T syncExec(Display display, final Result<T> toExecute) {
		final ArrayList<T> arrayList = new ArrayList<T>();
		new UIThreadRunnable(display) {
			protected void doRun() {
				arrayList.add(toExecute.run());
			}
		}.run();
		return arrayList.get(0);
	}

	/**
	 * Executes the {@code toExecute} on the display thread, and blocks the calling thread.
	 * 
	 * @param <T> the type of the result.
	 * @param toExecute the runnable to execute.
	 * @return the object result of execution on the UI thread.
	 */
	public static <T> T[] syncExec(final ArrayResult<T> toExecute) {
		return syncExec(display(), toExecute);
	}

	/**
	 * Executes the {@code toExecute} on the display thread, and blocks the calling thread.
	 * 
	 * @param <T> the type of the result.
	 * @param display the display on which toExecute must be executed.
	 * @param toExecute the runnable to execute.
	 * @return the object result of execution on the UI thread.
	 */
	public static <T> T[] syncExec(Display display, final ArrayResult<T> toExecute) {
		final ArrayList<T[]> arrayList = new ArrayList<T[]>();
		new UIThreadRunnable(display) {
			protected void doRun() {
				T[] run = toExecute.run();
				arrayList.add(run);
			}
		}.run();
		return arrayList.get(0);
	}

	/**
	 * Executes the {@code toExecute} on the UI thread, and blocks the calling thread.
	 * 
	 * @param toExecute the runnable to execute.
	 * @since 1.0
	 */
	public static void syncExec(final VoidResult toExecute) {
		syncExec(display(), toExecute);
	}

	/**
	 * Executes the {@code toExecute} on the display thread, and blocks the calling thread.
	 * 
	 * @param display the display on which toExecute must be executed.
	 * @param toExecute the runnable to execute.
	 */
	public static void syncExec(Display display, final VoidResult toExecute) {
		new UIThreadRunnable(display) {
			@Override
			protected void doRun() {
				toExecute.run();
			}
		}.run();
	}

	/**
	 * Executes the {@code toExecute} on the UI thread asynchronously, and does not block the calling thread.
	 * 
	 * @param toExecute the runnable to execute.
	 * @since 1.0
	 */
	public static void asyncExec(final VoidResult toExecute) {
		asyncExec(display(), toExecute);
	}

	/**
	 * Executes the {@code toExecute} on the UI thread asynchronously, and does not block the calling thread.
	 * 
	 * @param display the display on which toExecute must be executed.
	 * @param toExecute the runnable to execute.
	 */
	public static void asyncExec(Display display, final VoidResult toExecute) {
		new UIThreadRunnable(display, true) {
			@Override
			protected void doRun() {
				toExecute.run();
			}
		}.run();
	}

}

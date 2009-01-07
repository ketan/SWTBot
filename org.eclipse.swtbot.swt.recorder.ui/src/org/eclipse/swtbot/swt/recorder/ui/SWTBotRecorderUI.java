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
package org.eclipse.swtbot.swt.recorder.ui;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.recorder.SWTBotRecorder;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotAction;


/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotRecorderUI {

	public static final String	CLEAR	= "clear"; //$NON-NLS-1$
	public static final String	STOP	= "stop"; //$NON-NLS-1$
	public static final String	SAVE	= "save"; //$NON-NLS-1$
	public static final String	START	= "start"; //$NON-NLS-1$
	public static final String	PAUSE	= "pause"; //$NON-NLS-1$

	public static ImageRegistry	imageRegistry;
	private Display				display;
	private Shell				shell;
	private Button				startPauseButton;
	private Button				saveButton;
	private SWTBot				bot;
	private SWTBotRecorder		recorder;
	private Button				clearButton;
	private Button				stopRecorderButton;

	public SWTBotRecorderUI() {
	}

	public void initialize() throws Exception {
		record(waitForDisplayToAppear(5000));
	}

	protected void createShell() {
		shell = new Shell(display, SWT.TITLE | SWT.ON_TOP);
		shell.setLayout(new GridLayout(4, false));
		shell.setText("SWTBot Recorder"); //$NON-NLS-1$

		createStartPauseButton();
		createStopRecorderButton();
		createSaveButton();
		createClearButton();

		hookButtonActions();
		setButtonStates();

		shell.pack();
		shell.open();
	}

	private void createStopRecorderButton() {
		stopRecorderButton = new Button(shell, SWT.TOGGLE);
		stopRecorderButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		stopRecorderButton.setImage(imageRegistry.get(STOP));
	}

	public void setButtonStates() {
		saveButton.setEnabled(recorder.isRunning());
		clearButton.setEnabled(recorder.isRunning());
		stopRecorderButton.setEnabled(recorder.isRunning());
	}

	private void createClearButton() {
		clearButton = new Button(shell, SWT.TOGGLE);
		clearButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		clearButton.setImage(imageRegistry.get(CLEAR));
	}

	protected void createSaveButton() {
		saveButton = new Button(shell, SWT.TOGGLE);
		saveButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		saveButton.setImage(imageRegistry.get(SAVE));
	}

	protected void createStartPauseButton() {
		startPauseButton = new Button(shell, SWT.TOGGLE);
		startPauseButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		startPauseButton.setImage(imageRegistry.get(START));
	}

	private void hookButtonActions() {
		hookStartPauseButtonActions();
		hookSaveButtonActions();
		hookClearButtonActions();
		hookStopRecorderButtonActions();
	}

	private void hookStopRecorderButtonActions() {
		stopRecorderButton.addSelectionListener(new StopRecorderListener(this, startPauseButton, stopRecorderButton));
	}

	private void hookClearButtonActions() {
		clearButton.addSelectionListener(new ClearButtonSelectionListener(this, clearButton));
	}

	private void hookSaveButtonActions() {
		saveButton.addSelectionListener(new SaveButtonSelectionListener(this, saveButton));
	}

	private void hookStartPauseButtonActions() {
		startPauseButton.addSelectionListener(new StartPauseButtonToggleListener(this, startPauseButton));
	}

	protected void loadImages() {
		imageRegistry = new ImageRegistry();
		loadImage(START);
		loadImage(PAUSE);
		loadImage(SAVE);
		loadImage(STOP);
		loadImage(CLEAR);
	}

	private void loadImage(String imageName) {
		InputStream record = getClass().getClassLoader().getResourceAsStream("icons/" + imageName + ".gif"); //$NON-NLS-1$ //$NON-NLS-2$
		imageRegistry.put(imageName, new Image(display, record));
	}

	protected void record(final Display display) {
		new SWTBot().waitUntil(new DefaultCondition() {
			public String getFailureMessage() {
				return null;
			}

			public boolean test() throws Exception {
				return UIThreadRunnable.syncExec(new BoolResult() {
					public Boolean run() {
						return display.getSyncThread() != null;
					}
				});
			}

		});
		display.syncExec(recorderRunnable());
	}

	private Runnable recorderRunnable() {
		return new Runnable() {
			public void run() {
				init();
				hookRecorderListeners();
				createShell();
			}
		};
	}

	protected void init() {
		display = Display.getCurrent();
		loadImages();
	}

	protected void hookRecorderListeners() {
		bot = new SWTBot();
		recorder = new SWTBotRecorder(display, bot);
	}

	private Display waitForDisplayToAppear(long timeOut) {
		long endTime = System.currentTimeMillis() + timeOut;
		while (System.currentTimeMillis() < endTime) {
			Display display = SWTUtils.display();
			if (display != null)
				return display;
			SWTUtils.sleep(100);
		}
		throw new IllegalStateException("Was expecting a display to appear."); //$NON-NLS-1$
	}

	/**
	 * Stop recorder, if it's running.
	 */
	public void stop() {
		if (isRunning())
			recorder.stop();
	}

	public void clear() {
		recorder.clear();
	}

	/**
	 * Clear recorder and start it.
	 */
	public void start() {
		clear();
		unPause();
	}

	/**
	 * Start recorder, if it's not already running.
	 */
	public void unPause() {
		if (!isRunning())
			recorder.start();
	}

	/**
	 * Save recorded script.
	 */
	public void save() {
		String fileName = getSaveFileName();
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(new FileOutputStream(fileName));
			for (Iterator iterator = recorder.getEvents().getActions().iterator(); iterator.hasNext();) {
				SWTBotAction action = (SWTBotAction) iterator.next();
				printWriter.println(action);
			}
			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (printWriter != null)
				printWriter.close();
		}
	}

	public boolean isRunning() {
		return recorder.isRunning();
	}

	protected String getSaveFileName() {
		String recorderFileName = SWTBotPreferences.recorderFileName();
		if (isEmptyOrNull(recorderFileName))
			return new FileDialog(saveButton.getShell(), SWT.SAVE).open();
		return recorderFileName;
	}

	protected boolean isEmptyOrNull(String recorderFileName) {
		return (recorderFileName == null) || recorderFileName.trim().equals(""); //$NON-NLS-1$
	}

}

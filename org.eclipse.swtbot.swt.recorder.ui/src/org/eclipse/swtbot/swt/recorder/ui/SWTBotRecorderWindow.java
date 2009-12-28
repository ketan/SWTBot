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
import org.eclipse.swtbot.swt.finder.utils.StringUtils;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.recorder.SWTBotRecorder;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotAction;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotRecorderWindow {

	public static long			DISPLAY_WAIT_TIMEOUT	= 30 * 1000;
	public static final String	CLEAR					= "clear";
	public static final String	STOP					= "stop";
	public static final String	SAVE					= "save";
	public static final String	START					= "start";
	public static final String	PAUSE					= "pause";

	public static ImageRegistry	imageRegistry;
	private Display				display;
	private Shell				shell;
	private Button				startPauseButton;
	private Button				saveButton;
	private SWTBot				bot;
	private SWTBotRecorder		recorder;
	private Button				clearButton;
	private Button				stopRecorderButton;

	public SWTBotRecorderWindow() {
	}

	public void createWindow() {
		waitForDisplayToAppear(DISPLAY_WAIT_TIMEOUT);
		record(SWTUtils.display());
	}

	private void createShell() {
		shell = new Shell(display, SWT.TITLE | SWT.ON_TOP);
		shell.setLayout(new GridLayout(4, false));
		shell.setText("SWTBot Recorder");

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

	private void createSaveButton() {
		saveButton = new Button(shell, SWT.TOGGLE);
		saveButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		saveButton.setImage(imageRegistry.get(SAVE));
	}

	private void createStartPauseButton() {
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

	private void loadImages() {
		imageRegistry = new ImageRegistry();
		loadImage(START, PAUSE, SAVE, STOP, CLEAR);
	}

	private void loadImage(String... imageNames) {
		for (String imageName : imageNames) {
			InputStream record = getClass().getClassLoader().getResourceAsStream("icons/" + imageName + ".gif");
			imageRegistry.put(imageName, new Image(display, record));
		}
	}

	private void record(final Display display) {
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

	private void init() {
		display = Display.getCurrent();
		loadImages();
	}

	private void hookRecorderListeners() {
		bot = new SWTBot();
		recorder = new SWTBotRecorder(display, bot);
	}

	private void waitForDisplayToAppear(long timeout) {
		SWTUtils.waitForDisplayToAppear(timeout);
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
			for (SWTBotAction action : recorder.getEvents().getActions()) {
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

	private String getSaveFileName() {
		String recorderFileName = SWTBotPreferences.RECORDER_FILE_NAME;
		if (StringUtils.isEmptyOrNull(recorderFileName))
			return new FileDialog(saveButton.getShell(), SWT.SAVE).open();
		return recorderFileName;
	}

}

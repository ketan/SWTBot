package org.eclipse.swtbot.swt.recorder.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.SWT;

public class Bootstrapper {

	private final String	mainClassName;
	private String[]		args;

	public Bootstrapper(String mainClassName, String... args) {
		this.mainClassName = mainClassName;
		this.args = args;
	}

	void start() throws Exception {

		if (isCarbon())
			System.setProperty("org.eclipse.swt.internal.carbon.smallFonts", "");

		Thread recorderThread = recorderThread();

		recorderThread.start();
		executeMainClass();
		recorderThread.join();
	}

	private void executeMainClass() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Class<?> mainClass = Class.forName(this.mainClassName);
		mainClass.getMethod("main", new Class[] { String[].class }).invoke(null, new Object[] { args });
	}

	private Thread recorderThread() {
		Thread recorderThread = new Thread("SWTBotRecorder") {
			public void run() {
				new SWTBotRecorderWindow().createWindow();
			}
		};
		return recorderThread;
	}

	private static boolean isCarbon() {
		return ("carbon".equals(SWT.getPlatform()));
	}
}

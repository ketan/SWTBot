package org.eclipse.swtbot.eclipse.finder.exceptions;

import java.util.Collection;

import org.hamcrest.Matcher;

public class ResultNotUniqueException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResultNotUniqueException() {
		super();
	}

	public ResultNotUniqueException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResultNotUniqueException(String message) {
		super(message);
	}

	public ResultNotUniqueException(Throwable cause) {
		super(cause);
	}

	public ResultNotUniqueException(Matcher<?> matcher,
			Collection<?> allMatches) {
		this("Matcher results not unique "+matcher+" -> "+allMatches);
	}

}

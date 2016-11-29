package org.springboot.quick.commons.exception;

/**
 * Created by chababa on 7/12/16.
 */
public class MissingParameterException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingParameterException() {
	}

	public MissingParameterException(String message) {
		super("Missing paramter:" + message);
	}
}

package com.asiainfo.biapp.mcd.exception;


public class MpmException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1697104571095115994L;

	/**
	 * 
	 */
	public MpmException() {
		super();
	}

	/**
	 * @param message
	 */
	public MpmException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public MpmException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MpmException(String message, Throwable cause) {
		super(message, cause);
	}
}

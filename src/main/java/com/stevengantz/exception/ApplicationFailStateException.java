package com.stevengantz.exception;

/**
 * Runtime exception that is thrown when the application runs into
 * unrecoverable troubles.
 * Created by Steven Gantz on 3/12/2017.
 * 
 */
public class ApplicationFailStateException extends RuntimeException {

	/**
	 * Default serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	public ApplicationFailStateException(String message) {
		super(message);
	}
	
	public ApplicationFailStateException(Exception e) {
		super(e.getMessage());
	}

}

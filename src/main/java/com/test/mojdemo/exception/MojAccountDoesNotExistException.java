package com.test.mojdemo.exception;

/**
 * @author Manglesh Jain
 * Handle exception if account does not exist (while deleting an account)
 */
public class MojAccountDoesNotExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MojAccountDoesNotExistException(String message) {
		super(message);
	}

}


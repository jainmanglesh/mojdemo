package com.test.mojdemo.exception;

/**
 * @author Manglesh Jain
 * Handle exception if account already exists (While saving an account)
 */
public class MojAccountAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MojAccountAlreadyExistsException(String message) {
		super(message);
	}

}


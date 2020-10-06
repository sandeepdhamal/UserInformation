package com.newvision.exception;

/**
 * Custom exception class who throw the exception when user is not found in any
 * case.
 * 
 * @author Sandeep Dhamal
 *
 */
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(Long id) {
		super("User id not found : " + id);
	}

}

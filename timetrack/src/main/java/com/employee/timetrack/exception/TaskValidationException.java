package com.employee.timetrack.exception;

public class TaskValidationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TaskValidationException(String message) {
        super(message);
    }
}


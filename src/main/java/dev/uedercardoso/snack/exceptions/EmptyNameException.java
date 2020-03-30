package dev.uedercardoso.snack.exceptions;

public class EmptyNameException extends RuntimeException {
	
	private static final long serialVersionUID = 642771735254485161L;

	public EmptyNameException() {
		
	}
	
	public EmptyNameException(String message) {
		super(message);
	}

}

package dev.uedercardoso.snack.exceptions;

public class EmptyListException extends RuntimeException {
	
	private static final long serialVersionUID = 6725092114244769708L;

	public EmptyListException() {
		
	}
	
	public EmptyListException(String message) {
		super(message);
	}
	
}

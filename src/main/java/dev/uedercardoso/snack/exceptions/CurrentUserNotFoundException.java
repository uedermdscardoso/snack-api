package dev.uedercardoso.snack.exceptions;

public class CurrentUserNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -5111367139231450633L;

	public CurrentUserNotFoundException() {
		
	}
	
	public CurrentUserNotFoundException(String message) {
		super(message);
	}
	
}

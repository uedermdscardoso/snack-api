package dev.uedercardoso.snack.exceptions;

public class SnackNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 7153549994508789602L;

	public SnackNotFoundException() {
		
	}
	
	public SnackNotFoundException(String message) {
		super(message);
	}

}

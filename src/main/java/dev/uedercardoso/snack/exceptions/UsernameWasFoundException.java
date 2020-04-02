package dev.uedercardoso.snack.exceptions;

public class UsernameWasFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 8723935461673657096L;

	public UsernameWasFoundException() {
		
	}
	
	public UsernameWasFoundException(String message) {
		super(message);
	}
	
}

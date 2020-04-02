package dev.uedercardoso.snack.exceptions;

public class AccessNotAllowedException extends RuntimeException {
	
	private static final long serialVersionUID = -5275064329140305755L;
	
	public AccessNotAllowedException() {
		
	}
	
	public AccessNotAllowedException(String message) {
		super(message);
	}
}

package dev.uedercardoso.snack.exceptions;

public class OrderNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 8694514036918476274L;

	public OrderNotFoundException() {
		
	}
	
	public OrderNotFoundException(String message) {
		super(message);
	}
	
}

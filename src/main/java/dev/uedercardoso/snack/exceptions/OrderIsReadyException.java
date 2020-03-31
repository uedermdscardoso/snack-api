package dev.uedercardoso.snack.exceptions;

public class OrderIsReadyException extends RuntimeException {
	
	private static final long serialVersionUID = -243792683687082022L;

	public OrderIsReadyException() {
		
	}
	
	public OrderIsReadyException(String message) {
		super(message);
	}
}

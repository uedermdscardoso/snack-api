package dev.uedercardoso.snack.exceptions;

public class OrdersServiceException extends Exception {
	
	private static final long serialVersionUID = -1456327240453556908L;

	public OrdersServiceException() {
		
	}
	
	public OrdersServiceException(String message) {
		super(message);
	}
}

package dev.uedercardoso.snack.exceptions;

public class OrderCanceledException extends RuntimeException {
	
	private static final long serialVersionUID = 567038865542328030L;

	public OrderCanceledException(){
		
	}
	
	public OrderCanceledException(String message){
		super(message);
	}

}

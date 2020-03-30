package dev.uedercardoso.snack.exceptions;

public class IngredientNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -6434920516104039268L;

	public IngredientNotFoundException() {
		
	}
	
	public IngredientNotFoundException(String message) {
		super(message);
	}
}

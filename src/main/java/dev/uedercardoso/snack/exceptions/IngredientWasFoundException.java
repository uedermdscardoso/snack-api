package dev.uedercardoso.snack.exceptions;

public class IngredientWasFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -698006003962072552L;

	public IngredientWasFoundException() {
		
	}
	
	public IngredientWasFoundException(String message) {
		super(message);
	}
	
}

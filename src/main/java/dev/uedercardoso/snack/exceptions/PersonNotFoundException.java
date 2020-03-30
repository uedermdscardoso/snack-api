package dev.uedercardoso.snack.exceptions;

public class PersonNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 7098710466314996529L;

	public PersonNotFoundException() {
		
	}
	
	public PersonNotFoundException(String message) {
		super(message);
	}
}

package dev.uedercardoso.snack.exceptions;

public class SnackServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 4420194711631724648L;
	
	public SnackServiceException() {
		
	}
	
	public SnackServiceException(String message) {
		super(message);
	}
	
}

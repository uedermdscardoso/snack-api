package dev.uedercardoso.snack.exceptions;

import java.util.List;

import dev.uedercardoso.snack.domain.model.snack.Snack;

public class SnackWasFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -7266899542270496376L;

	public SnackWasFoundException() {
		
	}
	
	public SnackWasFoundException(String message) {
		super(message);
	}
	
}

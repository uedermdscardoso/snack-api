package dev.uedercardoso.snack.domain.model.snack;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SnackIngredientDTO implements Serializable {
	
	private static final long serialVersionUID = -9052276220902134762L;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	private Ingredient ingredient;
	private Long piece;
	
	public SnackIngredientDTO() {
		
	}
	
	public SnackIngredientDTO(SnackIngredient snackIngredient) {
		this.ingredient = snackIngredient.getIngredient();
		this.piece = snackIngredient.getPiece();
	}
	
}

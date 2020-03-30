package dev.uedercardoso.snack.domain.model.snack;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SnackDTO implements Serializable {
	
	private static final long serialVersionUID = 3346543832021033671L;

	private Long id; 
	private String name;
	
	@JsonIgnore
	private Boolean isCustom;
	
	private Double price;
	private List<SnackIngredientDTO> ingredients;
	
	public SnackDTO() {
		
	}
	
	public SnackDTO(Snack snack, List<SnackIngredientDTO> ingredientsDTO) {
		this.id = snack.getId();
		this.name = snack.getName() != null ? snack.getName() : "";
		this.isCustom = snack.isCustom();
		this.price = snack.getPrice();
		
		this.ingredients = ingredientsDTO;
		
	}
	
}

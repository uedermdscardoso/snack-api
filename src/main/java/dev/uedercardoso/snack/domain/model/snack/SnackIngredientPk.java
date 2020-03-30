package dev.uedercardoso.snack.domain.model.snack;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class SnackIngredientPk implements Serializable {
	
	private static final long serialVersionUID = 4050527416829917199L;

	@Column(name="snack_id",insertable=false,updatable=false)
	private Long snackId; 
	
	@Column(name="ingredient_id",insertable=false,updatable=false)
	private Long ingredientId;
	
	public SnackIngredientPk() {
		
	}
	
	public SnackIngredientPk(Long snackId, Long ingredientId) {
		this.snackId = snackId;
		this.ingredientId = ingredientId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ingredientId == null) ? 0 : ingredientId.hashCode());
		result = prime * result + ((snackId == null) ? 0 : snackId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SnackIngredientPk other = (SnackIngredientPk) obj;
		if (ingredientId == null) {
			if (other.ingredientId != null)
				return false;
		} else if (!ingredientId.equals(other.ingredientId))
			return false;
		if (snackId == null) {
			if (other.snackId != null)
				return false;
		} else if (!snackId.equals(other.snackId))
			return false;
		return true;
	}
		
}

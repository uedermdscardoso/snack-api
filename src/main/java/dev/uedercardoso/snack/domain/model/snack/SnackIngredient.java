package dev.uedercardoso.snack.domain.model.snack;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="snack_ingredient")
@Getter @Setter
public class SnackIngredient implements Serializable {
	
	private static final long serialVersionUID = 6269379676513153052L;

	@EmbeddedId
	private SnackIngredientPk id;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="snack_id",insertable=false, updatable=false)
	private Snack snack;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="ingredient_id",insertable=false, updatable=false)
	private Ingredient ingredient;
	
	@Column(nullable=false,columnDefinition="bigint default 1")
	private Long piece;
	
	public SnackIngredient() {
		
	}
	
	public SnackIngredient(SnackIngredientPk id, Snack snack, Ingredient ingredient, Long piece) {
		this.id = id;
		this.snack = snack;
		this.ingredient = ingredient;
		this.piece = piece;
	}
	
}

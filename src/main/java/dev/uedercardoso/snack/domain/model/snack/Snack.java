package dev.uedercardoso.snack.domain.model.snack;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="snack")
@Getter @Setter
public class Snack implements Serializable {
	
	private static final long serialVersionUID = 3814217833184393259L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 
	
	@Column(length=15)
	private String name;
	
	@Column(name="is_custom")
	private Boolean isCustom;
	
	private Double price;
	
	@OneToMany(mappedBy="snack", cascade=CascadeType.ALL)
	private List<SnackIngredient> items;
	
	public Snack() {
		
	}
	
	public Snack(Boolean isCustom, Double price) {
		this.isCustom = isCustom;
		this.price = price;
	}
	
	public Snack(Boolean isCustom, List<SnackIngredient> items) {
		this.isCustom = isCustom;
		this.items = items;
	}
	
	public Boolean containsIngredientName(final List<Ingredient> list, final String name){
	    return list.stream().map(Ingredient::getName).filter(name::equals).findFirst().isPresent();
	}
	
	public Boolean isCustom() {
		return this.isCustom;
	}
	
}

package dev.uedercardoso.snack.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
	
	@Column(name="is_custom",nullable=false)
	private Boolean isCustom;
	
	@ManyToMany
	@JoinTable(name = "snack_ingredient", 
			  joinColumns = @JoinColumn(name = "snack_id"), 
			  inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
	private List<Ingredient> ingredients;
	
	public Snack() {
		
	}
	
}

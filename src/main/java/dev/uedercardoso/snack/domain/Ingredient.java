package dev.uedercardoso.snack.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="ingredient")
@Getter @Setter
public class Ingredient implements Serializable {
	
	private static final long serialVersionUID = -3891711790163112768L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 
	
	@Column(length=15, nullable=false)
	private String name;
	
	@Column(nullable=false)
	private Double price;
	
	@ManyToMany(mappedBy="ingredients")
	private List<Snack> snacks;
	
	public Ingredient() {
		
	}
	
}

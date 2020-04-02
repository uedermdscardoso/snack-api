package dev.uedercardoso.snack.domain.model.snack;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	@Transient
	private Long piece;
	
	public Ingredient() {
		
	}

	public Ingredient(String name, Double price) {
		this.name = name;
		this.price = price;
	}
	
}

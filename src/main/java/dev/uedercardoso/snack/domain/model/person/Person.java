package dev.uedercardoso.snack.domain.model.person;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.uedercardoso.snack.domain.model.order.Orders;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="person")
@Getter @Setter
public class Person implements Serializable {
	
	private static final long serialVersionUID = -5762188455391479660L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 
	
	@Column(length=50, nullable=false)
	private String fullName;	
	
	@Column(length=12,nullable=false)
	private String username;	
	
	@JsonIgnore	
	@Column(length=12, nullable=false)
	private String password;	
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private PersonProfile profile;
	
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL)
	private List<Orders> orders;
	
	public Person() {
		
	}
	
}

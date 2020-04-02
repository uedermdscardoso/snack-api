package dev.uedercardoso.snack.domain.model.person;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersonDTO implements Serializable {
	
	private static final long serialVersionUID = 4946782212039447288L;
	
	private Long id;
	private String fullName;
	private String username;
	private PersonProfile profile;
	
	public PersonDTO() {
		
	}
	
	public PersonDTO(Person person) {
		this.id = person.getId();
		this.fullName = person.getFullName();
		this.username = person.getUsername(); 
		this.profile = person.getProfile();
	}
}

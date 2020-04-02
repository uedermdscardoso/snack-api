package dev.uedercardoso.snack.domain.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.uedercardoso.snack.domain.model.person.Person;
import dev.uedercardoso.snack.domain.model.person.PersonDTO;
import dev.uedercardoso.snack.domain.model.person.PersonProfile;
import dev.uedercardoso.snack.domain.repositories.PersonRepository;
import dev.uedercardoso.snack.exceptions.AccessNotAllowedException;
import dev.uedercardoso.snack.exceptions.CurrentUserNotFoundException;
import dev.uedercardoso.snack.exceptions.EmptyListException;
import dev.uedercardoso.snack.exceptions.PersonNotFoundException;
import dev.uedercardoso.snack.exceptions.UsernameWasFoundException;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public void save(List<Person> person) {
		
		for(Person p : person) {

			if(this.personRepository.existsByUsername(p.getUsername()))
				throw new UsernameWasFoundException("O nome do usuário "+p.getUsername()+" já existe.");
			
			p.setPassword(this.encryptPassword(p.getPassword()));	
		}
		
		this.personRepository.saveAll(person);
	}
	
	public void save(Long id, Person person, String currentUsername) {
		
		if(!this.personRepository.existsByUsernameAndProfile(currentUsername, PersonProfile.ADMIN) && !this.personRepository.existsByIdAndUsername(id, currentUsername))
			throw new AccessNotAllowedException("O usuário "+currentUsername+" não pode acessar o pedido "+id+" porque este pedido não foi feito por ele");
		
		if(!this.personRepository.existsByUsername(currentUsername))
			throw new CurrentUserNotFoundException("O usuário atual "+currentUsername+" não foi encontrado");
		
		Person current = this.personRepository.findById(id).get();
		
		if(!person.getUsername().equals(current.getUsername()) && this.personRepository.existsByUsername(person.getUsername()))
			throw new UsernameWasFoundException("O nome do usuário "+person.getUsername()+" já existe.");
		
		String password = this.encryptPassword(current.getPassword());
		person = new Person(id, person, password);
		
		this.personRepository.saveAndFlush(person);
	}
	
	public List<PersonDTO> getAllPersons(){
		
		List<Person> persons = this.personRepository.findAll();
		List<PersonDTO> personsDTO = new LinkedList<PersonDTO>();
		
		if(persons == null || persons.size() == 0)
			throw new EmptyListException("Lista vazia");
		
		for(Person person : persons) {
			personsDTO.add(new PersonDTO(person));
		}
		
		return personsDTO;
	}

	public PersonDTO getPersonById(Long id, String currentUsername){
		if(!this.personRepository.existsById(id))
			throw new PersonNotFoundException("Pessoa "+id+" não encontrado");
		
		if(!this.personRepository.existsByUsername(currentUsername))
			throw new CurrentUserNotFoundException("O usuário atual "+currentUsername+" não foi encontrado");

		if(!this.personRepository.existsByUsernameAndProfile(currentUsername, PersonProfile.ADMIN) && !this.personRepository.existsByIdAndUsername(id, currentUsername))
			throw new AccessNotAllowedException("O usuário "+currentUsername+" só pode atualizar os seus próprios dados.");
		
		Person person = this.personRepository.findById(id).get();
		PersonDTO personDTO = new PersonDTO(person);
		return personDTO;
	}
	
	public void delete(Long id, String currentUsername) {
		
		if(!this.personRepository.existsById(id))
			throw new PersonNotFoundException("Pessoa "+id+" não encontrada");
		
		if(!this.personRepository.existsByUsername(currentUsername))
			throw new CurrentUserNotFoundException("O usuário atual "+currentUsername+" não foi encontrado");
		
		if(!this.personRepository.existsByUsernameAndProfile(currentUsername, PersonProfile.ADMIN) && !this.personRepository.existsByIdAndUsername(id, currentUsername))
			throw new AccessNotAllowedException("O usuário "+currentUsername+" só pode excluir ele mesmo");
		
		this.personRepository.deleteById(id);
	}
	
	public String encryptPassword(String password) {
		PasswordEncoder p = new BCryptPasswordEncoder();
		return p.encode(password);
	}
	
}

package dev.uedercardoso.snack.domain.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.uedercardoso.snack.domain.model.person.Person;
import dev.uedercardoso.snack.domain.model.person.PersonDTO;
import dev.uedercardoso.snack.domain.repositories.PersonRepository;
import dev.uedercardoso.snack.exceptions.EmptyListException;
import dev.uedercardoso.snack.exceptions.PersonNotFoundException;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public void save(List<Person> persons) {
		
		for(Person person : persons) {
			person.setPassword(this.encryptPassword(person.getPassword()));	
		}
		
		this.personRepository.saveAll(persons);
	}
	
	public void save(Long id, Person person) {
		this.personRepository.saveAndFlush(person);
	}
	
	public List<PersonDTO> getAllPersons(){
		List<Person> persons = this.personRepository.findAll();
		List<PersonDTO> personsDTO = new LinkedList<PersonDTO>();
		for(Person person : persons) {
			personsDTO.add(new PersonDTO(person));
		}
		if(personsDTO == null || personsDTO.size() == 0)
			throw new EmptyListException("Lista vazia");
		
		return personsDTO;
	}

	public PersonDTO getPersonById(Long id){
		Person person = this.personRepository.findById(id).get();
		if(person == null)
			throw new EmptyListException("Pessoa "+id+" não encontrado");
		return new PersonDTO(person);
	}
	
	public void delete(Long id) {
		
		if(!this.personRepository.existsById(id))
			throw new PersonNotFoundException("Pessoa "+id+" não encontrada");
		
		this.personRepository.deleteById(id);
	}
	
	public String encryptPassword(String password) {
		PasswordEncoder p = new BCryptPasswordEncoder();
		return p.encode(password);
	}
	
}

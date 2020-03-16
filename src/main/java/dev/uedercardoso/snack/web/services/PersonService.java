package dev.uedercardoso.snack.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.uedercardoso.snack.domain.Ingredient;
import dev.uedercardoso.snack.domain.Person;
import dev.uedercardoso.snack.web.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public void save(Person person) {
		this.personRepository.save(person);
	}
	
	public void save(Long id, Person person) {
		this.personRepository.saveAndFlush(person);
	}
	
	public List<Person> getAllPersons(){
		return this.personRepository.findAll();
	}
	
	public void delete(Long id) {
		this.personRepository.deleteById(id);
	}
	
}

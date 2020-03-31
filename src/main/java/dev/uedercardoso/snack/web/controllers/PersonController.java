package dev.uedercardoso.snack.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.uedercardoso.snack.domain.model.person.Person;
import dev.uedercardoso.snack.domain.model.person.PersonDTO;
import dev.uedercardoso.snack.domain.services.PersonService;
import dev.uedercardoso.snack.exceptions.PersonNotFoundException;

@RestController
@RequestMapping("/persons")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<PersonDTO>> findAll(){
		try {
			
			List<PersonDTO> persons = this.personService.getAllPersons();
			
			return ResponseEntity.ok(persons);
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
	public ResponseEntity<PersonDTO> findById(@PathVariable Long id){
		try {
			
			PersonDTO person = this.personService.getPersonById(id);
			
			return ResponseEntity.ok(person);
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
	public ResponseEntity<Void> save(@Valid @RequestBody List<Person> person){
		try {
			
			this.personService.save(person);
			
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
	public ResponseEntity<Void> saveById(@PathVariable Long id, @Valid @RequestBody Person person){
		try {
			
			this.personService.save(id,person);
			
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		try {
			
			this.personService.delete(id);
			
			return ResponseEntity.ok().build();
			
		} catch(PersonNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
}

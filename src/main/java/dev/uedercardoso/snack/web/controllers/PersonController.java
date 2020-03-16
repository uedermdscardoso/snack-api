package dev.uedercardoso.snack.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.uedercardoso.snack.domain.Person;
import dev.uedercardoso.snack.web.services.PersonService;

@RestController
@RequestMapping("/persons")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@GetMapping
	public ResponseEntity<List<Person>> findAll(){
		try {
			
			List<Person> persons = this.personService.getAllPersons();
			
			return ResponseEntity.ok(persons);
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Void> save(@Valid @RequestBody Person person){
		try {
			
			this.personService.save(person);
			
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> saveById(@PathVariable Long id, @Valid @RequestBody Person person){
		try {
			
			this.personService.save(id,person);
			
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		try {
			
			this.personService.delete(id);
			
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
}

package dev.uedercardoso.snack.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import dev.uedercardoso.snack.exceptions.AccessNotAllowedException;
import dev.uedercardoso.snack.exceptions.CurrentUserNotFoundException;
import dev.uedercardoso.snack.exceptions.PersonNotFoundException;
import dev.uedercardoso.snack.exceptions.UsernameWasFoundException;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<PersonDTO>> findAll(){
		try {
			
			List<PersonDTO> personsDTO = this.personService.getAllPersons();
			
			return ResponseEntity.ok(personsDTO);
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
	public ResponseEntity<PersonDTO> findById(HttpServletRequest request, @PathVariable Long id){
		try {
			
			String currentUsername = request.getUserPrincipal().getName();
			
			PersonDTO personDTO = this.personService.getPersonById(id, currentUsername);
			
			return ResponseEntity.ok(personDTO);
			
		} catch(AccessNotAllowedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch(CurrentUserNotFoundException | PersonNotFoundException e) {
			return ResponseEntity.notFound().build();
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
			
		} catch(UsernameWasFoundException  e) {
			return ResponseEntity.status(HttpStatus.FOUND).build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
	public ResponseEntity<Void> saveById(HttpServletRequest request, @PathVariable Long id, @Valid @RequestBody Person person){
		try {
			
			String currentUsername = request.getUserPrincipal().getName();
			
			this.personService.save(id,person, currentUsername);
			
			return ResponseEntity.ok().build();
			
		} catch(AccessNotAllowedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch(CurrentUserNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(UsernameWasFoundException e) {
			return ResponseEntity.status(HttpStatus.FOUND).build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
	public ResponseEntity<Void> delete(HttpServletRequest request, @PathVariable Long id){
		try {
			
			String currentUsername = request.getUserPrincipal().getName();
			
			this.personService.delete(id, currentUsername);
			
			return ResponseEntity.ok().build();
			
		} catch(AccessNotAllowedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch(CurrentUserNotFoundException | PersonNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
}

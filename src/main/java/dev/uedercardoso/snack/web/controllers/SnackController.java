package dev.uedercardoso.snack.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.uedercardoso.snack.domain.Snack;
import dev.uedercardoso.snack.exceptions.EmptyListException;
import dev.uedercardoso.snack.web.services.SnackService;

@RestController
@RequestMapping("/snacks")
public class SnackController {

	@Autowired
	private SnackService snackService;
	
	@GetMapping
	public ResponseEntity<List<Snack>> findAll(){
		try {
			List<Snack> snacks = this.snackService.getAllSnacks();
			
			return ResponseEntity.ok(snacks);
			
		} catch(EmptyListException e) {
			return ResponseEntity.noContent().build();	
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Void> save(@Valid @RequestBody List<Snack> snacks){
		try {
			
			this.snackService.save(snacks);
			
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> saveById(@RequestBody Long id, @Valid @RequestBody Snack snack){
		try {
			
			this.snackService.save(id,snack);
			
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@RequestParam Long id){
		try {
			
			this.snackService.delete(id);
			
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
}

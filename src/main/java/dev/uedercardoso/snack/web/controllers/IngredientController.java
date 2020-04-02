package dev.uedercardoso.snack.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.uedercardoso.snack.domain.model.snack.Ingredient;
import dev.uedercardoso.snack.domain.services.IngredientService;
import dev.uedercardoso.snack.exceptions.EmptyListException;
import dev.uedercardoso.snack.exceptions.IngredientNotFoundException;
import dev.uedercardoso.snack.exceptions.IngredientWasFoundException;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<Ingredient>> findAll(){
		try {
			List<Ingredient> ingredients = this.ingredientService.getAllIngredients();
		
			return ResponseEntity.ok(ingredients);
			
		} catch(EmptyListException e) {
			return ResponseEntity.noContent().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> save(@Valid @RequestBody List<Ingredient> ingredients) {
		try {
			
			this.ingredientService.save(ingredients);
			
			return ResponseEntity.ok().build();
			
		} catch(IngredientWasFoundException e) {
			return ResponseEntity.status(HttpStatus.FOUND).build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		try {
			this.ingredientService.delete(id);
			
			return ResponseEntity.ok().build();
			
		} catch(IngredientNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
	
}

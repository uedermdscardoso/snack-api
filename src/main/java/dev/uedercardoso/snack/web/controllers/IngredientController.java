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

import dev.uedercardoso.snack.domain.Ingredient;
import dev.uedercardoso.snack.web.services.IngredientService;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;
	
	@GetMapping
	public ResponseEntity<List<Ingredient>> findAll(){
		try {
			List<Ingredient> ingredients = this.ingredientService.getAllIngredients();
		
			return ResponseEntity.ok(ingredients);
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Void> save(@Valid @RequestBody Ingredient ingredient) {
		try {
			
			this.ingredientService.save(ingredient);
			
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> saveById(@PathVariable Long id, @Valid @RequestBody Ingredient ingredient) {
		try {
			this.ingredientService.save(id, ingredient);
			
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		try {
			this.ingredientService.delete(id);
			
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
}

package dev.uedercardoso.snack.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.uedercardoso.snack.domain.model.snack.Ingredient;
import dev.uedercardoso.snack.domain.repositories.IngredientRepository;
import dev.uedercardoso.snack.exceptions.IngredientNotFoundException;
import dev.uedercardoso.snack.exceptions.SnackNotFoundException;

@Service
public class IngredientService {

	@Autowired
	private IngredientRepository ingredientRepository;
	
	public void save(List<Ingredient> ingredients) {
		this.ingredientRepository.saveAll(ingredients);
	}
	
	public void save(Long id, Ingredient ingredient) {
		this.ingredientRepository.saveAndFlush(ingredient);
	}
	
	public List<Ingredient> getAllIngredients(){
		return this.ingredientRepository.findAll();
	}
	
	public void delete(Long id) {
		
		if(!this.ingredientRepository.existsById(id))
			throw new IngredientNotFoundException("Ingrediente "+id+" não encontrado");
		
		this.ingredientRepository.deleteById(id);
	}
	
}

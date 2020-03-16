package dev.uedercardoso.snack.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.uedercardoso.snack.domain.Ingredient;
import dev.uedercardoso.snack.web.repositories.IngredientRepository;

@Service
public class IngredientService {

	@Autowired
	private IngredientRepository ingredientRepository;
	
	public void save(Ingredient ingredient) {
		this.ingredientRepository.saveAndFlush(ingredient);
	}
	
	public void save(Long id, Ingredient ingredient) {
		this.ingredientRepository.saveAndFlush(ingredient);
	}
	
	public List<Ingredient> getAllIngredients(){
		return this.ingredientRepository.findAll();
	}
	
	public void delete(Long id) {
		this.ingredientRepository.deleteById(id);
	}
	
}

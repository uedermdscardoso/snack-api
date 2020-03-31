package dev.uedercardoso.snack.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.uedercardoso.snack.domain.model.snack.Ingredient;
import dev.uedercardoso.snack.domain.repositories.IngredientRepository;
import dev.uedercardoso.snack.domain.repositories.SnackRepository;
import dev.uedercardoso.snack.exceptions.IngredientNotFoundException;
import dev.uedercardoso.snack.exceptions.OrdersServiceException;

@Service
public class IngredientService {

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private SnackRepository SnackRepository;
	
	public void save(List<Ingredient> ingredients) {
		this.ingredientRepository.saveAll(ingredients);
	}
	
	public void save(Long id, Ingredient ingredient) {
		this.ingredientRepository.saveAndFlush(ingredient);
	}
	
	public List<Ingredient> getAllIngredients(){
		return this.ingredientRepository.findAll();
	}
	
	public void delete(Long id) throws OrdersServiceException {
		
		if(!this.ingredientRepository.existsById(id))
			throw new IngredientNotFoundException("Ingrediente "+id+" não encontrado");
		
		Ingredient ingredient = this.ingredientRepository.findById(id).get();
		
		if(this.SnackRepository.existsBySnackByIngredient(ingredient))
			throw new OrdersServiceException("O ingredient "+id+" não pode ser excluido porque existem lanches que são compostos pelo "+ingredient.getName()); 
			
		this.ingredientRepository.deleteById(id);
	}
	
}

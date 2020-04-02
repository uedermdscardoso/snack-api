package dev.uedercardoso.snack.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.uedercardoso.snack.domain.model.snack.Ingredient;
import dev.uedercardoso.snack.domain.repositories.IngredientRepository;
import dev.uedercardoso.snack.domain.repositories.SnackIngredientRepository;
import dev.uedercardoso.snack.domain.repositories.SnackRepository;
import dev.uedercardoso.snack.exceptions.EmptyListException;
import dev.uedercardoso.snack.exceptions.IngredientNotFoundException;
import dev.uedercardoso.snack.exceptions.IngredientWasFoundException;
import dev.uedercardoso.snack.exceptions.OrdersServiceException;

@Service
public class IngredientService {

	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private SnackIngredientRepository snackIngredientRepository;

	@Autowired
	private SnackRepository snackRepository;
	
	public void save(List<Ingredient> ingredients) {
		
		for(Ingredient ingredient : ingredients) {
			if(this.ingredientRepository.existsByName(ingredient.getName().trim())) 
				throw new IngredientWasFoundException("O ingredient "+ingredient.getName().trim()+" já existe.");
		}
		
		this.ingredientRepository.saveAll(ingredients);
	}
	
	public List<Ingredient> getAllIngredients(){
		List<Ingredient> ingredients = this.ingredientRepository.findAll();
		if(ingredients == null || ingredients.size() == 0)
			throw new EmptyListException("Lista vazia");
		
		return ingredients;
	}
	
	public void delete(Long id) throws OrdersServiceException {
		
		if(!this.ingredientRepository.existsById(id))
			throw new IngredientNotFoundException("Ingrediente "+id+" não encontrado");
		
		Ingredient ingredient = this.ingredientRepository.findById(id).get();
		
		if(this.snackIngredientRepository.existsByIngredient(ingredient))
			throw new OrdersServiceException("O ingredient "+id+" não pode ser excluido porque existem lanches que são compostos pelo "+ingredient.getName()); 
			
		this.ingredientRepository.deleteById(id);
	}
	
}

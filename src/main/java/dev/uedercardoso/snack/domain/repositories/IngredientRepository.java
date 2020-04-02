package dev.uedercardoso.snack.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.uedercardoso.snack.domain.model.snack.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>{

	Boolean existsByName(String name);
	
}

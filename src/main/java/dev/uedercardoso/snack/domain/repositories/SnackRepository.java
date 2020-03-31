package dev.uedercardoso.snack.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.uedercardoso.snack.domain.model.snack.Ingredient;
import dev.uedercardoso.snack.domain.model.snack.Snack;

public interface SnackRepository extends JpaRepository<Snack, Long>{

	@Query("SELECT "
			+ "CASE WHEN COUNT(s) > 0 THEN true ELSE false END"
			+ " FROM Snack s INNER JOIN SnackIngredient si ON si IN s.items WHERE si.ingredient = :ingredient")
	Boolean existsBySnackByIngredient(Ingredient ingredient);
	
}

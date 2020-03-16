package dev.uedercardoso.snack.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.uedercardoso.snack.domain.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>{

}

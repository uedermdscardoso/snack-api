package dev.uedercardoso.snack.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.uedercardoso.snack.domain.model.snack.SnackIngredient;
import dev.uedercardoso.snack.domain.model.snack.SnackIngredientPk;

public interface SnackIngredientRepository extends JpaRepository<SnackIngredient, SnackIngredientPk> {

}

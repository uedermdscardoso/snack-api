package dev.uedercardoso.snack.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.uedercardoso.snack.domain.model.snack.Snack;

public interface SnackRepository extends JpaRepository<Snack, Long>{

	Boolean existsByName(String name);
	Boolean existsByIdAndIsCustom(Long id, Boolean isCustom);
	
}

package dev.uedercardoso.snack.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.uedercardoso.snack.domain.Snack;

public interface SnackRepository extends JpaRepository<Snack, Long>{

}

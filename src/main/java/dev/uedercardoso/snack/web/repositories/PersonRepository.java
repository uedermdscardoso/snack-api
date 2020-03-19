package dev.uedercardoso.snack.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.uedercardoso.snack.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

	Person findByUsername(String username);

}

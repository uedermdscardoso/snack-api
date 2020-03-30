package dev.uedercardoso.snack.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.uedercardoso.snack.domain.model.person.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

	Person findByUsername(String username);

}

package dev.uedercardoso.snack.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.uedercardoso.snack.domain.model.person.Person;
import dev.uedercardoso.snack.domain.model.person.PersonProfile;

public interface PersonRepository extends JpaRepository<Person, Long>{

	Person findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByUsernameAndProfile(String username, PersonProfile profile);
	Boolean existsByIdAndUsername(Long id, String username);

}

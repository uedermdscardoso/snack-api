package dev.uedercardoso.snack.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.uedercardoso.snack.domain.model.order.Orders;
import dev.uedercardoso.snack.domain.model.order.TypeStatus;
import dev.uedercardoso.snack.domain.model.person.Person;

public interface OrdersRepository extends JpaRepository<Orders, Long>{

	List<Orders> findByPerson(Person person);
	
	Boolean existsByIdAndStatus(Long id, TypeStatus status);
	
	@Query("SELECT "
			+ "CASE WHEN COUNT(o) > 0 THEN true ELSE false END"
			+ " FROM Orders o INNER JOIN Person p ON p.id = o.person.id WHERE (o.id = :id) AND (p.username = :username)")
	Boolean existsByIdAndPersonUsername(@Param("id") Long id, @Param("username") String username);
	
}

package dev.uedercardoso.snack.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.uedercardoso.snack.domain.model.order.Orders;
import dev.uedercardoso.snack.domain.model.person.Person;

public interface OrdersRepository extends JpaRepository<Orders, Long>{

	List<Orders> findByPerson(Person person);
	
}

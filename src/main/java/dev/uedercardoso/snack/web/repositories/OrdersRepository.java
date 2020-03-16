package dev.uedercardoso.snack.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.uedercardoso.snack.domain.Orders;
import dev.uedercardoso.snack.domain.Person;

public interface OrdersRepository extends JpaRepository<Orders, Long>{

	Orders findByPerson(Person person);
	
}

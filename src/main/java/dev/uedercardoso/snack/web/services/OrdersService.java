package dev.uedercardoso.snack.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.uedercardoso.snack.domain.Orders;
import dev.uedercardoso.snack.domain.Person;
import dev.uedercardoso.snack.exceptions.EmptyListException;
import dev.uedercardoso.snack.web.repositories.OrdersRepository;
import dev.uedercardoso.snack.web.repositories.PersonRepository;

@Service
public class OrdersService {

	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private PersonRepository personRepository;

	public void save(Orders order) {
		this.ordersRepository.saveAndFlush(order);
	}
	
	public Orders getOrders(Long id){
		Person person = this.personRepository.findById(id).get();
		
		Orders order = this.ordersRepository.findByPerson(person);
		
		return order;
	}
	
	public List<Orders> getAllOrders(){
		List<Orders> orders = this.ordersRepository.findAll();
		if(orders == null || orders.size() == 0) {
			throw new EmptyListException("Lista vazia");
		}
		return orders;
	}
	
	public void delete(Long id) {
		this.ordersRepository.deleteById(id);
	}
	
}

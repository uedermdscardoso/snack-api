package dev.uedercardoso.snack.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.uedercardoso.snack.domain.Ingredient;
import dev.uedercardoso.snack.domain.Item;
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
		
		
		if(order.getItems() != null || order.getItems().size() > 0) {
			
			Double priceItem = 0d;
			for(Item item : order.getItems()) {
				for(Ingredient ingredient : item.getSnack().getIngredients()	) {
					priceItem += ingredient.getPrice();
					
				}
			}
			
//			for(Item item : order.getItems()) {
//				if(item.getIsCustom()) {
//					this.saveCustomItems(order);
//				}	
//			}
		}
		
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

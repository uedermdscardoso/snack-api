package dev.uedercardoso.snack.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.uedercardoso.snack.domain.model.order.Orders;
import dev.uedercardoso.snack.domain.model.order.OrdersDTO;
import dev.uedercardoso.snack.domain.services.OrdersService;

@RestController
@RequestMapping("/orders")
public class OrdersController {
	
	@Autowired
	private OrdersService ordersService;
	
	@GetMapping
	public ResponseEntity<List<OrdersDTO>> findAll(){
		try {
			
			List<OrdersDTO> ordersDTO = this.ordersService.getAllOrders();
			
			return ResponseEntity.ok(ordersDTO);
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrdersDTO> findById(@PathVariable Long id){
		try {
			
			OrdersDTO orderDTO = this.ordersService.getOrdersById(id);
			
			return ResponseEntity.ok(orderDTO);
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/person/{id}")
	public ResponseEntity<List<OrdersDTO>> findByPersonId(@PathVariable Long id){
		try {
			
			List<OrdersDTO> ordersDTO = this.ordersService.getOrdersByPersonId(id);
			
			return ResponseEntity.ok(ordersDTO);
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Void> save(@Valid @RequestBody Orders order){
		
		try {

			this.ordersService.checkItems(order);
			this.ordersService.save(order);
			
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		try {
			
			this.ordersService.delete(id);
			
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
}

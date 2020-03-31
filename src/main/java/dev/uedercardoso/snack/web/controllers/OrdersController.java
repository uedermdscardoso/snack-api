package dev.uedercardoso.snack.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.uedercardoso.snack.domain.model.order.Orders;
import dev.uedercardoso.snack.domain.model.order.OrdersDTO;
import dev.uedercardoso.snack.domain.services.OrdersService;
import dev.uedercardoso.snack.exceptions.OrderCanceledException;
import dev.uedercardoso.snack.exceptions.OrderIsReadyException;
import dev.uedercardoso.snack.exceptions.OrderNotFoundException;

@RestController
@RequestMapping("/orders")
public class OrdersController {
	
	@Autowired
	private OrdersService ordersService;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<OrdersDTO>> findAll(){
		try {
			
			List<OrdersDTO> ordersDTO = this.ordersService.getAllOrders();
			
			return ResponseEntity.ok(ordersDTO);
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
	public ResponseEntity<OrdersDTO> findById(@PathVariable Long id){
		try {
			
			OrdersDTO orderDTO = this.ordersService.getOrdersById(id);
			
			return ResponseEntity.ok(orderDTO);
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/person/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
	public ResponseEntity<List<OrdersDTO>> findByPersonId(@PathVariable Long id){
		try {
			
			List<OrdersDTO> ordersDTO = this.ordersService.getOrdersByPersonId(id);
			
			return ResponseEntity.ok(ordersDTO);
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
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
	
	@PutMapping("/{id}/cancel")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
	public ResponseEntity<Void> cancelOrder(@PathVariable Long id){
		try {
			
			this.ordersService.cancelOrder(id);
			
			return ResponseEntity.ok().build();
			
		} catch(OrderIsReadyException | OrderCanceledException e) {
			return ResponseEntity.noContent().build();
		} catch(OrderNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("{id}/ready")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> changeStatusToReady(@PathVariable Long id){
		try {
			
			this.ordersService.changeStatusToReady(id);;
			
			return ResponseEntity.ok().build();
			
		} catch(OrderIsReadyException | OrderCanceledException e) {
			return ResponseEntity.noContent().build();
		} catch(OrderNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
}

package dev.uedercardoso.snack.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import dev.uedercardoso.snack.exceptions.AccessNotAllowedException;
import dev.uedercardoso.snack.exceptions.CurrentUserNotFoundException;
import dev.uedercardoso.snack.exceptions.EmptyListException;
import dev.uedercardoso.snack.exceptions.OrderCanceledException;
import dev.uedercardoso.snack.exceptions.OrderIsReadyException;
import dev.uedercardoso.snack.exceptions.OrderNotFoundException;
import dev.uedercardoso.snack.exceptions.PersonNotFoundException;
import dev.uedercardoso.snack.exceptions.SnackNotFoundException;

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
			
		} catch(EmptyListException e) {
			return ResponseEntity.noContent().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
	public ResponseEntity<OrdersDTO> findById(HttpServletRequest request, @PathVariable Long id){
		try {
			String currentUsername = request.getUserPrincipal().getName();
			
			OrdersDTO orderDTO = this.ordersService.getOrdersById(id,currentUsername);
			
			return ResponseEntity.ok(orderDTO);
			
		} catch(AccessNotAllowedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch(CurrentUserNotFoundException | OrderNotFoundException e) { 
			return ResponseEntity.notFound().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/my")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
	public ResponseEntity<List<OrdersDTO>> findByPersonId(HttpServletRequest  request){
		try {
			
			String username = request.getUserPrincipal().getName();
			List<OrdersDTO> ordersDTO = this.ordersService.getMyOrders(username);
			
			return ResponseEntity.ok(ordersDTO);
			
		} catch(PersonNotFoundException e) {
			return ResponseEntity.notFound().build();
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
			
		} catch(SnackNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("/{id}/cancel")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
	public ResponseEntity<Void> cancelOrder(HttpServletRequest request, @PathVariable Long id){
		try {
			
			String currentUsername = request.getUserPrincipal().getName();
			
			this.ordersService.cancelOrder(id, currentUsername);
			
			return ResponseEntity.ok().build();
			
		} catch(AccessNotAllowedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch(OrderIsReadyException | OrderCanceledException e) {
			return ResponseEntity.noContent().build();
		} catch(OrderNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("/{id}/ready")
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

package dev.uedercardoso.snack.domain.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import dev.uedercardoso.snack.domain.model.order.Item;
import dev.uedercardoso.snack.domain.model.order.ItemDTO;
import dev.uedercardoso.snack.domain.model.order.ItemPk;
import dev.uedercardoso.snack.domain.model.order.Orders;
import dev.uedercardoso.snack.domain.model.order.OrdersDTO;
import dev.uedercardoso.snack.domain.model.order.TypeStatus;
import dev.uedercardoso.snack.domain.model.person.Person;
import dev.uedercardoso.snack.domain.model.person.PersonDTO;
import dev.uedercardoso.snack.domain.model.snack.Ingredient;
import dev.uedercardoso.snack.domain.model.snack.Snack;
import dev.uedercardoso.snack.domain.model.snack.SnackDTO;
import dev.uedercardoso.snack.domain.model.snack.SnackIngredient;
import dev.uedercardoso.snack.domain.model.snack.SnackIngredientDTO;
import dev.uedercardoso.snack.domain.model.snack.SnackIngredientPk;
import dev.uedercardoso.snack.domain.repositories.ItemRepository;
import dev.uedercardoso.snack.domain.repositories.OrdersRepository;
import dev.uedercardoso.snack.domain.repositories.PersonRepository;
import dev.uedercardoso.snack.domain.repositories.SnackIngredientRepository;
import dev.uedercardoso.snack.domain.repositories.SnackRepository;
import dev.uedercardoso.snack.exceptions.EmptyListException;
import dev.uedercardoso.snack.exceptions.EmptyNameException;
import dev.uedercardoso.snack.exceptions.OrderCanceledException;
import dev.uedercardoso.snack.exceptions.OrderIsReadyException;
import dev.uedercardoso.snack.exceptions.OrderNotFoundException;
import dev.uedercardoso.snack.exceptions.OrdersServiceException;

@Service
public class OrdersService {

	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private SnackRepository snackRepository;

	@Autowired
	private SnackIngredientRepository snackIngredientRepository;
	
	@Autowired
	private ItemRepository itemRepository;

	public void save(Orders order) throws OrdersServiceException {
		
		int count = 0; 
		
		for(Item item : order.getItems()) {

			count++;
			
			if(order.getItems() == null || order.getItems().size() == 0)
				throw new EmptyListException("Items não foram preenchidos");
			
			if(item.getIsCustom()) {
				
				if(item.getSnack().getName() != null && !item.getSnack().getName().isEmpty())
					throw new EmptyNameException("O nome não pode ser preenchido para lanches personalizados.");

				Snack snackCustom = item.getSnack();
				snackCustom.setIsCustom(item.getIsCustom());
				snackCustom.setPrice(this.calcSnackPrice(snackCustom));
				snackCustom = this.snackRepository.saveAndFlush(snackCustom);
				item.setSnack(snackCustom);

				for(SnackIngredient item2 : item.getSnack().getItems()) {
					Ingredient ingredient = item2.getIngredient();
					SnackIngredientPk pk = new SnackIngredientPk(item.getSnack().getId(), ingredient.getId());
					Long piece = item2.getPiece() != null && item2.getPiece() > 0 ? item2.getPiece() : 1l;
					item2 = this.snackIngredientRepository.saveAndFlush(new SnackIngredient(pk, piece));
				}
				
			} else {
				Optional<Snack> opSnack = this.snackRepository.findById(item.getSnack().getId());
				if(opSnack.isPresent())
					item.setSnack(opSnack.get());
				
				Long quantity = (long) order.getItems().size();
				item.setQuantity(quantity);		
			}

			
			item.setPrice(this.calcSnackPrice(item.getSnack()));
			item.setDiscount(this.calcDiscount(item.getSnack()));
			
			Optional<Person> opPerson = this.personRepository.findById(order.getPerson().getId());
			if(!opPerson.isPresent())
				throw new OrdersServiceException("Pessoa com o id "+order.getPerson().getId());
			
			
			Double totalPrice = this.calcTotalPrice(order.getItems()); 
			Double totalDiscount = this.calcDiscountPrice(order.getItems());
			
			order = new Orders(order, totalPrice, totalDiscount, opPerson.get());
			
			if(count == order.getItems().size())
				order = this.ordersRepository.saveAndFlush(order);
			
			if(order.getId() != null || order.getId() > 0) {
				ItemPk pk = new ItemPk(order.getId(),item.getSnack().getId());
				this.itemRepository.saveAndFlush(new Item(pk, item));
			}				
			
		}
		
	}
	
	public OrdersDTO getOrdersById(Long id){
		
		Orders order = this.ordersRepository.findById(id).get();
		List<Orders> orders = new LinkedList<Orders>(); 
		orders.add(order);
		
		List<OrdersDTO> ordersDTO = this.convertOrdersToOrdersDTO(orders);
		
		return ordersDTO.get(0);
	}
	
	public List<OrdersDTO> getOrdersByPersonId(Long id){
		Person person = this.personRepository.findById(id).get();
		
		List<Orders> orders = this.ordersRepository.findByPerson(person);
		List<OrdersDTO> ordersDTO = this.convertOrdersToOrdersDTO(orders);
		
		return ordersDTO;
	}
	
	public List<OrdersDTO> getAllOrders(){
		
		List<Orders> orders = this.ordersRepository.findAll();
		List<OrdersDTO> ordersDTO = this.convertOrdersToOrdersDTO(orders);
		
		if(ordersDTO == null || ordersDTO.size() == 0) {
			throw new EmptyListException("Lista vazia");
		}
		return ordersDTO;
	}
	
	private List<OrdersDTO> convertOrdersToOrdersDTO(List<Orders> orders) {
		List<OrdersDTO> ordersDTO = new LinkedList<OrdersDTO>();
		List<ItemDTO> itemDTO = new LinkedList<ItemDTO>();
		List<SnackIngredientDTO> item2DTO = new LinkedList<SnackIngredientDTO>();
				
		for(Orders order : orders) {
			for(Item item : order.getItems()) {
				for(SnackIngredient item2 : item.getSnack().getItems()) {
					item2DTO.add(new SnackIngredientDTO(item2));
				}
				itemDTO.add(new ItemDTO(item, new SnackDTO(item.getSnack(), item2DTO)));
			}
			ordersDTO.add(new OrdersDTO(order, new PersonDTO(order.getPerson()), itemDTO));
		}
		return ordersDTO;
	}
	
	public void cancelOrder(Long id) {
		if(!this.ordersRepository.existsById(id))
			throw new OrderNotFoundException("Pedido "+id+" não encontrado");
		
		if(this.ordersRepository.existsByIdAndStatus(id, TypeStatus.READY))
			throw new OrderIsReadyException("O pedido "+id+" não pode ser cancelado porque já está pronto");
		
		if(this.ordersRepository.existsByIdAndStatus(id, TypeStatus.CANCELED))
			throw new OrderCanceledException("O pedido "+id+" já foi cancelado");

		this.changeStatus(id, TypeStatus.CANCELED);
	}
	
	public void changeStatusToReady(Long id) {
		if(!this.ordersRepository.existsById(id))
			throw new OrderNotFoundException("Pedido "+id+" não encontrado");
		
		if(this.ordersRepository.existsByIdAndStatus(id, TypeStatus.CANCELED))
			throw new OrderCanceledException("O pedido "+id+" já foi cancelado");
		
		if(this.ordersRepository.existsByIdAndStatus(id, TypeStatus.READY))
			throw new OrderIsReadyException("O pedido "+id+" já está pronto");
		
		this.changeStatus(id, TypeStatus.READY);
	}
	
	private void changeStatus(Long id, TypeStatus status) {
		Orders order = this.ordersRepository.findById(id).get();
		order = new Orders(order, status);
		
		this.ordersRepository.saveAndFlush(order);
	}
	
	private Double calcDiscount(Snack snack){
		
		Double discount = 0d;
		
		List<SnackIngredient> items = snack.getItems();
		
		Boolean hasLettuce = items.stream().anyMatch(item -> item.getIngredient().getName().equals("Alface")); 
		Boolean hasBacon = items.stream().anyMatch(item -> item.getIngredient().getName().equals("Bacon"));
		
		//10 % de desconto
		if(hasLettuce && !hasBacon) {
			return (snack.getPrice() * 10) / 100;
		} else {
			
			Double value = 0d;
			
			for(SnackIngredient item : snack.getItems()) {
				Ingredient ingredient = item.getIngredient();
				if(ingredient.getName().equals("Hambúrguer")) {
					value = (double) ((item.getPiece() % 3) == 0 ? (item.getPiece() / 3) : item.getPiece());
					discount = item.getPiece() != null && item.getPiece() > 1 ? ingredient.getPrice() * value : 1d; 
				} else if(ingredient.getName().equals("Queijo")) {
					value = (double) ((item.getPiece() % 3) == 0 ? (item.getPiece() / 3) : item.getPiece());
					discount = item.getPiece() != null && item.getPiece() > 1 ? ingredient.getPrice() * value : 1d; 				
				}
			}
			
		}
		return discount;
	}
	
	private Double calcTotalPrice(List<Item> items) {
		Double price = 0d;
		for(Item item : items) {
			price += item.getPrice();
		}
		return price;
	}
	
	private Double calcDiscountPrice(List<Item> items) {
		Double discount = 0d;
		for(Item item : items) {
			discount += item.getDiscount();
		}
		return discount; 
	}
	
	private Double calcSnackPrice(Snack snack) {
		Double price = 0d;
		for(SnackIngredient item : snack.getItems()) {
			price += item.getPiece() * item.getIngredient().getPrice();
		}
		return price;
	}
	
	public void checkItems(Orders order) throws Exception {
		for(Item item : order.getItems()) {
			
			Assert.notNull(item.getSnack(), "Informe o lanche");
			
			if(!item.isCustom())
				Assert.notNull(item.getSnack().getId(), "Informe o código do lanche");
			else if(item.isCustom()) {
				Assert.notNull(item.getQuantity(), "Informe a quantidade do item");
				Assert.notNull(item.getSnack().getItems(), "Informe os ingredientes do lanche personalizado");
				
				for(SnackIngredient item2 : item.getSnack().getItems()) {
					Assert.notNull(item2.getIngredient().getId(), "Informe o código do ingrediente");
					Assert.notNull(item2.getIngredient().getName(), "Informe o nome do ingrediente");
					Assert.notNull(item2.getIngredient().getPrice(), "Informe o preço do ingrediente");
				}
			} else
				throw new Exception("Informe a propriedade isCustom");
		}
	}
	
}

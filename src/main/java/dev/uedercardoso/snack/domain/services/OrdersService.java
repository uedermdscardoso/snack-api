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
import dev.uedercardoso.snack.domain.model.person.PersonProfile;
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
import dev.uedercardoso.snack.exceptions.AccessNotAllowedException;
import dev.uedercardoso.snack.exceptions.CurrentUserNotFoundException;
import dev.uedercardoso.snack.exceptions.EmptyListException;
import dev.uedercardoso.snack.exceptions.EmptyNameException;
import dev.uedercardoso.snack.exceptions.OrderCanceledException;
import dev.uedercardoso.snack.exceptions.OrderIsReadyException;
import dev.uedercardoso.snack.exceptions.OrderNotFoundException;
import dev.uedercardoso.snack.exceptions.OrdersServiceException;
import dev.uedercardoso.snack.exceptions.PersonNotFoundException;
import dev.uedercardoso.snack.exceptions.SnackNotFoundException;

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
		
		int count1 = -1, count2 = -1;
		List<Long> customSnackIds = new LinkedList<Long>();
		List<Long> snackIds = new LinkedList<Long>();
		
		for(Item item : order.getItems()) {
			
			if(order.getItems() == null || order.getItems().size() == 0)
				throw new EmptyListException("Items não foram preenchidos");

			if(item.getIsCustom()) {
				
				if(item.getSnack().getName() != null && !item.getSnack().getName().isEmpty())
					throw new EmptyNameException("O nome não pode ser preenchido para lanches personalizados.");

				Snack snackCustom = new Snack(item.getIsCustom(), this.calcCustomSnackPrice(item.getSnack().getItems()));
				snackCustom = this.snackRepository.saveAndFlush(snackCustom);
				
				for(SnackIngredient item2 : item.getSnack().getItems()) {
					
					Ingredient ingredient = item2.getIngredient();
					SnackIngredientPk pk = new SnackIngredientPk(snackCustom.getId(), ingredient.getId());
					Long piece = item2.getPiece() != null && item2.getPiece() > 0 ? item2.getPiece() : 1l;
					this.snackIngredientRepository.saveAndFlush(new SnackIngredient(pk, snackCustom, ingredient, piece));	
				
				}
				
				customSnackIds.add(snackCustom.getId());
				
				item.setPrice(this.calcCustomSnackPrice(item.getSnack().getItems()));
				
			} else {
				if(!this.snackRepository.existsByIdAndIsCustom(item.getSnack().getId(), false))
					throw new SnackNotFoundException("O lanche "+item.getSnack().getId()+" não foi encontrado");
				
				Optional<Snack> opSnack = this.snackRepository.findById(item.getSnack().getId());
				if(opSnack.isPresent())
					item.setSnack(opSnack.get());
				
				if(item.getQuantity() == null)
					item.setQuantity(1l);
				
				item.setPrice(this.calcSnackPrice(item));
				
				snackIds.add(item.getSnack().getId());
			}
			
			item.setDiscount(this.calcDiscount(item.getSnack()));
			
		}
		
		
		Optional<Person> opPerson = this.personRepository.findById(order.getPerson().getId());
		if(!opPerson.isPresent())
			throw new OrdersServiceException("Pessoa com o id "+order.getPerson().getId());
		
		Double totalPrice = this.calcTotalPrice(order.getItems()); 
		Double totalDiscount = this.calcDiscountPrice(order.getItems());
		
		Orders newOrder = new Orders(order, totalPrice, totalDiscount, opPerson.get());
		newOrder = this.ordersRepository.saveAndFlush(newOrder);
		
		if(newOrder.getId() != null || newOrder.getId() > 0) {
			
			newOrder.setItems(order.getItems());
			
			for(Item item : newOrder.getItems()) {
				if(item.getIsCustom()) {
					this.saveItems(newOrder, item, customSnackIds, ++count1);
				} else {
					this.saveItems(newOrder, item, snackIds, ++count2);
				}
			}
		}
	}
	
	public OrdersDTO getOrdersById(Long id, String currentUsername){
		
		if(!this.personRepository.existsByUsername(currentUsername))
			throw new CurrentUserNotFoundException("O usuário atual "+currentUsername+" não foi encontrado");
		
		if(!this.ordersRepository.existsById(id))
			throw new OrderNotFoundException("O pedido "+id+" não foi encontrado");
		
		if(!this.personRepository.existsByUsernameAndProfile(currentUsername, PersonProfile.ADMIN) && !this.ordersRepository.existsByIdAndPersonUsername(id,currentUsername))
			throw new AccessNotAllowedException("O usuário "+currentUsername+" não pode acessar o pedido "+id+" porque este pedido não foi feito por ele");
		
		Orders order = this.ordersRepository.findById(id).get();
		
		List<Orders> orders = new LinkedList<Orders>(); 
		orders.add(order);
		
		List<OrdersDTO> ordersDTO = this.convertOrdersToOrdersDTO(orders);
		
		return ordersDTO.get(0);
	}
	
	public List<OrdersDTO> getMyOrders(String username){
		if(!this.personRepository.existsByUsername(username))
			throw new PersonNotFoundException("Pessoa "+username+" não foi encontrado");
		
		Person person = this.personRepository.findByUsername(username);
		
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

	
	private void saveItems(Orders newOrder, Item item, List<Long> snackIds, int count) {
		Snack snack = null;
		ItemPk itemPk = null;
		
		Long id = snackIds.get(count);
		
		snack = this.snackRepository.findById(id).get();
		itemPk = new ItemPk(newOrder.getId(), id);
		
		Item i = new Item(itemPk, snack, item);
		i.setOrder(newOrder);
		i.getOrder().setItems(null);
		i.getSnack().setItems(null);
		
		this.itemRepository.saveAndFlush(i);	
	
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
	
	public void cancelOrder(Long id, String currentUsername) {
		
		if(!this.personRepository.existsByUsername(currentUsername))
			throw new CurrentUserNotFoundException("O usuário atual "+currentUsername+" não foi encontrado");
		
		if(!this.ordersRepository.existsById(id))
			throw new OrderNotFoundException("Pedido "+id+" não encontrado");
		
		if(!this.personRepository.existsByUsernameAndProfile(currentUsername, PersonProfile.ADMIN) && !this.ordersRepository.existsByIdAndPersonUsername(id,currentUsername))
			throw new AccessNotAllowedException("O usuário "+currentUsername+" não pode cancelar o pedido "+id+" porque este pedido não foi feito por ele");
		
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
		} else { // Outros descontos
			
			Double value = 0d;
			
			for(SnackIngredient item : snack.getItems()) {
				Ingredient ingredient = item.getIngredient();
				
				if(ingredient.getName().equals("Hambúrguer")) { //Desconto para muita carne 
					value = (double) ((item.getPiece() % 3) == 0 ? (item.getPiece() / 3) : item.getPiece());
					discount = item.getPiece() != null && item.getPiece() > 1 ? ingredient.getPrice() * value : 0d; 
				} else if(ingredient.getName().equals("Queijo")) { //Desconto para muito queijo
					value = (double) ((item.getPiece() % 3) == 0 ? (item.getPiece() / 3) : item.getPiece());
					discount = item.getPiece() != null && item.getPiece() > 1 ? ingredient.getPrice() * value : 0d; 				
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
		
		if(items == null || items.size() == 0)
			throw new EmptyListException("Lista vazia");
		
		for(Item item : items) {
			discount += item.getDiscount();
		}
		return discount; 
	}
	
	private Double calcCustomSnackPrice(List<SnackIngredient> items) {
		Double price = 0d;
		if(items == null || items.size() == 0)
			throw new EmptyListException("Lista vazia");
		
		for(SnackIngredient item : items) {
			price += item.getPiece() * item.getIngredient().getPrice();
		}
		return price;
	}
	
	private Double calcSnackPrice(Item item) {
		return item.getQuantity() * item.getSnack().getPrice();
	}
	
	public void checkItems(Orders order) throws Exception {
		for(Item item : order.getItems()) {
			
			Assert.notNull(item.getSnack(), "Informe o lanche");
			
			if(!item.isCustom())
				Assert.notNull(item.getSnack().getId(), "Informe o código do lanche");
			else if(item.isCustom()) {
				Assert.notNull(item.getQuantity(), "Informe a quantidade do item");
				Assert.isNull(item.getSnack().getId(), "O lanche personalizado não pode conter código.");
				Assert.notNull(item.getSnack().getItems(), "Informe os ingredientes do lanche personalizado");
				
				for(SnackIngredient item2 : item.getSnack().getItems()) {
					Assert.notNull(item2.getIngredient(), "Informe o ingrediente");
					Assert.notNull(item2.getIngredient().getId(), "Informe o código do ingrediente");
					Assert.notNull(item2.getIngredient().getName(), "Informe o nome do ingrediente");
					Assert.notNull(item2.getIngredient().getPrice(), "Informe o preço do ingrediente");
				}
			} else
				throw new Exception("Informe a propriedade isCustom");
		}
	}
	
}

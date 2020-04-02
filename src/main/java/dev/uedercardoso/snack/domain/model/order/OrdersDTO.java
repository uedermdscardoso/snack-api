package dev.uedercardoso.snack.domain.model.order;

import java.io.Serializable;
import java.util.List;

import dev.uedercardoso.snack.domain.model.person.PersonDTO;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrdersDTO implements Serializable {
	
	private static final long serialVersionUID = -7912650440720342637L;

	private Long orderId; 
	private Long creationDate;
	private TypeStatus status;
	private Double totalPrice;
	private Double totalDiscount;
	private List<ItemDTO> items;
	private PersonDTO person;
	
	public OrdersDTO() {
		
	}
	
	public OrdersDTO(Orders order, PersonDTO personDTO, List<ItemDTO> itemsDTO) {
		this.orderId = order.getId(); 
		this.creationDate = order.getCreationDate().getTime();
		this.status = order.getStatus();
		this.totalPrice = order.getTotalPrice();
		this.totalDiscount = order.getTotalDiscount();
		
		this.items = itemsDTO;	
		this.person = personDTO;
	}
	
}

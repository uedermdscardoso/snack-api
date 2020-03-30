package dev.uedercardoso.snack.domain.model.order;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.uedercardoso.snack.domain.model.snack.SnackDTO;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemDTO implements Serializable {
	
	private static final long serialVersionUID = -6151819720647206808L;

	@JsonIgnore
	private Long orderId;
	private SnackDTO snack;
	private Long quantity;
	private Boolean isCustom;
	private Double price;
	private Double discount;
	
	public ItemDTO() {
		
	}
	
	public ItemDTO(Item item, SnackDTO snackDTO) {
		this.orderId = item.getOrder().getId();
		this.quantity = item.getQuantity();
		this.isCustom = item.isCustom();
		this.price = item.getPrice();
		this.discount = item.getDiscount();
		
		this.snack = snackDTO;
	}
	
}

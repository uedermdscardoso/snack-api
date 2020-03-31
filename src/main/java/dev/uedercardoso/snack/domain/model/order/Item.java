package dev.uedercardoso.snack.domain.model.order;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dev.uedercardoso.snack.domain.model.snack.Snack;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="item")
@Getter @Setter
public class Item implements Serializable {
	
	private static final long serialVersionUID = 2926017562363069243L;

	@EmbeddedId
	private ItemPk id;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="order_id",insertable=false, updatable=false)
	private Orders order;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch=FetchType.LAZY)
	@JoinColumn(name="snack_id",insertable=false, updatable=false)
	private Snack snack;
	
	private Long quantity;
	
	private String description;
	
	@Column(name="is_custom",nullable=false)
	private Boolean isCustom;

	@Column(nullable=false)
	private Double price;
	
	private Double discount;
	
	public Item() {
		
	}

	public Item(ItemPk id, Item item) {
		this.id = id;
		this.quantity = item.getQuantity();
		this.isCustom = item.isCustom(); 
		this.price = item.getPrice();
		this.discount = item.getDiscount(); 
	}
	
	public Item(Item item, Orders order) {
		this.order = order; 
		this.snack = item.getSnack(); 
		this.isCustom = item.isCustom(); 
		this.price = item.getPrice();
		this.discount =item.getDiscount(); 
	}
	
	public Boolean isCustom() {
		return this.isCustom;
	}
	
}

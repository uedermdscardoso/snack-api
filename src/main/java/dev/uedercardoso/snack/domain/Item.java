package dev.uedercardoso.snack.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="snack_id",insertable=false, updatable=false)
	private Snack snack;
	
	@Column(nullable=false)
	private String description;
	
	@Column(name="is_custom",nullable=false)
	private Boolean isCustom;
	
	@Column(nullable=false)
	private Long quantity;
	
	@Column(nullable=false)
	private Double price;
	
	@Column(nullable=false)
	private Double discount;
	
	public Item() {
		
	}
	
}

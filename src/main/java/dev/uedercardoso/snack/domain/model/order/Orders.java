package dev.uedercardoso.snack.domain.model.order;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import dev.uedercardoso.snack.domain.model.person.Person;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="orders")
@Getter @Setter
public class Orders implements Serializable {

	private static final long serialVersionUID = -6174690896373469779L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 
	
	@Column(name="creation_date", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(name="total_price")
	private Double totalPrice;

	@Column(name="total_discount",columnDefinition="double default 0")
	private Double totalDiscount;
	
	@NotNull
	@OneToMany(mappedBy="order", cascade=CascadeType.ALL)
	private List<Item> items;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="person_id")
	private Person person;
	
	public Orders() {
		
	}
	
	public Orders(Orders order, Double totalPrice, Double totalDiscount, Person person) {
		this.creationDate = Calendar.getInstance().getTime();
		this.totalPrice = totalPrice;
		this.totalDiscount = totalDiscount;
		this.person = person;
		
		this.items = order.getItems();
	}
	
}

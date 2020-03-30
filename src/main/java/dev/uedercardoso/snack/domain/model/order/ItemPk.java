package dev.uedercardoso.snack.domain.model.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class ItemPk implements Serializable {

	private static final long serialVersionUID = -7071866100440730398L;

	@Column(name="order_id",insertable=false,updatable=false)
	private Long orderId; 
	
	@Column(name="snack_id",insertable=false,updatable=false)
	private Long snackId;
	
	public ItemPk() {
		
	}

	public ItemPk(Long orderId, Long snackId) {
		this.orderId = orderId;
		this.snackId = snackId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((snackId == null) ? 0 : snackId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPk other = (ItemPk) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (snackId == null) {
			if (other.snackId != null)
				return false;
		} else if (!snackId.equals(other.snackId))
			return false;
		return true;
	}
	
}

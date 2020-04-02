package dev.uedercardoso.snack.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.uedercardoso.snack.domain.model.order.Item;
import dev.uedercardoso.snack.domain.model.order.ItemPk;
import dev.uedercardoso.snack.domain.model.snack.Snack;

public interface ItemRepository extends JpaRepository<Item, ItemPk> {

	Boolean existsBySnack(Snack snack);
	
}

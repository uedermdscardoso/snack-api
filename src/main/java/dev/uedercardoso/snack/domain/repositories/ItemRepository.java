package dev.uedercardoso.snack.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.uedercardoso.snack.domain.model.order.Item;
import dev.uedercardoso.snack.domain.model.order.ItemPk;

public interface ItemRepository extends JpaRepository<Item, ItemPk> {

}

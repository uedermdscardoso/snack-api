package dev.uedercardoso.snack.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.uedercardoso.snack.domain.model.snack.Snack;
import dev.uedercardoso.snack.domain.model.snack.SnackIngredient;
import dev.uedercardoso.snack.domain.model.snack.SnackIngredientPk;
import dev.uedercardoso.snack.domain.repositories.SnackIngredientRepository;
import dev.uedercardoso.snack.domain.repositories.SnackRepository;
import dev.uedercardoso.snack.exceptions.EmptyListException;
import dev.uedercardoso.snack.exceptions.SnackNotFoundException;

@Service
public class SnackService {

	@Autowired
	private SnackRepository snackRepository;
	
	@Autowired
	private SnackIngredientRepository snackIngredientRepository;

	public List<Snack> getAllSnacks() {
		List<Snack> list = this.snackRepository.findAll();
		if(list == null || list.size() == 0) {
			throw new EmptyListException("Lista vazia");
		}
		return list;
	}
	
	public void save(List<Snack> snacks) {
		
		for(Snack snack : snacks) {
			snack.setPrice(this.calcSnackPriceByIngredients(snack));
			
			for(SnackIngredient item : snack.getItems()) {
				item.setId(new SnackIngredientPk(item.getSnack().getId(), item.getIngredient().getId()));
				this.snackIngredientRepository.saveAndFlush(item);
			}
		}
		
		this.snackRepository.saveAll(snacks);
	}
	
	public void saveCustom(Snack snack) {
		this.snackRepository.saveAndFlush(snack);
	}
	
	public void save(Long id, Snack snack) {
		this.snackRepository.saveAndFlush(snack);
	}
	
	public void delete(Long id) {
		
		if(!this.snackRepository.existsById(id))
			throw new SnackNotFoundException("Lanche "+id+" não encontrado");
		
		this.snackRepository.findById(id);
	}
	
	private Double calcSnackPriceByIngredients(Snack snack) {
		Double price = 0d;
		
		if(snack.getItems() == null || snack.getItems().size() == 0)
			throw new EmptyListException("Não tem items");
		
		for(SnackIngredient item : snack.getItems()) {
			if(item.getIngredient() != null) 
				price += item.getIngredient().getPrice();
		}
		
		return price;
	}
	
}

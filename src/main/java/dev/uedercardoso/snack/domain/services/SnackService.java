package dev.uedercardoso.snack.domain.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import dev.uedercardoso.snack.domain.model.snack.Snack;
import dev.uedercardoso.snack.domain.model.snack.SnackDTO;
import dev.uedercardoso.snack.domain.model.snack.SnackIngredient;
import dev.uedercardoso.snack.domain.model.snack.SnackIngredientDTO;
import dev.uedercardoso.snack.domain.model.snack.SnackIngredientPk;
import dev.uedercardoso.snack.domain.repositories.IngredientRepository;
import dev.uedercardoso.snack.domain.repositories.ItemRepository;
import dev.uedercardoso.snack.domain.repositories.SnackIngredientRepository;
import dev.uedercardoso.snack.domain.repositories.SnackRepository;
import dev.uedercardoso.snack.exceptions.EmptyListException;
import dev.uedercardoso.snack.exceptions.IngredientNotFoundException;
import dev.uedercardoso.snack.exceptions.SnackNotFoundException;
import dev.uedercardoso.snack.exceptions.SnackServiceException;
import dev.uedercardoso.snack.exceptions.SnackWasFoundException;

@Service
public class SnackService {

	@Autowired
	private SnackRepository snackRepository;

	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private SnackIngredientRepository snackIngredientRepository;

	public List<SnackDTO> getAllSnacks() {
		List<Snack> snacks = this.snackRepository.findAll();
		List<SnackDTO> snacksDTO = new LinkedList<SnackDTO>();
		List<SnackIngredientDTO> itemsDTO = new LinkedList<SnackIngredientDTO>();
		
		if(snacks == null || snacks.size() == 0)
			throw new EmptyListException("Lista vazia");
		
		for(Snack snack : snacks) {
			for(SnackIngredient item : snack.getItems())
				itemsDTO.add(new SnackIngredientDTO(item));
			
			snacksDTO.add(new SnackDTO(snack, itemsDTO));
		}
		return snacksDTO;
	}
	
	public void save(List<Snack> snacks) {
		
		Double price = 0d;
		int count = 0;
		
		List<Snack> newSnacks = new LinkedList<Snack>();
		
		for(Snack snack : snacks) {

			if(this.snackRepository.existsByName(snack.getName().trim()))
				throw new SnackWasFoundException("O nome do lanche "+snack.getName().trim()+" já existe.");
			
			price = this.calcSnackPriceByIngredients(snack);
			newSnacks.add(new Snack(snack.getName(), snack.getIsCustom(), price));
		}
		
		newSnacks = this.snackRepository.saveAll(newSnacks);
		
		for(Snack snack : snacks) {
			
			for(SnackIngredient item : snack.getItems()) {
				
				if(!this.ingredientRepository.existsById(item.getIngredient().getId()))
					throw new IngredientNotFoundException("O ingrediente "+item.getIngredient().getId()+" não existe");
				
				SnackIngredientPk pk = new SnackIngredientPk(newSnacks.get(count).getId(), item.getIngredient().getId());
				item.setId(pk);
				this.snackIngredientRepository.saveAndFlush(item);
			}
			
			count++;
		}
		
	}
	
	public void delete(Long id) {
		
		if(!this.snackRepository.existsById(id))
			throw new SnackNotFoundException("Lanche "+id+" não encontrado");
		
		Snack snack = this.snackRepository.findById(id).get();
		
		if(this.itemRepository.existsBySnack(snack))
			throw new SnackServiceException("O lanche "+id+" não pode ser excluido");
			
		this.snackRepository.findById(id);
	}
	
	public void checkSnacks(List<Snack> snacks) {
		Assert.notNull(snacks, "Informe os lanches");
		
		for(Snack snack : snacks) {
			
			Assert.isNull(snack.getId(), "Não informe o id do lanche");
			Assert.notNull(snack.getName(), "Informe o nome do lanche");
			Assert.notNull(snack.getIsCustom(), "Adicione o campo \"isCustom\" com o valor \"true\" ou \"false\"");
			Assert.notNull(snack.getItems(), "Adicione os items do lanche");
			
			for(SnackIngredient item : snack.getItems()) {
				Assert.isNull(item.getSnack(), "Não informe o lanche");
				Assert.notNull(item.getIngredient(), "Informe o ingrediente");
				Assert.notNull(item.getIngredient().getName(), "Informe o nome do ingrediente");
				Assert.notNull(item.getIngredient().getId(), "Informe o código do ingrediente "+item.getIngredient().getName());
				Assert.notNull(item.getIngredient().getPrice(), "Informe o preço do ingrediente "+item.getIngredient().getName());
				Assert.notNull(item.getPiece(), "Informe as porções do ingrediente "+item.getIngredient().getName());
			}
			
		}
	}
	
	private Double calcSnackPriceByIngredients(Snack snack) {
		Double price = 0d;
		
		if(snack.getItems() == null || snack.getItems().size() == 0)
			throw new EmptyListException("Não tem items");
		
		for(SnackIngredient item : snack.getItems()) {
			if(item.getIngredient() != null) 
				price += item.getPiece() * item.getIngredient().getPrice();
		}
		
		return price;
	}
	
}

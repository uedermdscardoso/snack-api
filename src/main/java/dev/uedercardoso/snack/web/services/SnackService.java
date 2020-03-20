package dev.uedercardoso.snack.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.uedercardoso.snack.domain.Snack;
import dev.uedercardoso.snack.exceptions.EmptyListException;
import dev.uedercardoso.snack.web.repositories.SnackRepository;

@Service
public class SnackService {

	@Autowired
	private SnackRepository snackRepository;

	public List<Snack> getAllSnacks() {
		List<Snack> list = this.snackRepository.findAll();
		if(list == null || list.size() == 0) {
			throw new EmptyListException("Lista vazia");
		}
		return list;
	}
	
	public void save(List<Snack> snacks) {
		this.snackRepository.saveAll(snacks);
	}
	
	public void saveCustom(Snack snack) {
		this.snackRepository.saveAndFlush(snack);
	}
	
	public void save(Long id, Snack snack) {
		this.snackRepository.saveAndFlush(snack);
	}
	
	public void delete(Long id) {
		this.snackRepository.findById(id);
	}
	
}

package com.cash.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.cash.exception.ObjectNotFoundException;
import com.cash.model.Fornecedor;
import com.cash.repository.FornecedorRepository;
import com.cash.service.FornecedorService;

public class FornecedorServiceImpl implements FornecedorService {

	@Autowired
	FornecedorRepository fornecedorRepository;
	
	@Override
	public List<Fornecedor> findAll() {
		return fornecedorRepository.findAll();
	}

	@Override
	public Optional<Fornecedor> findById(Long id) {
		return Optional.of(fornecedorRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Fornecedor não encontrado")));
	}

	@Override
	public void update(Long id, Fornecedor fornecedor) {
		Optional<Fornecedor> obj = 
				Optional.of(fornecedorRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Fornecedor não encontrado")));
		obj.get().setEmail(fornecedor.getEmail());
		obj.get().setNome(fornecedor.getNome());
		obj.get().setObservacao(fornecedor.getObservacao());
		obj.get().setTelefone(fornecedor.getTelefone());
		fornecedorRepository.save(obj.get());
	}

	@Override
	public Fornecedor save(Fornecedor fornecedor) {
		fornecedor.setId(null);
		return fornecedorRepository.save(fornecedor);
	}

	@Override
	public void delete(Long id) {
		Optional<Fornecedor> obj = 
				Optional.of(fornecedorRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Fornecedor não encontrado")));
		fornecedorRepository.delete(obj.get());
	}

}

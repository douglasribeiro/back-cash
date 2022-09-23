package com.cash.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.cash.dto.FornecedorDTO;
import com.cash.model.Fornecedor;

@Service
public interface FornecedorService {

	public List<Fornecedor> findAll();

	public Optional<Fornecedor> findById(Long id);

	public void update(Long id, FornecedorDTO fornecedor);

	public Fornecedor save(Fornecedor fornecedor);

	public void delete(Long id); 
	
}

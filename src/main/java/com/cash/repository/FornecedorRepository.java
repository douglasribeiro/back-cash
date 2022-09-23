package com.cash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cash.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

	List<Fornecedor> findByUsuarioId(Integer id);
	
}

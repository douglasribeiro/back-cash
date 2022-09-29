package com.cash.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cash.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	
	@Transactional(readOnly=true)
	@Query("SELECT c from Conta c where c.dtVenc >= ?1  ORDER BY c.dtVenc ASC")
	List<Conta> findByDtVencimento(Date venc);
	
	@Transactional(readOnly=true)
	@Query("SELECT c from Conta c where c.dtVenc >= ?1 ORDER BY c.dtVenc ASC")
	List<Conta> findByDtVencOrderByDtVencAsc(Date venc);
	
	@Transactional(readOnly=true)
	@Query("SELECT c from Conta c where c.dtVenc between ?1 and ?2  ORDER BY c.dtVenc ASC")
	List<Conta> findByDtVencimentoBetween(Date ini, Date fim);
/*
 * @Modifying
    @Transactional
    @Query(value = "insert into city (id, name) VALUES (?, ?)", nativeQuery = true)
    City save(Long id, String name);
    
 */
	
	@Modifying
	@Transactional
	@Query(value = "insert into conta (descr, dt_pagto, dt_venc, include_date, situacao, valor_desconto, valor_inicial, valor_juro, valor_pago, fornecedor_id, usuario_id) "
			+ "values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11)", nativeQuery = true)
	public int salva(
			String descr, 
			Date dt_pagto, 
			Date dt_venc, 
			Date include_date,
			Integer situacao, 
			Double valor_desconto, 
			Double valor_inicial, 
			Double valor_juro, 
			Double valor_pago, 
			Long fornecedor_id, 
			Integer usuario_id);
}

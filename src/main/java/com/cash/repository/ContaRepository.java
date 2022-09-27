package com.cash.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.cash.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	
	@Transactional(readOnly=true)
	@Query("SELECT c from Conta c where c.dtVenc >= ?1")
	List<Conta> findByDtVencimento(Date venc);
	
	@Transactional(readOnly=true)
	@Query("SELECT c from Conta c where c.dtVenc between ?1 and ?2")
	List<Conta> findByDtVencimentoBetween(Date ini, Date fim);

}

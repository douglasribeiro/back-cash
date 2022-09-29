package com.cash.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.cash.dto.ContaDTO;
import com.cash.model.Conta;

@Service
public interface ContaService {

	public List<Conta> findAll();

	public Optional<Conta> findById(Long id);
	
	public List<Conta> findByVenc(Date venc);
	
	public List<Conta> findByVencBetween(Date dtIni, Date dtFim);

	public void update(Long id, Conta conta);

	public Conta save(ContaDTO conta);

	public void delete(Long id);

	public List<Conta> replica(@Valid ContaDTO conta, Integer replica);

	public Boolean pagamento(Long id, @Valid Conta conta); 
	
}

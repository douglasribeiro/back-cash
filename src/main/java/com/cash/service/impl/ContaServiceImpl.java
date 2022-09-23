package com.cash.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.cash.enums.Situacao;
import com.cash.exception.ObjectNotFoundException;
import com.cash.model.Conta;
import com.cash.repository.ContaRepository;
import com.cash.service.ContaService;

public class ContaServiceImpl implements ContaService {

	@Autowired
	ContaRepository contaRepository;
	
	@Override
	public List<Conta> findAll() {
		return contaRepository.findAll();
	}

	@Override
	public Optional<Conta> findById(Long id) {
		return Optional.of(contaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Conta não encontrada")));
	}

	@Override
	public void update(Long id, Conta conta) {
		Optional<Conta> obj = 
				Optional.of(contaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Conta não encontrada")));
		obj.get().setDescr(conta.getDescr());
		obj.get().setDtPagto(conta.getDtPagto());
		obj.get().setDtVenc(conta.getDtVenc());
		obj.get().setFornecedor(conta.getFornecedor());
		obj.get().setSituacao(conta.getSituacao());
		obj.get().setValorDesconto(conta.getValorDesconto());
		obj.get().setValorInicial(conta.getValorInicial());
		obj.get().setValorJuro(conta.getValorJuro());
		obj.get().setValorPago(conta.getValorPago());
		obj.get().setUsuario(conta.getUsuario());
		contaRepository.save(obj.get());
	}

	@Override
	public Conta save(Conta conta) {
		conta.setId(null);
		return contaRepository.save(conta);
	}

	@Override
	public void delete(Long id) {
		Optional<Conta> obj = 
				Optional.of(contaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Conta não encontrada")));
		contaRepository.delete(obj.get());
	}

	@Override
	public List<Conta> replica(@Valid Conta conta, Integer replica) {
		int mesAtual = conta.getDtVenc().getMonthValue();
		List<Conta> retorno = new ArrayList<>();
		for(int i = 0; i < replica; i++) {
			LocalDate dtvenc = conta.getDtVenc();
			dtvenc = dtvenc.plusMonths(i);
			Conta obj = new Conta(
					null, 
					conta.getDescr(), 
					conta.getFornecedor(), 
					conta.getSituacao(), 
					dtvenc, 
					null, 
					dtvenc, conta.getValorInicial(), 
					conta.getValorJuro(), 
					conta.getValorDesconto(), 
					conta.getValorPago(),
					conta.getUsuario());
			contaRepository.save(obj);
			retorno.add(obj);
		}
		return retorno; 
	}

	@Override
	public Boolean pagamento(Long id, @Valid Conta conta) {
		Optional<Conta> obj = 
				Optional.of(contaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Conta não encontrada")));
		if(obj.get().getSituacao() != Situacao.PAGA && obj.get().getSituacao() != Situacao.CANCELADA) {
			obj.get().setDtPagto(conta.getDtPagto());
			obj.get().setSituacao(Situacao.PAGA);
			obj.get().setValorDesconto(conta.getValorDesconto());
			obj.get().setValorJuro(conta.getValorJuro());
			obj.get().setValorPago(conta.getValorPago());
			contaRepository.save(obj.get());
			return true;
		}
		return false;
	}

}

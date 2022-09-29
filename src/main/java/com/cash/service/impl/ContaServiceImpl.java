package com.cash.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.cash.dto.ContaDTO;
import com.cash.enums.Situacao;
import com.cash.exception.ObjectNotFoundException;
import com.cash.model.Conta;
import com.cash.model.Fornecedor;
import com.cash.model.Usuario;
import com.cash.repository.ContaRepository;
import com.cash.repository.FornecedorRepository;
import com.cash.repository.UsuarioRepository;
import com.cash.service.ContaService;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdValueProperty;

public class ContaServiceImpl implements ContaService {

	@Autowired
	ContaRepository contaRepository;
	
	@Autowired
	FornecedorRepository fornecedorRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<Conta> findAll() {
		return atualiza(new Date(), contaRepository.findAll());
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
	public Conta save(ContaDTO contaDTO) {
		Conta conta = modelMapper.map(contaDTO, Conta.class);
		Optional<Usuario> usuario = usuarioRepository.findById(contaDTO.getUsuario());
		conta.setUsuario(usuario.get());
		Optional<Fornecedor> fornecedor = fornecedorRepository.findById(contaDTO.getFornecedor());
		conta.setFornecedor(fornecedor.get());
		conta.setSituacao(Situacao.toEnum(contaDTO.getSituacao()));
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
	public List<Conta> replica(@Valid ContaDTO contaDTO, Integer replica) {
		Conta conta = modelMapper.map(contaDTO, Conta.class);
		Optional<Usuario> usuario = usuarioRepository.findById(contaDTO.getUsuario());
		conta.setUsuario(usuario.get());
		Optional<Fornecedor> fornecedor = fornecedorRepository.findById(contaDTO.getFornecedor());
		conta.setFornecedor(fornecedor.get());
		int mesAtual = conta.getDtVenc().getMonth();
		List<Conta> retorno = new ArrayList<>();
		conta.setSituacao(Situacao.toEnum(contaDTO.getSituacao()));
		Date dtvenc = conta.getDtVenc();
		Date today = new Date();
		for(int i = 0; i < replica; i++) {
			Conta obj = new Conta();
			dtvenc.setMonth(mesAtual +i);
			contaRepository.salva(conta.getDescr(), conta.getDtPagto(), dtvenc, today, conta.getSituacao().getCodigo(),
					conta.getValorDesconto(),  conta.getValorInicial(), conta.getValorJuro(), conta.getValorPago(),
					conta.getFornecedor().getId(), conta.getUsuario().getId());
			
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

	@Override
	public List<Conta> findByVenc(Date venc) {
		//return atualiza(new Date(), contaRepository.findByDtVencimento(venc));
		return atualiza(new Date(), contaRepository.findByDtVencOrderByDtVencAsc(venc));
	}

	private List<Conta> atualiza(Date venc, List<Conta> contas) {
		List<Conta> lista = new ArrayList<>();
		//venc.setDate(venc.getDate()+1);
		for(Conta conta : contas){
			if(conta.getDtVenc().before(venc) && conta.getSituacao().getCodigo() == 0) {
				conta.setSituacao(Situacao.ATRASADA);
				contaRepository.save(conta);
			}
			lista.add(conta);
		}
		return lista;
	}

	@Override
	public List<Conta> findByVencBetween(Date dtIni, Date dtFim) {
		return atualiza(new Date(), contaRepository.findByDtVencimentoBetween(dtIni, dtFim));
	}

}

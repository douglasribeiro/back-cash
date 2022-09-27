package com.cash.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import com.cash.dto.FornecedorDTO;
import com.cash.exception.ObjectNotFoundException;
import com.cash.model.Fornecedor;
import com.cash.model.Usuario;
import com.cash.repository.FornecedorRepository;
import com.cash.repository.UsuarioRepository;
import com.cash.service.FornecedorService;

public class FornecedorServiceImpl implements FornecedorService {

	@Autowired
	FornecedorRepository fornecedorRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public List<Fornecedor> findAll() {
		Usuario usuario;
		Authentication authentication = UserServiceImpl.isAuthenticated();
		if(authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		//System.out.println(authentication.getAuthorities());
		if(authentication.getPrincipal().equals("anonymousUser") 
				|| authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN")))
			return fornecedorRepository.findAll();
		else
			usuario = usuarioRepository.findByEmail(authentication.getPrincipal().toString()).get();
		return fornecedorRepository.findByUsuarioId(usuario.getId());
	}

	@Override
	public Optional<Fornecedor> findById(Long id) {
		return Optional.of(fornecedorRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Fornecedor não encontrado")));
	}

	@Override
	public void update(Long id, FornecedorDTO fornecedor) {
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
		fornecedor.setUsuario(recuperaUser());
		return fornecedorRepository.save(fornecedor);
	}

	@Override
	public void delete(Long id) {
		Optional<Fornecedor> obj = 
				Optional.of(fornecedorRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Fornecedor não encontrado")));
		fornecedorRepository.delete(obj.get());
	}
	
	private Usuario recuperaUser() {
		Authentication authentication = UserServiceImpl.isAuthenticated();
		return usuarioRepository.findByEmail(authentication.getPrincipal().toString()).get();
	}

}

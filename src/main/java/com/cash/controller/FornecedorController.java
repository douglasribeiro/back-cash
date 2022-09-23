package com.cash.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cash.dto.FornecedorDTO;
import com.cash.model.Fornecedor;
import com.cash.service.FornecedorService;
import com.cash.service.impl.UserServiceImpl;


@RestController
@RequestMapping(value = "/fornecedor")
public class FornecedorController {
	
	@Autowired
	FornecedorService fornecedorService;
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public ResponseEntity<List<FornecedorDTO>> findAll() {
		
		return ResponseEntity.ok().body(fornecedorService.findAll()
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList()));
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Optional<Fornecedor>> findById(@PathVariable("id") Long id) throws Exception{
		return ResponseEntity.ok().body(fornecedorService.findById(id));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody FornecedorDTO fornecedor, @PathVariable Long id) {
		fornecedorService.update(id, fornecedor);
		return ResponseEntity.noContent().build();
	}
	//@PreAuthorize("hasAnyRole(ADMIN)")
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody @Valid Fornecedor fornecedor) {
		Fornecedor obj = fornecedorService.save(fornecedor);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws Exception{
		fornecedorService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	private FornecedorDTO convertToDto(Fornecedor fornecedor) {
		FornecedorDTO fornecedorDTO = modelMapper.map(fornecedor, FornecedorDTO.class);
		return fornecedorDTO;
	}
	
}

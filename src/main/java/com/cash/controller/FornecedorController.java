package com.cash.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cash.model.Fornecedor;
import com.cash.service.FornecedorService;


@RestController
@RequestMapping(value = "/fornecedor")
public class FornecedorController {
	
	@Autowired
	FornecedorService fornecedorService;
	
	@GetMapping
	public ResponseEntity<List<Fornecedor>> findAll(){
		return ResponseEntity.ok().body(fornecedorService.findAll());
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Optional<Fornecedor>> findById(@PathVariable("id") Long id) throws Exception{
		return ResponseEntity.ok().body(fornecedorService.findById(id));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody Fornecedor fornecedor, @PathVariable Long id) {
		fornecedorService.update(id, fornecedor);
		return ResponseEntity.noContent().build();
	}
	
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

}

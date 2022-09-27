package com.cash.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cash.model.Conta;
import com.cash.service.ContaService;


@RestController
@RequestMapping(value = "/conta")
public class ContaController {
	
	@Autowired
	ContaService contaService;
	
	@GetMapping
	public ResponseEntity<List<Conta>> findAll(){
		return ResponseEntity.ok().body(contaService.findAll());
	}
		
	@GetMapping(value="/lista")
	public ResponseEntity<List<Conta>> findByVenc(@RequestParam("date") @DateTimeFormat(pattern="dd-MM-yyyy") Date dateTime) throws Exception{
		return ResponseEntity.ok().body(contaService.findByVenc(dateTime));
	}
	
	@GetMapping(value="/between")
	public ResponseEntity<List<Conta>> findByVencBetween(
			@RequestParam("dtIni") @DateTimeFormat(pattern="dd-MM-yyyy") Date dtIni,
			@RequestParam("dtFim") @DateTimeFormat(pattern="dd-MM-yyyy") Date dtFim) throws Exception{
		return ResponseEntity.ok().body(contaService.findByVencBetween(dtIni, dtFim));
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Optional<Conta>> findById(@PathVariable("id") Long id) throws Exception{
		return ResponseEntity.ok().body(contaService.findById(id));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody Conta conta, @PathVariable Long id) {
		contaService.update(id, conta);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/pagto/{id}")
	public ResponseEntity<Boolean> pagamento(@Valid @RequestBody Conta conta, @PathVariable Long id) {
		return ResponseEntity.ok().body(contaService.pagamento(id, conta));
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody @Valid Conta conta) {
		Conta obj = contaService.save(conta);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping(value = "/{ocorrencia}")
	public ResponseEntity<List<Conta>> replica(@RequestBody @Valid Conta conta, @PathVariable("ocorrencia") Integer replica) {
		List<Conta> obj = contaService.replica(conta, replica);
		return ResponseEntity.ok().body(obj);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws Exception{
		contaService.delete(id);
		return ResponseEntity.noContent().build();
	}

}

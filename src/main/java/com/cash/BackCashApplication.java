package com.cash;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.cash.enums.Situacao;
import com.cash.model.Conta;
import com.cash.model.Fornecedor;
import com.cash.repository.ContaRepository;
import com.cash.repository.FornecedorRepository;
import com.cash.service.ContaService;
import com.cash.service.FornecedorService;
import com.cash.service.impl.ContaServiceImpl;
import com.cash.service.impl.FornecedorServiceImpl;

@SpringBootApplication
public class BackCashApplication {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	@Autowired
	private ContaRepository contaRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackCashApplication.class, args);
	}

	@Component
	public class CommandLineAppStartupRunner implements CommandLineRunner {

		@Override
		public void run(String... args) throws Exception {
			
			Fornecedor f01 = new Fornecedor(1L, "Sabesp", null, null, "Fornecedor de Agua");
			Fornecedor f02 = new Fornecedor(2L, "EBP", null, null, "Fornecedor de Energia Eletrica");
			
			fornecedorRepository.saveAll(Arrays.asList(f01, f02));
			
			String data = "25/09/2022";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate date = LocalDate.parse(data,formatter);
			
			Conta agua = new Conta(1L, "Fornecimento de agua", f01, Situacao.ABERTA, date, null, 80.00, null, null, null);
			Conta energia = new Conta(2L, "Energia Eletrica    ", f02, Situacao.ABERTA, date, null, 170.50, 0.0, 0.0, null);
			
			contaRepository.saveAll(Arrays.asList(agua,energia));
			
		}
	}
	
	@Bean
	public FornecedorService fornecedorService() {
		return new FornecedorServiceImpl();
	}
	
	@Bean
	public ContaService contaService() {
		return new ContaServiceImpl();
	}
	
}

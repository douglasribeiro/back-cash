package com.cash;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import com.cash.enums.Perfil;
import com.cash.model.Usuario;
import com.cash.repository.UsuarioRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private UsuarioRepository usuarioRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackCashApplication.class, args);
	}

	@Component
	public class CommandLineAppStartupRunner implements CommandLineRunner {

		@Override
		public void run(String... args) throws Exception {

			Usuario user1 = new Usuario(1, "gerente", "gerente@mail.com", encoder.encode("123"));
			Usuario user2 = new Usuario(2, "admin", "admin@mail.com", encoder.encode("123"));
			Usuario user3 = new Usuario(3, "user", "user@mail.com", encoder.encode("123"));
			Usuario user4 = new Usuario(4, "user", "avulso@mail.com", encoder.encode("123"));

			user1.addPerfil(Perfil.GERENTE);
			user2.addPerfil(Perfil.ADMIN);
			user3.addPerfil(Perfil.USER);
			user4.addPerfil(Perfil.AVULSO);

			usuarioRepository.save(user1);
			usuarioRepository.save(user2);
			usuarioRepository.save(user3);
			usuarioRepository.save(user4);
			
			Fornecedor f01 = new Fornecedor(1L, "Sabesp", null, null, "Fornecedor de Agua",user1);
			Fornecedor f02 = new Fornecedor(2L, "EBP", null, null, "Fornecedor de Energia Eletrica",user2);
			
			fornecedorRepository.saveAll(Arrays.asList(f01, f02));
			
			String data = "25/09/2022";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate date = LocalDate.parse(data,formatter);
			
			Conta agua = new Conta(1L, "Fornecimento de agua", f01, Situacao.ABERTA, date, null, date, 80.00, null, null, null, user1);
			Conta energia = new Conta(2L, "Energia Eletrica    ", f02, Situacao.ABERTA, date, null, date, 170.50, 0.0, 0.0, null, user2);
			
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
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}

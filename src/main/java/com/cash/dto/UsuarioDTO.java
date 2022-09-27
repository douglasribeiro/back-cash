package com.cash.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.cash.enums.Perfil;

public class UsuarioDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String nome;

    private String email;

    private String senha;

	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(Integer id, String nome, String email, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
    
}

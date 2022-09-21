package com.cash.enums;

public enum Situacao {
	
	ABERTA(0, "Aberta"),
	PAGA(1, "Paga"),
	ATRASADA(2, "Atrasada"),
	CANCELADA(3,"Cancelada");
		
	private Integer codigo;
	private String descricao;
	
	private Situacao(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Situacao toEnum(Integer cod) {
		if(cod == null)
			return null;
		for (Situacao x: Situacao.values()) {
			if(cod.equals(x.getCodigo()))
				return x;
		}
		throw new IllegalArgumentException("Situacao invalida.");
	}
	

}

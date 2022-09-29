package com.cash.dto;

import java.io.Serializable;
import java.util.Date;

public class ContaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String descr;
	
	private Long fornecedor;
	
	private Integer situacao;
	
	private Date dtVenc;
	
	private Date includeDate;
	
	private Date dtPagto;
	
	private Double valorInicial;
	
	private Double valorJuro;
	
	private Double valorDesconto;
	
	private Double valorPago;
	
	private Integer usuario;

	private Integer replica;
	
	public ContaDTO(Long id, String descr, Long fornecedor, Integer situacao, Date dtVenc, Date includeDate,
			Date dtPagto, Double valorInicial, Double valorJuro, Double valorDesconto, Double valorPago,
			Integer usuario) {
		super();
		this.id = id;
		this.descr = descr;
		this.fornecedor = fornecedor;
		this.situacao = situacao;
		this.dtVenc = dtVenc;
		this.includeDate = includeDate;
		this.dtPagto = dtPagto;
		this.valorInicial = valorInicial;
		this.valorJuro = valorJuro;
		this.valorDesconto = valorDesconto;
		this.valorPago = valorPago;
		this.usuario = usuario;
	}

	public ContaDTO() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Long getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Long fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public Date getDtVenc() {
		return dtVenc;
	}

	public void setDtVenc(Date dtVenc) {
		this.dtVenc = dtVenc;
	}

	public Date getIncludeDate() {
		return includeDate;
	}

	public void setIncludeDate(Date includeDate) {
		this.includeDate = includeDate;
	}

	public Date getDtPagto() {
		return dtPagto;
	}

	public void setDtPagto(Date dtPagto) {
		this.dtPagto = dtPagto;
	}

	public Double getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(Double valorInicial) {
		this.valorInicial = valorInicial;
	}

	public Double getValorJuro() {
		return valorJuro;
	}

	public void setValorJuro(Double valorJuro) {
		this.valorJuro = valorJuro;
	}

	public Double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	public Integer getReplica() {
		return replica;
	}

	public void setReplica(Integer replica) {
		this.replica = replica;
	}

		
}

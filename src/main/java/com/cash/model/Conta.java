package com.cash.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cash.enums.Situacao;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Conta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 75)
	private String descr;
	
	@ManyToOne
	@JoinColumn(name="fornecedor_id")
	private Fornecedor fornecedor;
	
	@Enumerated(EnumType.ORDINAL)
	private Situacao situacao;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dtVenc;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date includeDate;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dtPagto;
	
	@Column(nullable = false)
	private Double valorInicial;
	
	private Double valorJuro;
	
	private Double valorDesconto;
	
	private Double valorPago;
	
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;

	
	public Conta() {}
	
	public Conta(Long id, String descr, Fornecedor fornecedor, Situacao situacao, Date dtVenc, Date includeDate,
			Date dtPagto, Double valorInicial, Double valorJuro, Double valorDesconto, Double valorPago,
			Usuario usuario) {
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



	@PrePersist
    public void prePersist(){
        if(this.getIncludeDate() == null){
        	Calendar brDate = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"),new Locale("pr", "BR"));
            this.setIncludeDate(brDate.getTime());
        }
    }
	
	@Column(name = "dt_inserido", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getIncludeDate() {
        return this.includeDate; 
    }

    public void setIncludeDate(Date includeDate) {
        this.includeDate = includeDate;
    }

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

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public Date getDtVenc() {
		return dtVenc;
	}

	public void setDtVenc(Date dtVenc) {
		this.dtVenc = dtVenc;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}

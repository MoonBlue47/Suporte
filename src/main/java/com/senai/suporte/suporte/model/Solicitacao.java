package com.senai.suporte.suporte.model;

import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "solicitacao")
public class Solicitacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "NIF é obrigatorio")
	@Column(nullable = false)
	private String nif;
	@NotBlank(message = "Nome do solicitante é obrigatorio")
	@Size(min = 3, max = 100, message = "Nome tem que ter entre 3 e 100 caracteres")
	private String nomeSolicitante;
	@NotBlank(message = "Numero da sala é obrigatorio")
	@Column(nullable = false)
	private String numeroSala;
	@NotBlank(message = "O código de patrimonio é obrigatorio")
	@Column(nullable = false)
	private String codigoPatrimonio;
	@NotBlank(message = "A descrição é obrigatoria")
	@Column(nullable = false)
	private String descricaoProblema;
	@NotBlank(message = "O tipo do problema é obrigatorio")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private String tipoProblema;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private StatusSolicitacao status = StatusSolicitacao.PENDENTE;

	/*==========================
	Método Construtor
	==========================*/
	
	public Solicitacao() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNomeSolicitante() {
		return nomeSolicitante;
	}

	public void setNomeSolicitante(String nomeSolicitante) {
		this.nomeSolicitante = nomeSolicitante;
	}

	public String getNumeroSala() {
		return numeroSala;
	}

	public void setNumeroSala(String numeroSala) {
		this.numeroSala = numeroSala;
	}

	public String getCodigoPatrimonio() {
		return codigoPatrimonio;
	}

	public void setCodigoPatrimonio(String codigoPatrimonio) {
		this.codigoPatrimonio = codigoPatrimonio;
	}

	public String getDescricaoProblema() {
		return descricaoProblema;
	}

	public StatusSolicitacao getStatus() {
		return status;
	}

	public void setStatus(StatusSolicitacao status) {
		this.status = status;
	}

	public void setDescricaoProblema(String descricaoProblema) {
		this.descricaoProblema = descricaoProblema;
	}

	public String getTipoProblema() {
		return tipoProblema;
	}

	public void setTipoProblema(String tipoProblema) {
		this.tipoProblema = tipoProblema;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass())return false;
		Solicitacao that = (Solicitacao) o;
		return id != null && id.equals(that.id);
	}
	@Override
	public int hashCode() {
		return Objects.hash(getClass());
	}
	
	public enum TipoProblema{
		IMFORMATICA("Informática"),
		ELETRICA("Elétrica"),
		ZELADORIA("Zeládoria");
		
		private final String descricao;
		
		TipoProblema(String descricao){
			this.descricao=descricao;
		}
		
		public String getDescricao() {
			return descricao;
		}
	}
	public enum StatusSolicitacao{
		PENDENTE("Pendent"),
		EM_ANDAMENTO("Em Andamento"),
		CONCLUIDO("Concluido");
		
		private final String descricao;
		
		StatusSolicitacao(String descricao){
			this.descricao = descricao;
		}
		public String getDescricao() {
			return descricao;
		}
	}
	
}
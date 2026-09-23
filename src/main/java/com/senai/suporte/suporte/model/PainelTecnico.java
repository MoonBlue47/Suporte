package com.senai.suporte.suporte.model;

import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "painel tecnico")
public class PainelTecnico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	@JoinColumn(name = "solicitacao_id", nullable = false, unique = true)
	private Solicitacao solicitacao;
	@NotBlank(message = "Nome do tecnico é obrigatorio")
	@Column(nullable = false)
	private String tecnicoResponsavel;
	@Column(columnDefinition = "TEXT")
	private String observacoes;
	
	public PainelTecnico() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public String getTecnicoResponsavel() {
		return tecnicoResponsavel;
	}

	public void setTecnicoResponsavel(String tecnicoResponsavel) {
		this.tecnicoResponsavel = tecnicoResponsavel;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	/*=============================================================
	 * Equals() e hasCOde() - igualdade baseada no id (padrão jpa)
	 *============================================================= */
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != getClass())return false;
		PainelTecnico that = (PainelTecnico) o;
		return id != null && id.equals(that.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getClass());
	}

}
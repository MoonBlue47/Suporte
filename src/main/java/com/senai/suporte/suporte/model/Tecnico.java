package com.senai.suporte.suporte.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "tecnicos")
public class Tecnico {
	//===========================
	//Chave primaria
	//===========================
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Nome é obrigatorio")
	@Size(min = 3, max = 100, message = "Nome deve conter entre 3 e 100 caractesres")
	@Column(nullable = false)
	private String nome;
	@NotBlank(message = "Email é obrigatorio")
	@Email(message = "Informe um e-mail valido")
	@Column(nullable = false, unique = true)
	private String email;
	@NotBlank(message = "Senha é obrigatoria")
	@Size(message = "A senha deve ter pelo menos 6 caracteres")
	@Column(nullable = false)
	private String senha;
	
	public Tecnico() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != getClass())return false;
		Tecnico tecnico = (Tecnico) o;
		return id != null && id.equals(tecnico.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getClass());
	}
}
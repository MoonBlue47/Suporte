package com.senai.suporte.suporte.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.senai.suporte.suporte.model.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long>{
	Optional<Tecnico> findByEmail(String email);
	
	@Query("SELECT t FROM Tecnico t WHERE t.email = LOWER(:login) OR LOWER(t.nome) = LOWER(:login)")
	List<Tecnico>buscarPorEmailOuNome(@Param("login") String login);

}
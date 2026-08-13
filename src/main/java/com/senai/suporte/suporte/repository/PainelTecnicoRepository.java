package com.senai.suporte.suporte.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.senai.suporte.suporte.model.PainelTecnico;


public interface PainelTecnicoRepository extends JpaRepository<PainelTecnico, Long>{
	
	Optional<PainelTecnico> findBySolicitcaoId(Long solicitacao);

}
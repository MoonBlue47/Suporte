package com.senai.suporte.suporte.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.senai.suporte.suporte.exception.RecursoNaoEncontradoException;
import com.senai.suporte.suporte.model.PainelTecnico;
import com.senai.suporte.suporte.model.Solicitacao;
import com.senai.suporte.suporte.model.Solicitacao.StatusSolicitacao;
import com.senai.suporte.suporte.repository.PainelTecnicoRepository;
import com.senai.suporte.suporte.repository.SolicitacaoRepository;

@Service
public class PainelTecnicoService {
	private final PainelTecnicoRepository painelTecnicoRepository;
	private final SolicitacaoRepository solicitacaoRepository;
	
	@Autowired
	public PainelTecnicoService(PainelTecnicoRepository painelTecnicoRepository,
			SolicitacaoRepository solicitacaoRepository) {
		super();
		this.painelTecnicoRepository = painelTecnicoRepository;
		this.solicitacaoRepository = solicitacaoRepository;
	}
	
	@Transactional
	public PainelTecnico assumir(Long solicitacaoId, String tecnicoResponsavel, String observacoes) {
		Solicitacao solicitacao = solicitacaoRepository.findById(solicitacaoId).orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação", solicitacaoId));
		
		if(solicitacao.getStatus() != StatusSolicitacao.PENDENTE){
			throw new IllegalStateException("Esta solicitacao não pode ser assumida, satatus atual:"+solicitacao.getStatus().getDescricao());
		}
		PainelTecnico painel = new PainelTecnico();
		painel.setSolicitacao(solicitacao);
		painel.setTecnicoResponsavel(tecnicoResponsavel);
		painel.setObservacoes(observacoes);
		
		solicitacao.setStatus(StatusSolicitacao.EM_ANDAMENTO);
		solicitacaoRepository.save(solicitacao);
		return painelTecnicoRepository.save(painel);
	}
	
	@Transactional
	public void concluir(Long solicitacaoId) {
       Solicitacao solicitacao = solicitacaoRepository.findById(solicitacaoId).orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação", solicitacaoId));
		
		if(solicitacao.getStatus() != StatusSolicitacao.PENDENTE){
			throw new IllegalStateException("Esta solicitacao não poder ser concluida, satatus atual:"+solicitacao.getStatus().getDescricao());
		}
		solicitacao.setStatus(StatusSolicitacao.CONCLUIDO);
		solicitacaoRepository.save(solicitacao);
	}
	
	@Transactional(readOnly = true)
	public Optional<PainelTecnico> buscarPorSlicitacao(Long solicitacaoId){
		return painelTecnicoRepository.findBySolicitcaoId(solicitacaoId);
		
	}
	
}
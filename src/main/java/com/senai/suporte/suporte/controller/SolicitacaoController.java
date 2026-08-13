package com.senai.suporte.suporte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senai.suporte.suporte.model.Solicitacao;
import com.senai.suporte.suporte.repository.SolicitacaoRepository;
import com.senai.suporte.suporte.service.SolicitacaoService;

import jakarta.validation.Valid;

public class SolicitacaoController {
	private final SolicitacaoService solicitacaoService;
	
	@Autowired
	public SolicitacaoController(SolicitacaoService solicitacaoService) {
		super();
		this.solicitacaoService = solicitacaoService;
	}
	
	@GetMapping
	public String exibirFormulario(Model model) {
		model.addAttribute("solicitacao", new Solicitacao());
		model.addAttribute("tipos", Solicitacao.TipoProblema.values());
		return "solicitacao/formulario";
	}
	
	@PostMapping
	public String salvar(@Valid @ModelAttribute Solicitacao solicitacao, BindingResult resultado, Model model, RedirectAttributes flash) {
		if(resultado.hasErrors()) {
			model.addAttribute("tipos", Solicitacao.TipoProblema.values());
			return "solicitacao/formulario";
		}
		
		solicitacaoService.salvar(solicitacao);
		
		flash.addFlashAttribute("Sucesso", "Chamado aberto com sucesso! Em breve um técnico irá atende-lo");
		return "redirect:/solicitacao";
	}
	
}

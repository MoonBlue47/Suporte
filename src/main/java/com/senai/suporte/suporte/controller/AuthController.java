package com.senai.suporte.suporte.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senai.suporte.suporte.model.Tecnico;
import com.senai.suporte.suporte.service.TecnicoService;

@Controller
public class AuthController {
	private final TecnicoService tecnicoService;
	
	public AuthController(TecnicoService tecnicoService) {
		this.tecnicoService = tecnicoService;
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/Cadastro")
	public String exibirCadastro(Model model) {
		model.addAttribute("tecnico", new Tecnico());
		return "cadastro";
	}
	
	@PostMapping("/cadastro")
	public String cadastrar(@Valid @ModelAttribute Tecnico tecnico, BindingResult resultado, Model model, RedirectAttributes flash) {
		if(resultado.hasErrors()) {
			return "cadastro";
		}
		try {
			tecnicoService.cadastrar(tecnico);
		} catch (Exception e) {
			model.addAttribute("erro", e.getMessage());
		}
		
		flash.addFlashAttribute("Sucesso", "Cadastro realizado com sucesso! Faça o login para continuar");
		return "redirect:/login";
	}
}

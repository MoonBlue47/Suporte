package com.senai.suporte.suporte.config;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.senai.suporte.suporte.exception.RecursoNaoEncontradoException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(RecursoNaoEncontradoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNaoEncontrado(RecursoNaoEncontradoException ex, Model model) {
		model.addAttribute("mensagemErro", ex.getMessage());    
		return "erro";
	}
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String headleRuntimeException(RuntimeException ex, Model model) {
		model.addAttribute("mensagemErro", ex.getMessage());
		return "Erro";
	}
}
package com.senai.suporte.suporte.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.senai.suporte.suporte.model.Tecnico;
import com.senai.suporte.suporte.repository.TecnicoRepository;
import java.util.List;

public class TecnicoUserDetailsService {
     private final TecnicoRepository tecnicoRepository;

     public TecnicoUserDetailsService(TecnicoRepository tecnicoRepository) {
         this.tecnicoRepository = tecnicoRepository;
     }

     public UserDetails loadUserByName(String login) throws UsernameNotFoundException {
         String loginLimpo = login.trim();

         List<Tecnico> encontrados = tecnicoRepository.buscarPorEmailOuNome(loginLimpo);
         if (encontrados.isEmpty()) {
             throw new UsernameNotFoundException("Técnico não encontrado:"+loginLimpo);
         }
         if(encontrados.size() > 1) {
             throw new UsernameNotFoundException("Há mais de um usuário com esse nome, Entre com o e-mail");
         }

         Tecnico tecnico = encontrados.get(0);
         return User.builder().username(tecnico.getEmail()).password(tecnico.getSenha()).roles("TECNICO").build();

    }

}
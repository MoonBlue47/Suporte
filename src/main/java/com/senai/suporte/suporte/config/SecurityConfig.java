package com.senai.suporte.suporte.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain ilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(auth->auth.requestMatchers("/","solicitacao","/login","/cadastro","/css/**","/js/**","/imagens/**").permitAll()
				.requestMatchers("/painel/**").authenticated().anyRequest().authenticated()).formLogin(form->form.loginPage("/login")
						.defaultSuccessUrl("/painel",true).permitAll()).logout(logout->logout.logoutSuccessUrl("/login?logout").permitAll());
		return http.build();
		
	}
}
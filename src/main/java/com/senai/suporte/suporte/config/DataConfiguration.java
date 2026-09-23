package com.senai.suporte.suporte.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DataConfiguration {
	private static final String URL = "jdbc:mysql://localhost:3306/suporte"+"?createDatabaseIfNotExist=true&useSSL=false&serveTimezone=America/Sao_Paulo";
	private static final String USUARIO = "root";
	private static final String SENHA = System.getenv().getOrDefault("DB_PASSWORD", "senia@126");	
	
	@Bean
	public  DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl(URL);
		dataSource.setUsername(USUARIO);
		dataSource.setPassword(SENHA);
		
		return dataSource;
	}
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(true);
		boolean bancoJaCriado = bancoJaCriado();
		adapter.setGenerateDdl(!bancoJaCriado);
		
		
		if(bancoJaCriado) {
			System.out.println("[DataConfiguration] Banco já existe - > as tabelas não seraão criadas");
		}else {
			System.out.println("[DataConfiguration] Primaiera ewxecução -> Criando as tabelas do banco");
		}
			
		adapter.setDatabasePlatform("or.hibernate.dialect.MySQLDialect");
		adapter.setPrepareConnection(true);
		return adapter;	
			
	}
	
	public boolean bancoJaCriado() {
		String[] tabelasEsperadas = {"solicitações", "tecnicos", "painelTecnico"};
		try (Connection conexao = DriverManager.getConnection(URL,USUARIO,SENHA)){
			DatabaseMetaData metadados = conexao.getMetaData();
			for(String tabela: tabelasEsperadas) {
				try(ResultSet rs = metadados.getTables(conexao.getCatalog(), null, tabela, new String[] {"TABLE"})){
					if(!rs.next()) {
						return false;
					}
				}
			}
			return false;
			
		} catch (SQLException e) {
			System.out.println("[DataConfiguration] Não foi possivel verificar o banco de dados: " + e.getMessage());
			return false;
		}
	}
}
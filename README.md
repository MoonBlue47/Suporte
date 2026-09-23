# 🛠️ SENAI Suporte — Sistema de Gestão de Chamados

Plataforma integrada de abertura, encaminhamento e gestão de chamados de manutenção e suporte escolar (Informática, Elétrica e Zeladoria).

<div align="center">
  <img src="./README/" width="500">
</div>

---

## 📌 Funcionalidades

- **Abertura de Chamados:** Cadastro simples por NIF, número de sala, código de património e descrição da ocorrência.
- **Painel Técnico Operacional:** Visão geral de métricas, filtragem dinâmica por status/categoria e busca contextual.
- **Fluxo de Atendimento:** Capacidade de assumir chamados com atribuição técnica e encerramento com anotações de serviço.
- **Área Autenticada:** Interface de login e registo para equipas técnicas.
- **Armazenamento de Sessão:** Compatível com dados persistidos em tempo de execução/LocalStorage para testes rápidos.

---

## 💻 Tecnologias Utilizadas

### Backend
- **Java 17+**
- **Spring Boot** (MVC, Core, Web)
- **Maven** (Gestão de dependências e build)

### Frontend
- **HTML5 & CSS3 Moderno** (Design system com variáveis CSS, responsividade nativa e utilitários)
- **JavaScript (ES6+)** (Manipulação de DOM, consumo de filtros e gestão de dados)
- **Phosphor Icons** (Tipografia de ícones)

---

## 📂 Estrutura do Projeto

```text
Suporte/
├── src/
│   ├── main/
│   │   ├── java/com/senai/suporte/suporte/
│   │   │   ├── config/          # Classes de configuração do Spring
│   │   │   ├── controller/      # Endpoints e rotas da aplicação
│   │   │   ├── exception/       # Tratamento customizado de erros
│   │   │   ├── model/           # Entidades e regras de negócio
│   │   │   ├── repository/      # Interfaces de acesso a dados
│   │   │   ├── service/         # Camada de lógica de negócio
│   │   │   └── SuporteApplication.java
│   │   └── resources/
│   │       ├── static/          # Frontend (HTML, CSS, JS)
│   │       └── application.properties
│   └── test/                    # Testes unitários e de integração
├── pom.xml                      # Ficheiro de configuração do Maven
└── README.md

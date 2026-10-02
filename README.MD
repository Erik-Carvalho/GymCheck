# 🏋️ GymCheck — PWA para Acompanhamento e Evolução de Treinos

> **Treine. Registre. Evolua.**

O **GymCheck** é uma aplicação web progressiva (PWA) desenvolvida em Java com Spring Boot, projetada para substituir o uso de papéis e blocos de notas durante os treinos na academia. Com um design focado em dispositivos móveis (*mobile-first*), a aplicação permite que o atleta gerencie seu perfil corporal, acompanhe treinos, registre cargas/repetições e monitore sua evolução física com persistência segura na nuvem.

## 📸 Visão Geral e Funcionalidades

### 🌟 Funcionalidades Principais

* **Perfil do Atleta:** Gestão de dados corporais (Nome, Data de Nascimento, Gênero, Altura) e acompanhamento de métricas.

* **Registro Dinâmico de Treinos:** Checagem rápida de séries executadas, peso utilizado e repetições diretamente na academia.

* **Progressão de Cargas:** Histórico de evolução por exercício para otimização do estímulo muscular.

* **Experiência PWA (Progressive Web App):** Suporte à instalação na tela inicial (Android/iOS) sem precisar baixar na App Store ou Play store, navegação em tela cheia e armazenamento de arquivos estáticos via Service Worker (`sw.js`).

* **Autenticação e Segurança:** Controle de acesso com Spring Security, senhas criptografadas (`BCryptPasswordEncoder`) e proteção contra falhas de segurança comuns (CSRF, XSS).

## 🛠️ Tecnologias Principais

| Categoria | Tecnologia | Descrição | 
 | ----- | ----- | ----- | 
| **Linguagem** | Java 17 | JDK LTS de alta performance e estabilidade | 
| **Framework Backend** | Spring Boot 3 | Framework principal para APIs e regras de negócio | 
| **Segurança** | Spring Security 6 | Autenticação baseada em sessão/formulário e controle de rotas | 
| **Acesso a Dados / ORM** | Spring Data JPA / Hibernate | Mapeamento objeto-relacional e gerenciamento de persistência | 
| **Banco de Dados (Dev)** | H2 Database | Banco em memória para desenvolvimento local e testes ágeis | 
| **Banco de Dados (Prod)** | PostgreSQL | Banco de dados relacional hospedado na nuvem (Neon.tech Serverless) | 
| **Template Engine** | Thymeleaf | Renderização dinâmica de HTML5 no lado do servidor | 
| **Protótipo Design do App** | Vercel | Protótipo estático/UI das telas do app para validação visual |
| **Estilização / UI** | Tailwind CSS | Framework utilitário para layout responsivo e mobile-first | 
| **PWA & Client-Side** | Service Workers & Web App Manifest | Suporte a instalação no sistema operacional e navegação offline basica | 
| **Gerenciador de Build** | Maven | Automação e gerenciamento de dependências via Maven Wrapper (`mvnw`) | 
| **Containerização** | Docker | Imagem customizada com suporte a Multi-Stage Build | 
| **Hospedagem Cloud** | Render.com | Web Service hospedado via container Docker e automação de deploy | 
| **Monitorização / Keep-Alive** | UptimeRobot | Prevenção do ColdStart do Render | 
## 🏗️ Arquitetura e Ambientes (Spring Profiles)

A aplicação utiliza **Spring Profiles** para isolar as configurações entre o ambiente de desenvolvimento local e o ambiente de produção em nuvem:

```
GymCheck
 ├── 🔹 dev  ──> H2 Database (In-Memory) + Logs detalhados
 └── 🔸 prod ──> PostgreSQL (Neon Serverless via SSL) + Hibernate DDL Update

```

* **`application-dev.properties`:** Ativo por padrão em desenvolvimento. O banco H2 roda em memória, dispensando instalações adicionais no computador.

* **`application-prod.properties`:** Ativado em produção no Render. Obtém as credenciais dinamicamente via variáveis de ambiente seguras e estabelece conexão criptografada com o PostgreSQL do Neon.tech.

## 📁 Estrutura do Projeto

```
gymcheck/
 ├── src/
 │    ├── main/
 │    │    ├── java/com/gymcheck/gymcheck/
 │    │    │    ├── config/          # Spring Security e configurações globais
 │    │    │    ├── controller/      # Controllers Thymeleaf (Perfil, Treino, Login)
 │    │    │    ├── dto/             # Data Transfer Objects com validações de entrada
 │    │    │    ├── model/           # Entidades JPA (Usuario, Treino, Exercicio, etc.)
 │    │    │    ├── repository/      # Repositórios Spring Data JPA
 │    │    │    └── service/         # Camada de regras de negócio
 │    │    └── resources/
 │    │         ├── static/          # CSS, JavaScript, manifest.json e sw.js (PWA)
 │    │         ├── templates/       # Views Thymeleaf (HTML5 + Tailwind)
 │    │         ├── application.properties
 │    │         ├── application-dev.properties
 │    │         └── application-prod.properties
 ├── Dockerfile                      # Instruções de build Docker para produção no Render
 └── pom.xml                         # Dependências do projeto Maven

```

## 📱 Instalação e Execução Local

### Pré-requisitos

* **Java 17 JDK** instalado.

* **Git** instalado.

### Passo a Passo

1. **Clone o repositório:**

   ```
   git clone https://github.com/Erik-Carvalho/GymCheck.git
   cd GymCheck
   
   ```

2. **Execute a aplicação (Perfil `dev` por padrão):**

   * **Linux/macOS:**

     ```
     ./mvnw spring-boot:run
     
     ```

   * **Windows (Git Bash / CMD):**

     ```
     mvnw spring-boot:run
     
     ```

3. **Acesse no navegador:**

   * **Aplicação:** `http://localhost:8080`

   * **H2 Console:** `http://localhost:8080/h2-console` *(se ativado em dev)*


4. **Criar o Banco de Dados no Neon.tech**

* Crie uma conta gratuita em neon.tech.

* Crie um projeto e copie os dados da string de conexão PostgreSQL fornecida.

5. **Configurar o Serviço Web no Render.com**

* Acesse render.com e crie um novo Web Service.

* Conecte o repositório do seu GitHub contendo o projeto.

* Escolha o runtime Docker.

* Configure as Variáveis de Ambiente (Environment Variables) no painel do Render:
## ☁️ Configuração de Produção (Render.com + Neon.tech)

Para realizar o deploy no Render conectado ao banco PostgreSQL no Neon.tech, adicione as seguintes **Environment Variables** no painel do Web Service:

| Chave | Descrição | Exemplo de Valor | 
 | ----- | ----- | ----- | 
| `SPRING_PROFILES_ACTIVE` | Define o perfil de execução ativo | `prod` | 
| `SPRING_DATASOURCE_URL` | String de conexão JDBC com o Neon | `jdbc:postgresql:.....` | 
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco no Neon | `neondb_owner` | 
| `SPRING_DATASOURCE_PASSWORD` | Senha de acesso do Neon | `sua_senha` | 

**DICA**
*Para remover o ColdStart padrão do Render, estou utilizando o UptimeRobot que fará uma requisição a cada 5 minutos, mantendo o contêiner sempre ativo na memória.

## ✒️ Autor
Desenvolvido por **Erik Carvalho**.
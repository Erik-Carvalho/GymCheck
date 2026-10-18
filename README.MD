# 🏋️ GymCheck — PWA para Acompanhamento e Evolução de Treinos

> **Treine. Registre. Evolua.**

O **GymCheck** é uma aplicação web progressiva (PWA) desenvolvida em Java com Spring Boot, projetada para substituir o uso de papéis e blocos de notas durante os treinos na academia. Com um design focado em dispositivos móveis (*mobile-first*), a aplicação permite que o atleta gerencie seu perfil corporal, acompanhe treinos, registre cargas/repetições e monitore sua evolução física com persistência segura na nuvem.

Pensado para **uso próprio e para um grupo limitado de usuários**, todo o ecossistema do GymCheck foi arquitetado de forma **100% gratuita** - eliminando custos de desenvolvimento e manutenção contínua -, mas sem abrir mão da alta disponibilidade, garantindo que a aplicação permaneça online e pronta para uso a todo momento.

## 📸 Visão Geral e Funcionalidades

### 🌟 Funcionalidades Principais

* **Perfil do Atleta:** Gestão de dados corporais (Nome, Data de Nascimento, Gênero, Altura) e acompanhamento de métricas.

* **Treinos e Histórico:** Rotinas por dia da semana, status pendente/concluído no dashboard e registro das séries, cargas e repetições realmente executadas.

* **Evolução Física:** Gráficos de progressão de cargas por exercício e histórico de peso, cintura e braço.

* **Dieta e Nutrição:** Registro de refeições por data, calorias e macronutrientes, com metas diárias opcionais por usuário.

* **Recuperação de Senha:** Redefinição por link de uso único, com validade de 30 minutos e envio transacional pela API HTTPS do Brevo.

* **Experiência PWA (Progressive Web App):** Instalação na tela inicial (Android/iOS), abertura em tela cheia e cache de recursos estáticos via Service Worker (`sw.js`). Os dados e ações da conta precisam de conexão com a internet.

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
| **E-mail Transacional** | Brevo API (HTTPS) | Envio de e-mails de recuperação sem depender de portas SMTP |
| **Protótipo Design do App** | Vercel | Protótipo estático/UI das telas do app para validação visual |
| **Estilização / UI** | Tailwind CSS | Framework utilitário para layout responsivo e mobile-first | 
| **PWA & Client-Side** | Service Workers & Web App Manifest | Suporte a instalação no sistema operacional e navegação offline basica | 
| **Gerenciador de Build** | Maven | Automação e gerenciamento de dependências via Maven Wrapper (`mvnw`) | 
| **Containerização** | Docker | Imagem customizada com suporte a Multi-Stage Build | 
| **Hospedagem Cloud** | Render.com | Web Service hospedado via container Docker e automação de deploy | 
| **Monitorização / Keep-Alive** | UptimeRobot | Prevenção do Cold Start e Deep Sleep do Render | 
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
| `GYMCHECK_BASE_URL` | URL pública usada nos links de recuperação | `https://seu-app.onrender.com` |
| `BREVO_API_KEY` | Chave da API transacional v3 do Brevo (não é a chave SMTP) | `xkeysib-...` |
| `MAIL_FROM` | Endereço remetente verificado no Brevo | `noreply@seudominio.com` |

No Render Free, configure `BREVO_API_KEY` e `MAIL_FROM`. A aplicação envia pela API HTTPS do Brevo, evitando as portas SMTP bloqueadas nesse plano. Gere uma chave de API v3 no painel do Brevo e confirme que `MAIL_FROM` é um remetente verificado. Salve a chave como variável secreta; não a inclua no código ou no repositório. A resposta da recuperação é genérica por segurança; confira os logs do serviço para diagnosticar falhas de envio.

**DICA**
Para remover o ColdStart padrão do Render, estou utilizando o UptimeRobot que faz uma requisição a cada 5 minutos, mantendo o contêiner sempre ativo na memória, evitando que a aplicação entre em modo Deep Sleep.

## 📲 Instalar o GymCheck no celular

O GymCheck é um PWA e pode ser adicionado à tela inicial sem baixar pela Play Store ou App Store. Abra a URL publicada pelo render depois do deploy em um navegador compatível; a conexão deve usar HTTPS.

### Android

1. Abra o GymCheck no Chrome.
2. Toque no menu de três pontos e escolha **Instalar aplicativo** ou **Adicionar à tela inicial**.
3. Confirme. O ícone do GymCheck aparecerá na tela inicial e na lista de aplicativos.

### iPhone ou iPad

1. Abra o GymCheck no Safari.
2. Toque em **Compartilhar** e escolha **Adicionar à Tela de Início**.
3. Confirme o nome e toque em **Adicionar**. O ícone ficará disponível na tela inicial.

Ao abrir pelo ícone, o app é exibido em modo de aplicativo. O acesso aos dados e às ações da conta continua precisando de conexão com a internet.

## ✒️ Autor
Desenvolvido por **Erik Carvalho**.

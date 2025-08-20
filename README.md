# LiterAlura 📖

## 📝 Descrição do Projeto
**LiterAlura** é um catálogo de livros interativo que funciona via terminal (console).  
O projeto foi desenvolvido como parte do desafio de programação da **Alura** para praticar o uso de **Java** com o framework **Spring**.  

A aplicação consome a API gratuita **Gutendex** para buscar informações sobre livros e autores, e persiste esses dados em um banco de dados **PostgreSQL**, permitindo consultas posteriores sem a necessidade de novas chamadas à API.

---

## ✨ Funcionalidades
A aplicação oferece um menu com 5 opções de interação:

1. **Buscar livro pelo título**  
   Realiza uma busca na API Gutendex, encontra o livro e o salva no banco de dados, incluindo informações do autor.

2. **Listar livros registrados**  
   Exibe todos os livros que foram salvos no banco de dados.

3. **Listar autores registrados**  
   Exibe todos os autores que foram salvos no banco de dados, com seus anos de nascimento e falecimento.

4. **Listar autores vivos em um determinado ano**  
   Permite ao usuário inserir um ano e exibe os autores que estavam vivos naquele período.

5. **Listar livros em um determinado idioma**  
   Exibe os livros registrados em um dos idiomas suportados *(espanhol, inglês, francês, português)*.

---

## 🛠️ Tecnologias Utilizadas
- **Linguagem:** Java 17  
- **Framework:** Spring Boot 3  
- **Spring Data JPA:** Para persistência de dados e criação de repositórios  
- **Spring Core:** Para injeção de dependências e gerenciamento de componentes  
- **Banco de Dados:** PostgreSQL  
- **Mapeamento JSON:** Jackson Databind  
- **Gerenciador de Dependências:** Maven  

---

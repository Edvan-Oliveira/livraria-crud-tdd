# 📝 Sobre

Este é um projeto de um CRUD de uma API REST desenvolvida com a técnica do TDD (Test Driven Development/Desenvolvimento Orientado por Testes).

O objetivo deste projeto é demonstrar as boas práticas no desenvolvimento de testes de APIs, mais especificamente, APIs desenvolvidas com o framework [Spring Boot](https://spring.io/projects/spring-boot).

A estrutura de testes do projeto está assim:

- Camada de serviço → testes unitários com anotação @ExtendWith
- Camada de repositório → testes de integração com anotação @DataJpaTest
- Camada de recurso → testes de integração com anotação @WebMvcTest
- Aplicação → teste de integração de todas as camadas com anotação @SpringBootTest

<br/>

# 🎨 Imagens

<div align="center">
    <div>
        <p>Lista de todos os livros</p>
        <img src="https://github.com/Edvan-Oliveira/imagens/blob/main/livraria-crud-tdd/listarTodos.png?raw=true" alt="Listar todos">
    </div>
    <div>
        <p>Cadastro de um livro</p>
        <img src="https://github.com/Edvan-Oliveira/imagens/blob/main/livraria-crud-tdd/cadastroSucesso.png?raw=true" alt="Cadastro com sucesso">
    </div>
    <div>
        <p>Erro de livro sem atributos</p>
        <img src="https://github.com/Edvan-Oliveira/imagens/blob/main/livraria-crud-tdd/cadastrarLivroAtributosNulos.png?raw=true" alt="Cadastro com sucesso">
    </div>
    <div>
        <p>Erro de ISBN duplicado</p>
        <img src="https://github.com/Edvan-Oliveira/imagens/blob/main/livraria-crud-tdd/cadastroISBNDuplicado.png?raw=true" alt="Cadastro isbn duplicado">
    </div>
    <div>
        <p>Buscar livro pelo ID</p>
        <img src="https://github.com/Edvan-Oliveira/imagens/blob/main/livraria-crud-tdd/buscarPorIDSucesso.png?raw=true" alt="Buscar por ID com sucesso">
    </div>  
    <div>
        <p>Atualização de um livro</p>
        <img src="https://github.com/Edvan-Oliveira/imagens/blob/main/livraria-crud-tdd/atualizarSucesso.png?raw=true" alt="Atualizar com sucesso">
    </div>   
    <div>
        <p>Erro de livro não encontrado</p>
        <img src="https://github.com/Edvan-Oliveira/imagens/blob/main/livraria-crud-tdd/buscarPorIDInexistente.png?raw=true" alt="Buscar por ID inexistente">
    </div>
</div>


# 🚀 Tecnologias utilizadas

## Para o desenvolvimento da aplicação

- Java 17
- Spring Boot 2.6
- PostgreSQL
- Spring Data JPA
- Lombok
- Maven

## Para a construção dos testes

- JUnit 5
- AssertJ
- Mockito
- Banco de dados H2
- Spring Boot Test

<br/>

# 🔗 Link para acessar a API remotamente

Para acessar o API que está hospedada na plataforma [Heroku](https://id.heroku.com/login) e poder testar as rotas, basta clicar aqui: https://edvan-livraria-crud-tdd.herokuapp.com/livros

Para a URL de livros, estão disponíveis as operações básicas de um CRUD de acordo com o padrão REST:

- /livros (POST) → Cadastra um novo livro
- /livros (GET) → Lista todos os livros
- /livros/{id} (PUT) → Atualiza o livro informado
- /livros/{id} (GET) → Busca o livro pelo ID
- /livros/{id} (DELETE) → Deleta o livro

<br/>

# 👓 Instruções de como rodar o projeto localmente em sua máquina

Para executar a API localmente, pode-se escolher dois caminhos, o caminhos com as configurações de teste e o outro caminho com as configurações de desenvolvedor.

#

## ✏️ Pré-Requisitos

Para executar localmente, apenas será necessário ter instalados em sua máquina os seguintes programas: [Java](https://www.java.com/pt-BR/), [Git](https://git-scm.com/) e [Maven](https://maven.apache.org/).

#

## 📗 Configurações de teste

Com as configurações de testes, o banco de dados utilizado será o H2, ele é um banco de dados em memória, com isso, todas as vezes que a aplicação for parada, todos os dados serão apagados.

Com os programas pré-requisitos instalados, agora é só executar em um terminal os comandos abaixo.

```bash
$ git clone https://github.com/Edvan-Oliveira/livraria-crud-tdd.git

$ cd livraria-crud-tdd

$ mvn clean install

$ cd target

$ java -jar livraria-crud-tdd-0.0.1-SNAPSHOT.jar
```
#

## 📕 Configurações de desenvolvedor

Com as configurações de desenvolvedor, o banco de dados utilizado será o [PostgreSQL](https://www.postgrescompare.com/), por tanto é preciso ter ele instalado em sua máquina.

Com todos os programas devidamente instalados, segue as instruções:

1 - Primeiro é preciso criar um banco de dados chamado _livraria-crud-tdd_ no [PostgreSQL](https://www.postgrescompare.com/).

```sql
    CREATE DATABASE livraria-crud-tdd ENCODING 'UTF8';
```

2- Em seguida é necessário criar o usuário com permissão de login, o nome será _adm_ e a senha _123456_.

```sql
    CREATE ROLE adm WITH LOGIN SUPERUSER PASSWORD '123456';
```
3- Por fim, agora é só executar em um terminal os comandos abaixo.

```bash
$ git clone https://github.com/Edvan-Oliveira/livraria-crud-tdd.git

$ cd livraria-crud-tdd

$ mvn clean install

$ cd target

$ java -Dspring.profiles.active=dev -jar livraria-crud-tdd-0.0.1-SNAPSHOT.jar
```

#

## Contatos

<div>
    <a href="https://www.linkedin.com/in/edvan-oliveira-0822b2227/" target="_blank"><img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white" target="_blank"></a>
  <a href = "mailto:edvan.oliveiract@gmail.com"><img src="https://img.shields.io/badge/-Gmail-%23333?style=for-the-badge&logo=gmail&logoColor=white" target="_blank"></a>
  <a href = "https://t.me/Edvan_Oliveira"><img src="https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white" target="_blank"></a>

</div>

#

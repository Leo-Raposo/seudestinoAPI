# Seu Destino API

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

## Descrição

A **Seu Destino API** é uma aplicação RESTful construída com **Spring Boot** e **PostgreSQL**, projetada para gerenciar viagens e destinos. Ela permite que os usuários criem, atualizem, excluam e visualizem informações sobre viagens e destinos, mantendo um relacionamento de um para muitos entre essas entidades.

## Funcionalidades

### Endpoints de Destino

- **GET /destinos**: Retorna a lista de todos os destinos cadastrados.
- **GET /destinos/{id}**: Retorna os detalhes de um destino específico.
- **GET /destinos/contagem**: Retorna o número total de destinos cadastrados.
- **GET /destinos/populares**: Retorna o TOP 10 destinos mais visitados (ordenados pelo número de viagens associadas).
- **POST /destinos**: Cria um novo destino.
- **PUT /destinos/{id}**: Atualiza as informações de um destino existente.
- **DELETE /destinos/{id}**: Remove um destino específico.

### Endpoints de Viagem

- **GET /viagens**: Retorna a lista de todas as viagens cadastradas.
- **GET /viagens/{id}**: Retorna os detalhes de uma viagem específica.
- **GET /viagens/active**: Retorna uma lista de viagens que estão ativas no momento (cuja data de início já passou e a data de término ainda não chegou).
- **GET /viagens/por-periodo?inicio={dataInicio}&fim={dataFim}**: Retorna uma lista de viagens que ocorrem em um determinado período.
- **GET /destinos/{id}/viagens/recentes**: Retorna uma lista das viagens mais recentes associadas a um destino específico.
- **POST /viagens**: Cria uma nova viagem, associando-a a um destino existente.
- **PUT /viagens/{id}**: Atualiza as informações de uma viagem existente.
- **DELETE /viagens/{id}**: Remove uma viagem específica.

## Estrutura de Dados

### Destino

- **id**: Identificador único do destino.
- **nome**: Nome do destino.
- **pais**: País onde o destino está localizado.
- **viagens**: Lista de viagens associadas a este destino.

### Viagem

- **id**: Identificador único da viagem.
- **titulo**: Título ou nome da viagem.
- **dataInicio**: Data de início da viagem.
- **dataFim**: Data de término da viagem.
- **destino**: Destino associado à viagem.

## Validações

- **Destino**: Nome e país são obrigatórios.
- **Viagem**: Título, data de início, data de término e destino são obrigatórios. A data de início deve ser anterior à data de término.

## Instalação e Configuração

1. Clone o repositório:
    ```bash
    git clone https://github.com/seu-usuario/seudestino-api.git
    ```
2. Navegue até o diretório do projeto:
    ```bash
    cd seudestino-api
    ```
3. Configure o banco de dados PostgreSQL no `application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    ```
4. Execute o projeto com o Maven:
    ```bash
    mvn spring-boot:run
    ```

## Tecnologias Utilizadas

- **Java 11+**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Maven**

## Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para abrir uma issue ou enviar um pull request.


## Contato

Criado por [Leonardo Raposo](https://github.com/Leo-Raposo) - entre em contato!

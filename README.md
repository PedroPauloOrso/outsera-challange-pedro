###### Desafio para Outsera por by Pedro P. Orso ######

# Descrição

    Este projeto é uma API REST construída com Spring Boot para gerenciar filmes, produtores e estúdios. Inclui funcionalidade para calcular os produtores com os maiores e menores intervalos entre premiações.

# Funcionalidades

    Operações CRUD: operações de criação, leitura, atualização e exclusão para filmes, produtores e estúdios.
    Cálculo do intervalo de premiação: Calcule os produtores com os intervalos mais curtos e maiores entre as premiações.
    Carregamento de dados: carrega automaticamente dados de filmes de um arquivo CSV na inicialização.
    API RESTful: expõe endpoints para interagir com os dados.
    Testes de integração: garantem que o aplicativo funcione conforme o esperado.

# Pré-requisitos

    Java 11 ou superior (Utilizando openjdk-22.0.1)
    Maven 3.6 ou superior

# Começando

    mvn clean install
    Execute manualmente o ChallengeApplication.java ou rode o comando mvn spring-boot:run 

# Tests

    Rode o comando mvn test ou manualmente

# Endpoints da API
### Filmes

    GET /v1/movies: Recuperar todos os filmes.
    GET /v1/movies/{id}: Recuperar um filme pelo ID.
    POST /v1/movies: Criar um novo filme.
    PUT /v1/movies/{id}: Atualizar um filme.
    DELETE /v1/movies/{id}: Deletar um filme.

### Produtores

    GET /v1/producers: Recuperar todos os produtores.
    GET /v1/producers/{id}: Recuperar um produtor pelo ID.
    POST /v1/producers: Criar um novo produtor.
    PUT /v1/producers/{id}: Atualizar um produtor.
    DELETE /v1/producers/{id}: Deletar um produtor.

### Estúdios

    GET /v1/studios: Recuperar todos os estúdios.
    GET /v1/studios/{id}: Recuperar um estúdio pelo ID.
    POST /v1/studios: Criar um novo estúdio.
    PUT /v1/studios/{id}: Atualizar um estúdio.
    DELETE /v1/studios/{id}: Deletar um estúdio.

### Premiações

    GET /v1/awards/intervals: Obter produtores com os maiores e menores intervalos entre premiações.

# Tecnologias Utilizadas

    Spring Boot
    Spring Data JPA
    Hibernate
    H2 Database
    Maven
    JUnit 5
    MockMvc
    Lombok
    MapStruct
    OpenCSV


### Postman

1. Pode ser testado pelos postmans exemplo que se encontram na pasta raiz: .\Outsera - Pedro.postman_collection.json






------------------------------------------------------------------------------

# Description

    This project is a Spring Boot REST API designed to manage movies, producers, and studios. It includes functionality to calculate the producers with the longest and shortest intervals between awards.

# Features

    CRUD Operations: Create, Read, Update, and Delete operations for Movies, Producers, and Studios.
    Award Interval Calculation: Calculate producers with the shortest and longest intervals between awards.
    Data Loading: Automatically loads movie data from a CSV file at startup.
    RESTful API: Exposes endpoints for interacting with the data.
    Integration Tests: Ensures the application works as expected.

# Prerequisites

    Java 11 or higher (Using openjdk-22.0.1)
    Maven 3.6 or higher

# Getting Started

    mvn clean install

# Tests

    Run the command: mvn test

# API Endpoints
### Movies

    GET /v1/movies: Retrieve all movies.
    GET /v1/movies/{id}: Retrieve a movie by ID.
    POST /v1/movies: Create a new movie.
    PUT /v1/movies/{id}: Update a movie.
    DELETE /v1/movies/{id}: Delete a movie.

### Producers

    GET /v1/producers: Retrieve all producers.
    GET /v1/producers/{id}: Retrieve a producer by ID.
    POST /v1/producers: Create a new producer.
    PUT /v1/producers/{id}: Update a producer.
    DELETE /v1/producers/{id}: Delete a producer.

### Studios

    GET /v1/studios: Retrieve all studios.
    GET /v1/studios/{id}: Retrieve a studio by ID.
    POST /v1/studios: Create a new studio.
    PUT /v1/studios/{id}: Update a studio.
    DELETE /v1/studios/{id}: Delete a studio.

### Awards

    GET /v1/awards/intervals: Get producers with the longest and shortest intervals between awards.

# Technologies Used

    Spring Boot
    Spring Data JPA
    Hibernate
    H2 Database
    Maven
    JUnit 5
    MockMvc
    Lombok
    MapStruct
    OpenCSV

### Postman

1. Can be tested with the example postmans in the root folder: .\Outsera - Pedro.postman_collection.json
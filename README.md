# dionysus
Dionysus is a project for consume third party services, process and make the data available.


[![Build Status](https://travis-ci.org/aliniribeiroo/dionysus.svg?branch=master)](https://travis-ci.org/aliniribeiroo/dionysus)

## Técnologias utilizadas

### Backend
* [Java](https://java.com/en/download/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [H2 batabase](http://www.h2database.com/html/main.html)
* [Flyway](https://flywaydb.org/)
* [PostgreSQL](https://www.postgresql.org/download/)
* [Maven](https://maven.apache.org/)

## Executando a aplicação

1. Como pré-requisito, possuir [docker](https://www.docker.com/).
2. Baixar o arquivo docker-compose.yml deste repositório e executar o comando: docker-compose up.

> Será baixada a imagem do banco de dados e da aplicação, onde a mesma ficará disponível na porta 8080.
> Importante: A imagem do banco de dados que o docker irá iniciar utiliza a porta 5432, fica imprescindível ter esta porta disponível ao iniciar a orquestragem dos containers.

> Para rodar a aplicação localmente, há a necessidade de passar as inforações da base de dados, conforme exemplo:
```
-DDB_HOST=jdbc:postgresql://localhost:5432/bates
-DDB_USER=postgres
-DDB_PASSWORD=postgres
```


### API's disponíveis
* [Wiki do projeto MockServices](https://github.com/aliniribeiroo/dionysus/wiki/Mock-Service-APIs)
* [Wiki do projeto Dionysus](https://github.com/aliniribeiroo/dionysus/wiki/Dionysus-API%60s)

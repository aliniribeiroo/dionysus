# Dionysus
Dionysus é uma aplicação que faz integração com  serviços de terceiros, buscando, processando, armazenando dados e disponibilizando os mesmos para serem consumidos via API. O objetivo é disponibilizar para o usuário os dados em tempo real, com segurança e assertividade.

Minha proposta é que, a aplicação seja executada e consumida de dentro de uma VPC (Virtual private Cloud) da Amazon,  com sub redes públicas e privadas (NAT). Desta forma mantemos os servidores de banco de dados em uma subnet privada, sem a acesso público.

Fonte: https://docs.aws.amazon.com/pt_br/vpc/latest/userguide/VPC_Scenario2.html

Como precisamos simular um grande volume de dados, foi desenvolvida um mock que retorna  informações simulando a integração com serviços de terceiros apresentados na proposta (Serviço A, B e C).

Para o armazenamento destas informações, foi escolhido o banco de dados Postgres por ser muito indicado em caso de armazenamento de um volume grande de dados.

## Serviços que a aplicação irá disponibilizar
* Dívidas de um CPF;
* Última consulta de algum estabelecimento á um CPF;
* Histórico de movimentações financeiras de um CPF;
* Dados da última compra em um cartão de crédito de um CPF;
* Rendimentos de um CPF;
* Bens de um CPF;
* O cálculo da pontuação de um CPF.

## Regras para a busca das informações nos serviços de terceiros
* No primeiro acesso á alguma API no sistema, o serviço de carga irá ser chamado, trazendo todas as pessoas que possuem dívidas, chamando o Serviço A. Uma Flag é alterada no banco de dados, informando que a primeira carga já foi executada e armazenando o dia;
* Após a carga inicial, a cada 24 horas uma requisição para o Serviço A é realizada para buscar os CPF`s com dívidas das últimas 24 horas;
* A mesma regra se aplicará para o Serviço B, pois estamos levando em consideração que estas informações são menos propensas a serem alteradas com frequência;
* As chamadas ao Serviço C, serão realizadas quando a SPI do Dionysus for chamada, os dados não serão armazenados em seu banco de dados, pois acreditamos que estas informações precisam ser em tempo real e o retorno dos dados deve ser extremamente rápido.

* [As APIS consumidas pela Mock podem ser encontradas aqui.](https://github.com/aliniribeiroo/dionysus/wiki/Mock-Service-APIs)
* [As APIS que o Dionysus disponibiliza podem ser encontradas aqui.](https://github.com/aliniribeiroo/dionysus/wiki/Dionysus-API%60s)

## Técnologias utilizadas

O projeto foi construído em utilizando as seguintes tecnologias:

### Backend
* [Java](https://java.com/en/download/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [H2 batabase](http://www.h2database.com/html/main.html)
* [Flyway](https://flywaydb.org/)
* [PostgreSQL](https://www.postgresql.org/download/)
* [Maven](https://maven.apache.org/)
* [Quartz - Biblioteca de agendamento de tarefas](http://www.quartz-scheduler.org/)

Tanto a mock quanto o dionysus, tiveram suas imagens geradas e disponibilizadas no dockerHub:
* [Imagem do MockServices](https://hub.docker.com/r/aliniribeiroo/dionysus)
* [Imagem do Dionysus](https://hub.docker.com/r/aliniribeiroo/mockservice)


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


## Integração contínua com Travis-ci
[![Build Status](https://travis-ci.org/aliniribeiroo/dionysus.svg?branch=master)](https://travis-ci.org/aliniribeiroo/dionysus)

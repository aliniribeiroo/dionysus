# Dionysus
Dionysus é uma aplicação que faz integração com  serviços de terceiros, buscando, processando, armazenando dados e disponibilizando os mesmos para serem consumidos via API. O objetivo é disponibilizar para o usuário os dados em tempo real, com segurança e assertividade.

Minha proposta é que, a aplicação seja executada em uma VPC (Virtual Private Cloud) com uma sub-rere publica e uma sub-rede privada, onde na sub-rede privada se encontra a base de dados(RDS) e na sub-rede publica fica os serviços do Dionysus em um EC2.
As duas aplicações utilizam o mesmo "grupo de segurança", então a comunicação entre banco de dados e serviço apenas acontece se os mesmos estão dentro do mesmo grupo de segurança.
A unica aplicação que conseguirá ser acessada pela internet é a aplicação da sub-rede publica.
Segue um desenho para tentar exemplificar este cenário:
![dionysus](https://user-images.githubusercontent.com/10133177/57110267-4c626f80-6d0e-11e9-9af7-6d20d083cae2.jpg)


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

## Autenticação

As API`s disponíveis no Dionysus respeitam a autênticação e o papel do ususário que está realizando a solicitação de dados.
Temos uma API específica para os administradores do sistema, para rastrear quais os usuários que buscaram no Dionysus por um CPF específico, fazendo assim transparente a rastreabilidade da busca das informaçoes no sistema [(Documentação desta API no final desta página)](https://github.com/aliniribeiroo/dionysus/wiki/Dionysus-API%60s).

A implementação de autenticação foi realizada com [JWT](https://jwt.io/).

Para realizar os testes das API`s que o dionysus disponibiliza, sera um pré-requisito realizar a autenticação no sisterma e utilizar o tone da API de login  no Header da requisição, de acordo com o exemplo do giff abaixo:
![auth](https://user-images.githubusercontent.com/10133177/57044898-73ec0600-6c42-11e9-880f-fbf0331c7558.gif)

#### Usuários padrões

* user:admin@dionysus.com | password:123456
* user: user@dionysus.com | password:123456


## Técnologias utilizadas

O projeto foi construído em utilizando as seguintes tecnologias:

### Backend
* [Java](https://java.com/en/download/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [H2 batabase](http://www.h2database.com/html/main.html)
* [Flyway](https://flywaydb.org/)
* [PostgreSQL](https://www.postgresql.org/download/)
* [Maven](https://maven.apache.org/)
* [Docker](https://www.docker.com/)
* [Quartz - Biblioteca de agendamento de tarefas](http://www.quartz-scheduler.org/)



Tanto a mock quanto o dionysus, tiveram suas imagens geradas e disponibilizadas no [dockerHub](https://hub.docker.com/u/aliniribeiroo):
* [Imagem do MockServices](https://hub.docker.com/r/aliniribeiroo/dionysus)
* [Imagem do Dionysus](https://hub.docker.com/r/aliniribeiroo/mockservice)


## Executando a aplicação

1. Como pré-requisito, possuir [docker](https://www.docker.com/).
2. Baixar o arquivo docker-compose.yml deste repositório e executar o comando: docker-compose up.

> Será baixada a imagem do banco de dados e da aplicação, onde a mesma ficará disponível na porta 8080.
> Importante: A imagem do banco de dados que o docker irá iniciar utiliza a porta 5432, fica imprescindível ter esta porta disponível ao iniciar a orquestragem dos containers.

> Para rodar a aplicação localmente, há a necessidade de passar as inforações da base de dados, conforme exemplo:
```
-DDB_HOST=jdbc:postgresql://localhost:5432/{nome do banco de dados criado}
-DDB_USER=postgres
-DDB_PASSWORD=postgres
-DMOCK_APP_NAME=localhost
```


### API's disponíveis
* [Wiki do projeto MockServices](https://github.com/aliniribeiroo/dionysus/wiki/Mock-Service-APIs)
* [Wiki do projeto Dionysus](https://github.com/aliniribeiroo/dionysus/wiki/Dionysus-API%60s)


## Integração contínua com Travis-ci

O travis-ci foi configurado para realizar o buld do projeto e validar os testes automatozados.
Hoje temos contruídos no Dionysus 25 testes validando os principais cenários.

[![Build Status](https://travis-ci.org/aliniribeiroo/dionysus.svg?branch=master)](https://travis-ci.org/aliniribeiroo/dionysus)

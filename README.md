# Gestão de Reservas de Salas

## Desenvolvimento

Para iniciar a aplicação no perfil de "dev", simplesmente execute:

    ./mvnw

## Empacotando para Produção

Para otimizar e empacotar a aplicação de reserva para produção, execute:

    ./mvnw -Pprod clean package

Para iniciar a aplicação no perfil de "prod", simplesmente execute:

    java -jar -Dspring.profiles.active=prod ./target/reserva-0.0.1-SNAPSHOT.war

    
## Teste

Para executar os testes da aplicação, execute:

    ./mvnw clean test

O relatório contendo o resultado do % de cobertura de testes encontra-se na pasta:

    ./target/test-results/coverage/jacoco/index.html

## Endpoints da Aplicação:
### API Agendamento
GET
* [http://localhost:8081/api/agendamento]: *Retorna todos os agendamentos realizados.*<br/>

* [http://localhost:8081/api/agendamento?sala.id=number]: *Retorna todos os agendamentos relacionados a uma sala pelo Id da mesma.*<br/>
    Ex. http://localhost:8081/api/agendamento?sala.id=1

* [http://localhost:8081/api/agendamento?sala.nome=string]: *[Pesquisa "IgnoreCase" ] *Retorna todos os agendamentos relacionados a uma sala pelo nome da mesma.*<br/>
    Ex. http://localhost:8081/api/agendamento?sala.nome=Sala Minas Gerais
    
* [http://localhost:8081/api/agendamento?dataInicio=date&dataFim=date]: *Retorna todos os agendamentos contidos dentro de um período.*<br/>
    Ex. http://localhost:8081/api/agendamento?dataInicio=2015-01-26T08:00:00&dataFim=2015-07-29T09:20:00
    

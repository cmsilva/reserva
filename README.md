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

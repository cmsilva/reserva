# Gestão de Reservas de Salas

## Desenvolvimento
Pré-Requisitos:
<p>
Java 8 (JDK)
<br/>
Maven
</p>

Para iniciar a aplicação no perfil de "dev", execute:

    ./mvnw

## Empacotando para Produção

Para empacotar a aplicação de reserva para produção, execute:

    ./mvnw -Pprod clean package

Para iniciar a aplicação no perfil de "prod", execute:

    java -jar -Dspring.profiles.active=prod ./target/reserva-0.0.1-SNAPSHOT.war

    
## Teste

Para executar os testes da aplicação, execute:

    ./mvnw clean test

O relatório contendo o resultado do % de cobertura de testes encontra-se na pasta:

    ${project.base.dir}/target/test-results/coverage/jacoco/index.html

## Endpoints da Aplicação:
### API SALA
###GET

* [http://localhost:8081/api/sala] <br/>Retorna todas as salas.<br/>

* [http://localhost:8081/api/sala/{id}] <br/>Retorna uma sala por id.<br/>
    Ex. http://localhost:8081/api/sala/1

###POST

* [http://localhost:8081/api/sala] <br/>Cria uma sala.<br/>
<pre>
<code>
{
	"nome" : "Sala São Paulo"
}
</code>
</pre>
###PUT
* [http://localhost:8081/api/sala]<br/>Altera uma sala.<br/>
<pre>
<code>
{
	"id" : "1",
	"nome" : "Sala Minas Gerais"
}
</code>
</pre>
###DELETE
* [http://localhost:8081/api/sala/{id}]<br/>Remove uma sala pelo id.


### API AGENDAMENTO
###GET

* [http://localhost:8081/api/agendamento] <br/>Retorna todos os agendamentos.<br/>

* [http://localhost:8081/api/agendamento/{id}] <br/>Retorna um agendamento por id.<br/>
    Ex. http://localhost:8081/api/agendamento/1

* [http://localhost:8081/api/agendamento?sala.id=number]<br/> Retorna todos os agendamentos relacionados a uma sala pelo id da mesma.<br/>
    Ex. http://localhost:8081/api/agendamento?sala.id=1

* [http://localhost:8081/api/agendamento?sala.nome=string]<br/> Retorna todos os agendamentos relacionados a uma sala pelo nome da mesma. (Pesquisa ignora Case)<br/>
    Ex. http://localhost:8081/api/agendamento?sala.nome=Sala Minas Gerais
        
* [http://localhost:8081/api/agendamento?dataInicio=date&dataFim=date]<br/> Retorna todos os agendamentos contidos dentro de um período.<br/>
    Ex. http://localhost:8081/api/agendamento?dataInicio=2018-01-26T08:00:00&dataFim=2018-07-29T09:20:00
    
###POST

* [http://localhost:8081/api/agendamento]<br/> Cria um agendamento.<br/>
<pre>
<code>
{
	"titulo" : "Reunião sobre Fretamento",
	"dataInicio": "2015-03-25T09:30:00",
	"dataFim": "2015-03-29T07:30:00",
	"sala": {
		"id": "1"
	}
}
</code>
</pre>
###PUT
* [http://localhost:8081/api/agendamento]<br/> Altera um agendamento.<br/>
<pre>
<code>
{	
	"id": "53",
	"titulo" : "Reunião sobre Financeiro",
	"dataInicio": "2015-02-27T09:30:00",
	"dataFim": "2015-02-28T07:30:00",
	"sala": {
		"id": "2"
	}
}
</code>
</pre>
###DELETE
* [http://localhost:8081/api/agendamento/{id}]<br/> Remove um agendamento pelo id.




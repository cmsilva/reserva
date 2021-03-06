package br.com.magluiza.reserva.web.rest;

import br.com.magluiza.reserva.core.Constants;
import br.com.magluiza.reserva.core.MessageConstants;
import br.com.magluiza.reserva.core.exception.CustomParameterizedException;
import br.com.magluiza.reserva.domain.Agendamento;
import br.com.magluiza.reserva.service.AgendamentoService;
import br.com.magluiza.reserva.web.rest.dto.AgendamentoDto;
import br.com.magluiza.reserva.web.rest.dto.AgendamentosDto;
import br.com.magluiza.reserva.web.rest.util.helper.AgendamentoHelper;
import br.com.magluiza.reserva.web.rest.util.helper.SalaHelper;
import br.com.magluiza.reserva.web.rest.util.mapper.AgendamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/agendamento")
public class AgendamentoResource {

    private static final Logger log = LoggerFactory.getLogger(AgendamentoResource.class);
    private AgendamentoService service;

    public AgendamentoResource(AgendamentoService service) {
        this.service = service;
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pesquisarTudo() {
        log.info("Ação Pesquisar todos Agendamentos");

        List<AgendamentoDto> agendamentos = AgendamentoHelper.transformarParaDto(service.pesquisarTudo());
        log.debug("Quantidade de Agendamentos: {}", agendamentos.size());
        return new ResponseEntity<>(new AgendamentosDto(agendamentos), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pesquisarPorId(@PathVariable Long id) {
        log.info("Ação Pesquisar Agendamento por Id");
        log.debug("Pesquisando o Agendamento de Id: {}", id);

        Agendamento agendamento = service.pesquisarPorId(id);
        return new ResponseEntity<>(AgendamentoMapper.INSTANCE.sourceToDestination(agendamento), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"dataInicio", "dataFim"})
    public ResponseEntity<?> pesquisarPorPeriodo(@RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio, @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        log.info("Ação Pesquisar Agendamento por período");

        List<AgendamentoDto> agendamentos = AgendamentoHelper.transformarParaDto(service.pesquisarPorPeriodo(inicio, fim));
        log.debug("Quantidade de Agendamentos: {}", agendamentos.size());
        return new ResponseEntity<>(new AgendamentosDto(agendamentos), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "sala.nome")
    public ResponseEntity<?> pesquisarPorNomeSala(@RequestParam("sala.nome") String nome) {
        log.info("Ação Pesquisar Agendamento por nome da Sala");

        List<AgendamentoDto> agendamentos = AgendamentoHelper.transformarParaDto(service.pesquisarPorNomeSala(nome));
        log.debug("Quantidade de Agendamentos: {}", agendamentos.size());
        return new ResponseEntity<>(new AgendamentosDto(agendamentos), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "sala.id")
    public ResponseEntity<?> pesquisarPorIdSala(@RequestParam("sala.id") Long idSala) {
        log.info("Ação Pesquisar Agendamento por id Sala");

        List<AgendamentoDto> agendamentos = AgendamentoHelper.transformarParaDto(service.pesquisarPorIdSala(idSala));
        log.debug("Quantidade de Agendamentos: {}", agendamentos.size());
        return new ResponseEntity<>(new AgendamentosDto(agendamentos), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> criar(@Validated @RequestBody AgendamentoDto agendamento) {
        log.info("Ação Criar Agendamento");

        if (SalaHelper.salaNaoEstaPreenchida(agendamento.getSala())) {
            throw new CustomParameterizedException(MessageConstants.ERR_FIELD_REQUIRED, Constants.FIELD_AGENDAMENTO_SALA_ID);
        }

        log.debug("Criando Agendamento,  Título: {}, Id Sala: {}", agendamento.getTitulo(), agendamento.getSala().getId());
        Agendamento newAgendamento = service.criar(AgendamentoMapper.INSTANCE.destinationToSource(agendamento));
        return new ResponseEntity<>(AgendamentoMapper.INSTANCE.sourceToDestination(newAgendamento), HttpStatus.CREATED);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> atualizar(@RequestBody AgendamentoDto agendamento) {
        log.info("Ação Atualizar Agendamento");

        if (AgendamentoHelper.agendamentoNaoEstaPreenchido(agendamento)) {
            throw new CustomParameterizedException(MessageConstants.ERR_FIELD_REQUIRED, Constants.FIELD_AGENDAMENTO_ID);
        }
        if (SalaHelper.salaNaoEstaPreenchida(agendamento.getSala())) {
            throw new CustomParameterizedException(MessageConstants.ERR_FIELD_REQUIRED, Constants.FIELD_AGENDAMENTO_SALA_ID);
        }
        service.atualizar(AgendamentoMapper.INSTANCE.destinationToSource(agendamento));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        log.info("Ação Remover Agendamento por Id");
        log.debug("Removendo o Agendamento de Id: {}", id);

        service.remover(id);
    }
}

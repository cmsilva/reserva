package br.com.magluiza.reserva.web.rest;

import br.com.magluiza.reserva.core.Constants;
import br.com.magluiza.reserva.core.MessageConstants;
import br.com.magluiza.reserva.core.exception.CustomParameterizedException;
import br.com.magluiza.reserva.domain.Agendamento;
import br.com.magluiza.reserva.service.AgendamentoService;
import br.com.magluiza.reserva.web.rest.dto.AgendamentoDto;
import br.com.magluiza.reserva.web.rest.dto.AgendamentosDto;
import br.com.magluiza.reserva.web.rest.util.mapper.AgendamentoMapper;
import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/agendamento")
public class AgendamentoResource {

    private static final Logger log = LoggerFactory.getLogger(AgendamentoResource.class);
    private AgendamentoService service;

    public AgendamentoResource(AgendamentoService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        log.debug("Recuperando o Agendamento de Id: {}", id);

        Agendamento agendamento = service.recuperarPorId(id);
        return new ResponseEntity<>(agendamento, HttpStatus.OK);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        List<AgendamentoDto> agendamentos = toDtoList(service.recuperarTudo());
        log.debug("Quantidade de Agendamentos: {}", agendamentos.size());
        return new ResponseEntity<>(new AgendamentosDto(agendamentos), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "data.dia")
    public ResponseEntity<?> findAllNoDia(
            @And({@Spec(path = "dataInicio", params = {"data.dia"}, config = "yyyy-MM-dd", spec = DateBefore.class),
                    @Spec(path = "dataFim", params = {"data.dia"}, config = "yyyy-MM-dd", spec = DateAfter.class)}) Specification specification) {
        List<AgendamentoDto> agendamentos = toDtoList(service.recuperarTudo(specification));
        log.debug("Quantidade de Agendamentos: {}", agendamentos.size());
        return new ResponseEntity<>(new AgendamentosDto(agendamentos), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "sala.nome")
    public ResponseEntity<?> findAllNomeDaSala(@Spec(path = "sala.nome", params = "sala.nome", spec = LikeIgnoreCase.class) Specification specification) {
        List<AgendamentoDto> agendamentos = toDtoList(service.recuperarTudo(specification));
        log.debug("Quantidade de Agendamentos: {}", agendamentos.size());
        return new ResponseEntity<>(new AgendamentosDto(agendamentos), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllIdSala(@Spec(path = "sala.id", params = "sala.id", spec = Equal.class) Specification specification) {
        List<AgendamentoDto> agendamentos = toDtoList(service.recuperarTudo(specification));
        log.debug("Quantidade de Agendamentos: {}", agendamentos.size());
        return new ResponseEntity<>(new AgendamentosDto(agendamentos), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Validated @RequestBody AgendamentoDto agendamento) {
        log.debug("Criando Agendamento,  Título: {}", agendamento.getTitulo());

        if (estaVaziaSala(agendamento)) {
            throw new CustomParameterizedException(MessageConstants.ERR_FIELD_REQUIRED, Constants.FIELD_AGENDAMENTO_SALA_ID);
        }
        Agendamento newAgendamento = service.criar(AgendamentoMapper.INSTANCE.destinationToSource(agendamento));
        return new ResponseEntity<>(AgendamentoMapper.INSTANCE.sourceToDestination(newAgendamento), HttpStatus.CREATED);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody AgendamentoDto agendamento) {
        if (Objects.isNull(agendamento.getId())) {
            throw new CustomParameterizedException(MessageConstants.ERR_FIELD_REQUIRED, Constants.FIELD_AGENDAMENTO_ID);
        }
        if (estaVaziaSala(agendamento)) {
            throw new CustomParameterizedException(MessageConstants.ERR_FIELD_REQUIRED, Constants.FIELD_AGENDAMENTO_SALA_ID);
        }

        service.atualizar(AgendamentoMapper.INSTANCE.destinationToSource(agendamento));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.debug("Removendo a Agendamento de Id: {}", id);
        service.remover(id);
    }

    private boolean estaVaziaSala(@RequestBody @Validated AgendamentoDto agendamento) {
        return (Objects.isNull(agendamento.getSala()) || Objects.isNull(agendamento.getSala().getId()));
    }

    private List<AgendamentoDto> toDtoList(List<Agendamento> agendamentos) {
        return agendamentos.stream()
                .map(agendamento -> AgendamentoMapper.INSTANCE.sourceToDestination(agendamento))
                .collect(Collectors.toList());
    }
}

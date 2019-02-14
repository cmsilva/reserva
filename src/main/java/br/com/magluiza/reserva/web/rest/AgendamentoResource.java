package br.com.magluiza.reserva.web.rest;

import br.com.magluiza.reserva.core.exception.CustomParameterizedException;
import br.com.magluiza.reserva.domain.Agendamento;
import br.com.magluiza.reserva.service.AgendamentoService;
import br.com.magluiza.reserva.web.rest.dto.AgendamentoDto;
import br.com.magluiza.reserva.web.rest.dto.AgendamentosDto;
import br.com.magluiza.reserva.web.rest.util.mapper.AgendamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        log.debug("Recuperando o agendamento de id: {}", id);

        Agendamento agendamento = service.recuperarPorId(id);
        return new ResponseEntity<>(agendamento, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {

        List<AgendamentoDto> agendamentos = service.recuperarTudo()
                .stream()
                .map(agendamento -> {
                    AgendamentoDto dto = AgendamentoMapper.INSTANCE.sourceToDestination(agendamento);
                    return dto;
                })
                .collect(Collectors.toList());
        log.debug("Quantidade de agendamentos recuperadas: {}", agendamentos.size());
        return new ResponseEntity<>(new AgendamentosDto(agendamentos), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Validated @RequestBody AgendamentoDto agendamentoToCreate) {
        log.debug("Criando agendamento de título: {}", agendamentoToCreate.getTitulo());

        if (checkSalaNotExists(agendamentoToCreate)) {
            throw new CustomParameterizedException("error.field.required", "agendamento.sala.id");
        }
        Agendamento newAgendamento = service.criar(AgendamentoMapper.INSTANCE.destinationToSource(agendamentoToCreate));
        return new ResponseEntity<>(AgendamentoMapper.INSTANCE.sourceToDestination(newAgendamento), HttpStatus.CREATED);
    }


    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody AgendamentoDto body) {

        if(Objects.isNull(body.getId())) {
            throw new CustomParameterizedException("error.field.required", "agendamento.id");
        }

        if (checkSalaNotExists(body)) {
            throw new CustomParameterizedException("error.field.required", "agendamento.sala.id");
        }

        service.atualizar(AgendamentoMapper.INSTANCE.destinationToSource(body));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.debug("Removendo a agendamento de id: {}", id);
        service.remover(id);
    }

    private boolean checkSalaNotExists(@RequestBody @Validated AgendamentoDto agendamento) {
        return (Objects.isNull(agendamento.getSala()) || Objects.isNull(agendamento.getSala().getId()));
    }
}

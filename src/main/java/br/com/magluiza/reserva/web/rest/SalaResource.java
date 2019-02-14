package br.com.magluiza.reserva.web.rest;

import br.com.magluiza.reserva.core.exception.CustomParameterizedException;
import br.com.magluiza.reserva.domain.Sala;
import br.com.magluiza.reserva.service.SalaService;
import br.com.magluiza.reserva.web.rest.dto.SalaDto;
import br.com.magluiza.reserva.web.rest.dto.SalasDto;
import br.com.magluiza.reserva.web.rest.util.mapper.SalaMapper;
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
@RequestMapping("/api/sala")
public class SalaResource {

    private static final Logger log = LoggerFactory.getLogger(SalaResource.class);
    private SalaService service;

    public SalaResource(SalaService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        log.debug("Recuperando a sala de id: {}", id);

        Sala sala = service.recuperarPorId(id);
        return new ResponseEntity<>(SalaMapper.INSTANCE.sourceToDestination(sala), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {

        List<SalaDto> salas = service.recuperarTudo()
                .stream()
                .map(sala -> {
                    SalaDto dto = SalaMapper.INSTANCE.sourceToDestination(sala);
                    return dto;
                })
                .collect(Collectors.toList());
        log.debug("Quantidade de salas recuperadas: {}", salas.size());
        return new ResponseEntity<>(new SalasDto(salas), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Validated @RequestBody SalaDto body) {
        log.debug("Criando sala de nome: {}", body.getNome());

        Sala newSala = service.criar(SalaMapper.INSTANCE.destinationToSource(body));
        return new ResponseEntity<>(SalaMapper.INSTANCE.sourceToDestination(newSala), HttpStatus.CREATED);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody SalaDto body) {

        if (Objects.isNull(body.getId())) {
            throw new CustomParameterizedException("error.field.required", "id");
        }
        service.atualizar(SalaMapper.INSTANCE.destinationToSource(body));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.debug("Removendo a sala de id: {}", id);
        service.remover(id);
    }
}

package br.com.magluiza.reserva.web.rest;

import br.com.magluiza.reserva.core.Constants;
import br.com.magluiza.reserva.core.MessageConstants;
import br.com.magluiza.reserva.core.exception.CustomParameterizedException;
import br.com.magluiza.reserva.domain.Sala;
import br.com.magluiza.reserva.service.SalaService;
import br.com.magluiza.reserva.web.rest.dto.SalaDto;
import br.com.magluiza.reserva.web.rest.dto.SalasDto;
import br.com.magluiza.reserva.web.rest.util.helper.SalaHelper;
import br.com.magluiza.reserva.web.rest.util.mapper.SalaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sala")
public class SalaResource {

    private static final Logger log = LoggerFactory.getLogger(SalaResource.class);
    private SalaService service;

    public SalaResource(SalaService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pesquisarPorId(@PathVariable Long id) {
        log.info("Ação Pesquisar por Id");
        log.debug("Pesquisando a sala de Id: {}", id);

        Sala sala = service.pesquisarPorId(id);
        return new ResponseEntity<>(SalaMapper.INSTANCE.sourceToDestination(sala), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pesquisarTudo() {
        log.info("Ação Pesquisar tudo");

        List<SalaDto> salas = SalaHelper.transformarParaDto(service.pesquisarTudo());
        log.debug("Quantidade de salas: {}", salas.size());
        return new ResponseEntity<>(new SalasDto(salas), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> criar(@Validated @RequestBody SalaDto body) {
        log.info("Ação Criar");
        log.debug("Criando Sala de nome: {}", body.getNome());

        Sala sala = service.criar(SalaMapper.INSTANCE.destinationToSource(body));
        return new ResponseEntity<>(SalaMapper.INSTANCE.sourceToDestination(sala), HttpStatus.CREATED);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> atualizar(@RequestBody SalaDto sala) {
        log.info("Ação Atualizar");
        log.debug("Atualizando Sala Id: {}", sala.getId());

        if (SalaHelper.salaNaoEstaPreenchida(sala)) {
            throw new CustomParameterizedException(MessageConstants.ERR_FIELD_REQUIRED, Constants.FIELD_SALA_ID);
        }
        service.atualizar(SalaMapper.INSTANCE.destinationToSource(sala));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        log.info("Ação remover");
        log.debug("Removendo a Sala de id: {}", id);

        service.remover(id);
    }
}

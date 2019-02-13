package br.com.magluiza.reserva.web.rest;

import br.com.magluiza.reserva.domain.Sala;
import br.com.magluiza.reserva.service.SalaService;
import br.com.magluiza.reserva.web.rest.dto.SalaDto;
import br.com.magluiza.reserva.web.rest.dto.SalasDto;
import br.com.magluiza.reserva.web.rest.mapper.SalaMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sala")
public class SalaResource {

    private SalaService service;

    public SalaResource(SalaService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getSala(@PathVariable Long id) {
        Optional<Sala> sala = service.findById(id);
        return new ResponseEntity<>(sala.get(), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getSalas() {
        List<SalaDto> salas = service.findAll()
                .stream()
                .map(sala -> {
                    SalaDto dto = SalaMapper.INSTANCE.sourceToDestination(sala);
                    return dto;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(new SalasDto(salas), HttpStatus.OK);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> newSala(@Validated @RequestBody SalaDto body) {
        Sala newSala = service.create(body.getNome());
        return new ResponseEntity<>(SalaMapper.INSTANCE.sourceToDestination(newSala), HttpStatus.OK);
    }

    @PutMapping(name = "/{id}", produces = "application/json")
    public ResponseEntity<?> updateSala(
            @PathVariable String id,
            @RequestParam("name") String name) {
        return new ResponseEntity<>(new SalaDto(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteSala(@PathVariable Long id) {
        service.delete(id);
    }
}

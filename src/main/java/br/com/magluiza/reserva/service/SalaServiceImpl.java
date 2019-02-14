package br.com.magluiza.reserva.service;

import br.com.magluiza.reserva.core.exception.SalaExistsException;
import br.com.magluiza.reserva.core.exception.SalaNotFoundException;
import br.com.magluiza.reserva.domain.Sala;
import br.com.magluiza.reserva.repository.SalaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaServiceImpl implements SalaService {

    private static final Logger log = LoggerFactory.getLogger(SalaServiceImpl.class);
    private final SalaRepository repository;

    public SalaServiceImpl(SalaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Sala create(String nome) {
        Optional<Sala> sala = repository.findByNomeIgnoreCase(nome);
        if (sala.isPresent()) {
            log.error("Criando uma sala que já existe, nome: {}", nome);
            throw new SalaExistsException(nome, "error.sala.exists");
        }
        Sala newSala = repository.save(new Sala(nome));
        return newSala;
    }

    @Override
    public List<Sala> findAll() {
        return repository.findAll();
    }

    @Override
    public Sala findById(Long id) {
        Optional<Sala> sala = repository.findById(id);
        if (sala.isPresent()) {
            return sala.get();
        }
        log.error("Buscando por uma sala que não existe, id: {}", id);
        throw new SalaNotFoundException(id, "error.sala.notfound");
    }

    @Override
    public void delete(Long id) {
        Optional<Sala> sala = repository.findById(id);
        if (sala.isPresent()) {
            repository.delete(sala.get());
        } else {
            log.error("Removendo uma sala que não existe, id: {}", id);
            throw new SalaNotFoundException(id, "error.sala.notfound");
        }
    }

    @Override
    public Sala update(Sala sala) {
        Long id = sala.getId();
        Optional<Sala> salaDb = repository.findById(id);
        if (salaDb.isPresent()) {
            Sala salaToUpdate = salaDb.get();
            salaToUpdate.setNome(sala.getNome());
            return repository.save(salaToUpdate);
        }
        log.error("Atualizando uma sala que não existe, id: {}", id);
        throw new SalaNotFoundException(id, "error.sala.notfound");
    }
}

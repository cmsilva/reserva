package br.com.magluiza.reserva.service;

import br.com.magluiza.reserva.domain.Sala;
import br.com.magluiza.reserva.repository.SalaRepository;
import br.com.magluiza.reserva.web.rest.errors.CustomParameterizedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaServiceImpl implements SalaService {

    private SalaRepository repository;

    public SalaServiceImpl(SalaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Sala create(String nome) {
        Sala newSala = repository.save(new Sala(nome));
        return newSala;
    }

    @Override
    public List<Sala> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Sala> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id) {
        Optional<Sala> sala = repository.findById(id);
        if (sala.isPresent()) {
            repository.delete(sala.get());
        } else {
            throw new CustomParameterizedException("error.SalaNotFound");
        }
    }
}

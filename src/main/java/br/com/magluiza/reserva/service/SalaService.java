package br.com.magluiza.reserva.service;

import br.com.magluiza.reserva.domain.Sala;

import java.util.List;
import java.util.Optional;

public interface SalaService {
    Sala create(String nome);

    List<Sala> findAll();

    Optional<Sala> findById(Long id);

    void delete(Long id);
}

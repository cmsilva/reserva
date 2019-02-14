package br.com.magluiza.reserva.service;

import br.com.magluiza.reserva.domain.Sala;

import java.util.List;

public interface SalaService {
    Sala create(String nome);

    List<Sala> findAll();

    Sala findById(Long id);

    void delete(Long id);

    Sala update(Sala sala);

}

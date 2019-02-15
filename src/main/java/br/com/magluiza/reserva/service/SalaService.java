package br.com.magluiza.reserva.service;

import br.com.magluiza.reserva.domain.Sala;

import java.util.List;

public interface SalaService {
    Sala criar(Sala sala);

    List<Sala> pesquisarTudo();

    Sala recuperarPorId(Long id);

    void remover(Long id);

    Sala atualizar(Sala sala);
}

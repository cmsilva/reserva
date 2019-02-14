package br.com.magluiza.reserva.service;

import br.com.magluiza.reserva.domain.Agendamento;

import java.util.List;

public interface AgendamentoService {
    Agendamento criar(Agendamento agendamento);

    List<Agendamento> recuperarTudo();

    Agendamento recuperarPorId(Long id);

    void remover(Long id);

    Agendamento atualizar(Agendamento sala);
}

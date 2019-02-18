package br.com.magluiza.reserva.service;

import br.com.magluiza.reserva.domain.Agendamento;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoService {
    Agendamento criar(Agendamento agendamento);

    List<Agendamento> pesquisarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim);

    Agendamento pesquisarPorId(Long id);

    void remover(Long id);

    Agendamento atualizar(Agendamento sala);

    List<Agendamento> pesquisarTudo();

    List<Agendamento> pesquisarPorNomeSala(String nome);

    List<Agendamento> pesquisarPorIdSala(Long idSala);
}

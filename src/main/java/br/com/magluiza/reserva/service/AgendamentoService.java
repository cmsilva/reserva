package br.com.magluiza.reserva.service;

import br.com.magluiza.reserva.domain.Agendamento;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AgendamentoService {
    Agendamento criar(Agendamento agendamento);

    List<Agendamento> recuperarTudo(Specification<Agendamento> specification);

    Agendamento recuperarPorId(Long id);

    void remover(Long id);

    Agendamento atualizar(Agendamento sala);

    List<Agendamento> recuperarTudo();
}

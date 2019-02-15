package br.com.magluiza.reserva.web.rest.util.helper;

import br.com.magluiza.reserva.domain.Agendamento;
import br.com.magluiza.reserva.web.rest.dto.AgendamentoDto;
import br.com.magluiza.reserva.web.rest.util.mapper.AgendamentoMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class AgendamentoHelper {
    private AgendamentoHelper() {
        super();
    }

    public static boolean agendamentoNaoEstaPreenchido(AgendamentoDto agendamento) {
        return (Objects.isNull(agendamento) || Objects.isNull(agendamento.getId()));
    }

    public static List<AgendamentoDto> transformarParaDto(List<Agendamento> agendamentos) {
        return agendamentos.stream()
                .map(agendamento -> AgendamentoMapper.INSTANCE.sourceToDestination(agendamento))
                .collect(Collectors.toList());
    }
}

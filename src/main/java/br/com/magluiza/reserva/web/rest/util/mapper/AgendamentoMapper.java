package br.com.magluiza.reserva.web.rest.util.mapper;

import br.com.magluiza.reserva.domain.Agendamento;
import br.com.magluiza.reserva.web.rest.dto.AgendamentoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AgendamentoMapper {
    AgendamentoMapper INSTANCE = Mappers.getMapper(AgendamentoMapper.class);

    AgendamentoDto sourceToDestination(Agendamento agendamento);

    Agendamento destinationToSource(AgendamentoDto agendamento);
}

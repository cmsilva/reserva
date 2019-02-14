package br.com.magluiza.reserva.web.rest.mapper;

import br.com.magluiza.reserva.domain.Sala;
import br.com.magluiza.reserva.web.rest.dto.SalaDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SalaMapper {
    SalaMapper INSTANCE = Mappers.getMapper(SalaMapper.class);

    SalaDto sourceToDestination(Sala sala);

    Sala destinationToSource(SalaDto sala);
}

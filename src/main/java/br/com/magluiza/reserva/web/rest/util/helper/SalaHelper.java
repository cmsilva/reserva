package br.com.magluiza.reserva.web.rest.util.helper;

import br.com.magluiza.reserva.domain.Sala;
import br.com.magluiza.reserva.web.rest.dto.SalaDto;
import br.com.magluiza.reserva.web.rest.util.mapper.SalaMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class SalaHelper {
    private SalaHelper() {
        super();
    }

    public static boolean salaNaoEstaPreenchida(SalaDto sala) {
        return (Objects.isNull(sala) || Objects.isNull(sala.getId()));
    }

    public static List<SalaDto> transformarParaDto(List<Sala> salas) {
        return salas.stream()
                .map(sala -> SalaMapper.INSTANCE.sourceToDestination(sala))
                .collect(Collectors.toList());
    }
}

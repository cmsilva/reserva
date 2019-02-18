package br.com.magluiza.reserva.web.rest.util.mapper;

import br.com.magluiza.reserva.domain.Agendamento;
import br.com.magluiza.reserva.domain.Sala;
import br.com.magluiza.reserva.web.rest.dto.AgendamentoDto;
import br.com.magluiza.reserva.web.rest.dto.SalaDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AgendamentoMapperImplTest {

    @Test
    public void sourceToDestination_when_AgendamentoNull() {
        AgendamentoDto agendamentoDto = new AgendamentoMapperImpl().sourceToDestination(null);
        Assert.assertNull(agendamentoDto);
    }

    @Test
    public void destinationToSource_when_AgendamentoNull() {
        Agendamento agendamento = new AgendamentoMapperImpl().destinationToSource(null);
        Assert.assertNull(agendamento);
    }

    @Test
    public void destinationToSource_when_SalaNull() {
        Sala sala = new AgendamentoMapperImpl().salaDtoToSala(null);
        Assert.assertNull(sala);
    }

    @Test
    public void sourceToDestination_when_SalaNull() {
        SalaDto salaDto = new AgendamentoMapperImpl().salaToSalaDto(null);
        Assert.assertNull(salaDto);
    }
}


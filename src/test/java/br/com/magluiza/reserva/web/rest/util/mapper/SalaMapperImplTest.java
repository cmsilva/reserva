package br.com.magluiza.reserva.web.rest.util.mapper;

import br.com.magluiza.reserva.domain.Sala;
import br.com.magluiza.reserva.web.rest.dto.SalaDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SalaMapperImplTest {

    @Test
    public void destinationToSource_when_SalaNull() {
        Sala sala = new SalaMapperImpl().destinationToSource(null);
        Assert.assertNull(sala);
    }

    @Test
    public void sourceToDestination_when_SalaNull() {
        SalaDto salaDto = new SalaMapperImpl().sourceToDestination(null);
        Assert.assertNull(salaDto);
    }
}


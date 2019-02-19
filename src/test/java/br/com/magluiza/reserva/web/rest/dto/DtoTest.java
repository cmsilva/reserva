package br.com.magluiza.reserva.web.rest.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.pojo.tester.api.assertion.Assertions;

@RunWith(SpringRunner.class)
public class DtoTest {

    @Test
    public void testAllDtos() {
        Assertions.assertPojoMethodsFor(SalaDto.class).areWellImplemented();
        Assertions.assertPojoMethodsFor(SalasDto.class).areWellImplemented();

        Assertions.assertPojoMethodsFor(AgendamentoDto.class).areWellImplemented();
        Assertions.assertPojoMethodsFor(AgendamentosDto.class).areWellImplemented();
    }
}

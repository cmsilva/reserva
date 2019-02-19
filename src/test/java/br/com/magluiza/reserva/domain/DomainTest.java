package br.com.magluiza.reserva.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.pojo.tester.api.assertion.Assertions;

@RunWith(SpringRunner.class)
public class DomainTest {

    @Test
    public void testAllDomains() {
        Assertions.assertPojoMethodsFor(Sala.class).areWellImplemented();
        Assertions.assertPojoMethodsFor(Agendamento.class).areWellImplemented();
    }
}

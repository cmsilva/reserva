package br.com.magluiza.reserva.core.exception;

import br.com.magluiza.reserva.ReservaApp;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservaApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NegocioExceptionTest {

    @MockBean
    private MessageSource messageSource;

    @Test
    public void test() {
        when(messageSource.getMessage(any(), any(), any())).thenReturn("abcd");
        NegocioException negocioException = new NegocioException("chave.abcd", messageSource);
        Assert.assertEquals(negocioException.getChave(), "chave.abcd");
        Assert.assertEquals(negocioException.getMessage(), "abcd");
    }
}

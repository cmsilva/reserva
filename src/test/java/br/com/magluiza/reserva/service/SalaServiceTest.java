package br.com.magluiza.reserva.service;

import br.com.magluiza.reserva.core.MessageConstants;
import br.com.magluiza.reserva.core.exception.NegocioException;
import br.com.magluiza.reserva.domain.Sala;
import br.com.magluiza.reserva.repository.SalaRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class SalaServiceTest {
    @MockBean
    MessageSource messageSource;
    @MockBean
    SalaRepository repository;

    @Test
    public void pesquisarTudo() {
        BDDMockito.given(repository.findAll()).willReturn(Helper.createList(Helper.criaSala(1L, "Sl1")));
        SalaService salaService = new SalaServiceImpl(messageSource, repository);
        List<Sala> salas = salaService.pesquisarTudo();

        Assert.assertEquals(1, salas.size());
        Assert.assertEquals(1L, (long) salas.get(0).getId());
        Assert.assertEquals("Sl1", (salas.get(0).getNome()));
    }

    @Test
    public void pesquisarPorId() {
        BDDMockito.given(repository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(Helper.criaSala(1L, "Sl1")));
        SalaService salaService = new SalaServiceImpl(messageSource, repository);
        Sala sala = salaService.pesquisarPorId(1L);

        Assert.assertEquals(1L, (long) sala.getId());
        Assert.assertEquals("Sl1", (sala.getNome()));
    }

    @Test
    public void pesquisarPorId_when_IdSalaNaoPreenchido() {
        SalaService salaService = new SalaServiceImpl(messageSource, repository);
        try {
            salaService.pesquisarPorId(null);
            Assert.fail();
        } catch (NegocioException e) {
            Assert.assertEquals(MessageConstants.ERR_SALA_NAO_ENCONTRADA, e.getChave());
        }
    }

    @Test
    public void remover() {
        BDDMockito.given(repository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(Helper.criaSala(1L, "Sl1")));
        SalaService salaService = new SalaServiceImpl(messageSource, repository);
        salaService.remover(1L);
    }

    @Test
    public void remover_when_IdSalaNaoPreenchido() {
        try {
            SalaService salaService = new SalaServiceImpl(messageSource, repository);
            salaService.remover(null);
            Assert.fail();
        } catch (NegocioException e) {
            Assert.assertEquals(MessageConstants.ERR_SALA_NAO_ENCONTRADA, e.getChave());
        }
    }

    @Test
    public void criar() {
        Sala salaToCreate = Helper.criaSala(null, "Sl1");

        BDDMockito.given(repository.findByNomeIgnoreCase(ArgumentMatchers.anyString())).willReturn(Optional.empty());
        SalaService salaService = new SalaServiceImpl(messageSource, repository);
        salaService.criar(salaToCreate);
    }

    @Test
    public void criar_when_SalaComMesmoNome() {
        Sala salaToCreate = Helper.criaSala(null, "Sl1");

        BDDMockito.given(repository.findByNomeIgnoreCase(ArgumentMatchers.anyString())).willReturn(Optional.of(Helper.criaSala(2L, "Sl1")));
        SalaService salaService = new SalaServiceImpl(messageSource, repository);
        try {
            salaService.criar(salaToCreate);
            Assert.fail();
        } catch (NegocioException e) {
            Assert.assertEquals(MessageConstants.ERR_SALA_COM_MESMO_NOME, e.getChave());
        }
    }

    @Test
    public void atualizar() {
        Sala salaToUpdate = Helper.criaSala(1L, "Sl2");
        BDDMockito.given(repository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(Helper.criaSala(1L, "Sl1")));
        BDDMockito.given(repository.findByNomeIgnoreCase(ArgumentMatchers.anyString())).willReturn(Optional.empty());
        SalaService salaService = new SalaServiceImpl(messageSource, repository);
        salaService.atualizar(salaToUpdate);
    }

    @Test
    public void atualizar_when_SalaComMesmoNome() {
        Sala salaToUpdate = Helper.criaSala(1L, "Sl1");
        BDDMockito.given(repository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(Helper.criaSala(1L, "Sl3")));
        BDDMockito.given(repository.findByNomeIgnoreCase(ArgumentMatchers.anyString())).willReturn(Optional.of(Helper.criaSala(2L, "Sl1")));
        SalaService salaService = new SalaServiceImpl(messageSource, repository);
        try {
            salaService.atualizar(salaToUpdate);
            Assert.fail();
        } catch (NegocioException e) {
            Assert.assertEquals(MessageConstants.ERR_SALA_COM_MESMO_NOME, e.getChave());
        }
    }

    @Test
    public void atualizar_when_IdSalaNaoPreenchido() {
        Sala salaToUpdate = Helper.criaSala(1L, "Sl2");
        SalaService salaService = new SalaServiceImpl(messageSource, repository);
        try {
            salaService.atualizar(salaToUpdate);
            Assert.fail();
        } catch (NegocioException e) {
            Assert.assertEquals(MessageConstants.ERR_SALA_NAO_ENCONTRADA, e.getChave());
        }
    }

    static class Helper {

        static <E> List<E> createList(E... elementos) {
            return Arrays.asList(elementos);
        }

        static Sala criaSala(Long idSl, String slNm) {
            Sala s1 = new Sala();
            s1.setId(idSl);
            s1.setNome(slNm);
            return s1;
        }
    }
}

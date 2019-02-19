package br.com.magluiza.reserva.service;

import br.com.magluiza.reserva.core.MessageConstants;
import br.com.magluiza.reserva.core.exception.NegocioException;
import br.com.magluiza.reserva.domain.Agendamento;
import br.com.magluiza.reserva.domain.Sala;
import br.com.magluiza.reserva.repository.AgendamentoRepository;
import br.com.magluiza.reserva.repository.SalaRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class AgendamentoServiceTest {
    @MockBean
    MessageSource messageSource;
    @MockBean
    AgendamentoRepository repository;
    @MockBean
    SalaRepository salaRepository;

    @Test
    public void pesquisarTudo() {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, "Sl1");

        BDDMockito.given(repository.findAll()).willReturn(Helper.createList(ag1));
        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);
        List<Agendamento> agendamentos = service.pesquisarTudo();

        Assert.assertEquals(1, agendamentos.size());
        Assert.assertEquals(1L, (long) agendamentos.get(0).getId());
        Assert.assertEquals(datei, agendamentos.get(0).getDataInicio());
        Assert.assertEquals(datef, agendamentos.get(0).getDataFim());
        Assert.assertEquals("Ag1", agendamentos.get(0).getTitulo());
        Assert.assertEquals(1L, (long) agendamentos.get(0).getSala().getId());
        Assert.assertEquals("Sl1", agendamentos.get(0).getSala().getNome());
    }

    @Test
    public void pesquisarPorNomeSala() {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, "Sl1");

        BDDMockito.given(repository.findPorNomeSala(ArgumentMatchers.anyString())).willReturn(Helper.createList(ag1));
        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);
        List<Agendamento> agendamentos = service.pesquisarPorNomeSala("Sl1");

        Assert.assertEquals(1, agendamentos.size());
        Assert.assertEquals(1L, (long) agendamentos.get(0).getId());
        Assert.assertEquals(datei, agendamentos.get(0).getDataInicio());
        Assert.assertEquals(datef, agendamentos.get(0).getDataFim());
        Assert.assertEquals("Ag1", agendamentos.get(0).getTitulo());
        Assert.assertEquals(1L, (long) agendamentos.get(0).getSala().getId());
        Assert.assertEquals("Sl1", agendamentos.get(0).getSala().getNome());
    }

    @Test
    public void pesquisarPorIdSala() {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, "Sl1");

        BDDMockito.given(repository.findPorIdSala(ArgumentMatchers.anyLong())).willReturn(Helper.createList(ag1));
        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);
        List<Agendamento> agendamentos = service.pesquisarPorIdSala(1L);

        Assert.assertEquals(1, agendamentos.size());
        Assert.assertEquals(1L, (long) agendamentos.get(0).getId());
        Assert.assertEquals(datei, agendamentos.get(0).getDataInicio());
        Assert.assertEquals(datef, agendamentos.get(0).getDataFim());
        Assert.assertEquals("Ag1", agendamentos.get(0).getTitulo());
        Assert.assertEquals(1L, (long) agendamentos.get(0).getSala().getId());
        Assert.assertEquals("Sl1", agendamentos.get(0).getSala().getNome());
    }

    @Test
    public void pesquisarPorPeriodo() {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, "Sl1");

        BDDMockito.given(repository.findPorPeriodo(ArgumentMatchers.any(), ArgumentMatchers.any())).willReturn(Helper.createList(ag1));
        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);
        List<Agendamento> agendamentos = service.pesquisarPorPeriodo(datei, datef);

        Assert.assertEquals(1, agendamentos.size());
        Assert.assertEquals(1L, (long) agendamentos.get(0).getId());
        Assert.assertEquals(datei, agendamentos.get(0).getDataInicio());
        Assert.assertEquals(datef, agendamentos.get(0).getDataFim());
        Assert.assertEquals("Ag1", agendamentos.get(0).getTitulo());
        Assert.assertEquals(1L, (long) agendamentos.get(0).getSala().getId());
        Assert.assertEquals("Sl1", agendamentos.get(0).getSala().getNome());
    }

    @Test
    public void pesquisarPorId() {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, "Sl1");

        BDDMockito.given(repository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(ag1));
        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);
        Agendamento agendamento = service.pesquisarPorId(1L);

        Assert.assertEquals(1L, (long) agendamento.getId());
        Assert.assertEquals(datei, agendamento.getDataInicio());
        Assert.assertEquals(datef, agendamento.getDataFim());
        Assert.assertEquals("Ag1", agendamento.getTitulo());
        Assert.assertEquals(1L, (long) agendamento.getSala().getId());
        Assert.assertEquals("Sl1", agendamento.getSala().getNome());
    }

    @Test
    public void removePorId() {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, "Sl1");

        BDDMockito.given(repository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(ag1));
        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);
        service.remover(1L);
    }

    @Test
    public void criar() {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(null, datei, datef, "Ag1", 1L, null);

        Agendamento ag2 = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, "Sl1");

        BDDMockito.given(repository.save(ArgumentMatchers.any())).willReturn(ag2);
        BDDMockito.given(salaRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(ag1.getSala()));
        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);
        Agendamento agendamentoCriado = service.criar(ag1);

        Assert.assertEquals(1L, (long) agendamentoCriado.getId());
        Assert.assertEquals(datei, agendamentoCriado.getDataInicio());
        Assert.assertEquals(datef, agendamentoCriado.getDataFim());
        Assert.assertEquals("Ag1", agendamentoCriado.getTitulo());
        Assert.assertEquals(1L, (long) agendamentoCriado.getSala().getId());
        Assert.assertEquals("Sl1", agendamentoCriado.getSala().getNome());
    }

    @Test
    public void criar_when_IdSalaNaoEncontrado() {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(null, datei, datef, "Ag1", 3L, null);

        BDDMockito.given(salaRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.empty());
        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);
        try {
            service.criar(ag1);
            Assert.fail();
        } catch (NegocioException e) {
            Assert.assertEquals(MessageConstants.ERR_SALA_NAO_ENCONTRADA, e.getChave());
        }
    }

    @Test
    public void criar_when_PeriodoAgendamentoInvalidoDiferente() {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(null, datef, datei, "Ag1", 1L, null);

        BDDMockito.given(salaRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(Helper.criaSala(1L, "Sl1")));
        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);
        try {
            service.criar(ag1);
            Assert.fail();
        } catch (NegocioException e) {
            Assert.assertEquals(MessageConstants.ERR_PERIODO_AGENDAMENTO_INVALIDO, e.getChave());
        }
    }

    @Test
    public void criar_when_PeriodoAgendamentoInvalidoIguais() {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(null, datef, datei, "Ag1", 1L, null);

        BDDMockito.given(salaRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(Helper.criaSala(1L, "Sl1")));
        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);
        try {
            service.criar(ag1);
            Assert.fail();
        } catch (NegocioException e) {
            Assert.assertEquals(MessageConstants.ERR_PERIODO_AGENDAMENTO_INVALIDO, e.getChave());
        }
    }

    @Test
    public void criar_when_ConflitoAgenda() {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(null, datei, datef, "Ag1", 1L, null);
        Agendamento ag2 = Helper.criaAgendamento(2L, datei, datef, "Ag2", 1L, "Sl1");

        BDDMockito.given(repository.findAllNoPeriodoParaSala(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .willReturn(Helper.createList(ag2));

        BDDMockito.given(salaRepository.findById(ArgumentMatchers.anyLong()))
                .willReturn(Optional.of(Helper.criaSala(1L, "Sl1")));
        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);
        try {
            service.criar(ag1);
            Assert.fail();
        } catch (NegocioException e) {
            Assert.assertEquals(MessageConstants.ERR_CONFLITO_AGENDA_SALA, e.getChave());
        }
    }

    @Test
    public void atualizar() {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento agToUpdate = Helper.criaAgendamento(1L, datei, datef, "Ag3", 1L, null);
        Agendamento agFromDb = Helper.criaAgendamento(1L, datei.plusMonths(2L), datef.plusMonths(2L), "Ag1", 1L, "Sl1");

        BDDMockito.given(repository.findById(1L)).willReturn(Optional.of(agFromDb));
        BDDMockito.given(salaRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(Helper.criaSala(1L, "Sl1")));
        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);
        service.atualizar(agToUpdate);
    }

    @Test
    public void atualizar_when_AgendamentoNaoEncontrado() {

        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento agToUpdate = Helper.criaAgendamento(1L, datei, datef, "Ag3", 1L, null);

        BDDMockito.given(repository.findById(1L)).willReturn(Optional.empty());
        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);
        try {
            service.atualizar(agToUpdate);
            Assert.fail();
        } catch (NegocioException e) {
            Assert.assertEquals(MessageConstants.ERR_AGENDAMENTO_NAO_ENCONTRADO, e.getChave());
        }
    }

    @Test
    public void atualizar_when_SalaNaoEncontrada() {

        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento agToUpdate = Helper.criaAgendamento(1L, datei, datef, "Ag3", 1L, null);
        Agendamento agFromDb = Helper.criaAgendamento(1L, datei.plusMonths(2L), datef.plusMonths(2L), "Ag1", 1L, "Sl1");

        BDDMockito.given(salaRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.empty());
        BDDMockito.given(repository.findById(1L)).willReturn(Optional.of(agFromDb));

        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);
        try {
            service.atualizar(agToUpdate);
            Assert.fail();
        } catch (NegocioException e) {
            Assert.assertEquals(MessageConstants.ERR_SALA_NAO_ENCONTRADA, e.getChave());
        }
    }

    @Test
    public void atualizar_when_SalaAlteradaComConflitoAgenda() {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento agToUpdate = Helper.criaAgendamento(1L, datei, datef, "Ag1", 3L, null);
        Agendamento agFromDb = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, "Sl1");
        Agendamento agFromDbSala3 = Helper.criaAgendamento(1L, datei, datef, "Ag3", 3L, "Sl3");

        BDDMockito.given(repository.findAllNoPeriodoParaSala(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .willReturn(Helper.createList(agFromDbSala3));

        BDDMockito.given(repository.findById(1L)).willReturn(Optional.of(agFromDb));
        BDDMockito.given(salaRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(Helper.criaSala(3L, "Sl3")));
        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);

        try {
            service.atualizar(agToUpdate);
        } catch (NegocioException e) {
            Assert.assertEquals(MessageConstants.ERR_CONFLITO_AGENDA_SALA, e.getChave());
        }
    }

    @Test
    public void atualizar_when_PeriodoAlteradoConflitoAgendaDataFimDiferente() {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento agToUpdate = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, null);
        Agendamento agFromDb = Helper.criaAgendamento(1L, datei, datef.plusMonths(2L), "Ag1", 1L, "Sl1");

        BDDMockito.given(repository.findAllNoPeriodoParaSala(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .willReturn(Helper.createList(agFromDb));

        BDDMockito.given(repository.findById(1L)).willReturn(Optional.of(agFromDb));
        BDDMockito.given(salaRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(Helper.criaSala(3L, "Sl3")));
        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);

        try {
            service.atualizar(agToUpdate);
        } catch (NegocioException e) {
            Assert.assertEquals(MessageConstants.ERR_CONFLITO_AGENDA_SALA, e.getChave());
        }
    }

    @Test
    public void atualizar_when_PeriodoAlteradoConflitoAgendaDataInicioDiferente() {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento agToUpdate = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, null);
        Agendamento agFromDb = Helper.criaAgendamento(1L, datei.plusMonths(2L), datef, "Ag1", 1L, "Sl1");

        BDDMockito.given(repository.findAllNoPeriodoParaSala(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .willReturn(Helper.createList(agFromDb));

        BDDMockito.given(repository.findById(1L)).willReturn(Optional.of(agFromDb));
        BDDMockito.given(salaRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(Helper.criaSala(3L, "Sl3")));
        AgendamentoServiceImpl service = new AgendamentoServiceImpl(messageSource, repository, salaRepository);

        try {
            service.atualizar(agToUpdate);
        } catch (NegocioException e) {
            Assert.assertEquals(MessageConstants.ERR_CONFLITO_AGENDA_SALA, e.getChave());
        }
    }

    static class Helper {

        static <E> List<E> createList(E... elementos) {
            return Arrays.asList(elementos);
        }

        static Agendamento criaAgendamento(Long idAg, LocalDateTime di, LocalDateTime df, String agTt, Long idSl, String slNm) {
            Agendamento a1 = new Agendamento();
            a1.setId(idAg);
            a1.setTitulo(agTt);
            a1.setDataInicio(di);
            a1.setDataFim(df);
            a1.setSala(criaSala(idSl, slNm));
            return a1;
        }

        static Sala criaSala(Long idSl, String slNm) {
            Sala s1 = new Sala();
            s1.setId(idSl);
            s1.setNome(slNm);
            return s1;
        }
    }
}

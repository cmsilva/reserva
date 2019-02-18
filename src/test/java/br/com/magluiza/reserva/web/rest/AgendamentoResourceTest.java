package br.com.magluiza.reserva.web.rest;

import br.com.magluiza.reserva.ReservaApp;
import br.com.magluiza.reserva.core.Constants;
import br.com.magluiza.reserva.core.MessageConstants;
import br.com.magluiza.reserva.core.exception.CustomParameterizedException;
import br.com.magluiza.reserva.domain.Agendamento;
import br.com.magluiza.reserva.domain.Sala;
import br.com.magluiza.reserva.service.AgendamentoService;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AgendamentoResource REST controller.
 *
 * @see AgendamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservaApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AgendamentoResourceTest {

    private MockMvc restAgendamentoMockMvc;

    @MockBean
    AgendamentoService agendamentoService;

    @Before
    public void setup() {
        AgendamentoResource agendamentoResource = new AgendamentoResource(agendamentoService);
        this.restAgendamentoMockMvc = MockMvcBuilders
                .standaloneSetup(agendamentoResource)
                .build();
    }

    @Test
    public void recuperarTodosAgendamentos() throws Exception {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, "Sl1");
        given(agendamentoService.pesquisarTudo()).willReturn(Helper.createList(ag1));

        restAgendamentoMockMvc.perform(get("/api/agendamento/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.agendamentos", hasSize(1)))
                .andExpect(jsonPath("$.agendamentos[0].id", Is.is(1)))
                .andExpect(jsonPath("$.agendamentos[0].titulo", Is.is("AG1")))
                .andExpect(jsonPath("$.agendamentos[0].sala.id", Is.is(1)))
                .andExpect(jsonPath("$.agendamentos[0].sala.nome", Is.is("SL1")));
    }

    @Test
    public void recuperarPorId() throws Exception {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, "Sl1");
        given(agendamentoService.pesquisarPorId(1L)).willReturn(ag1);

        restAgendamentoMockMvc.perform(get("/api/agendamento/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.titulo", Is.is("AG1")))
                .andExpect(jsonPath("$.sala.id", Is.is(1)))
                .andExpect(jsonPath("$.sala.nome", Is.is("SL1")));
    }

    @Test
    public void pesquisarPorPeriodo() throws Exception {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, "Sl1");
        given(agendamentoService.pesquisarPorPeriodo(datei, datef)).willReturn(Helper.createList(ag1));

        restAgendamentoMockMvc.perform(get("/api/agendamento/").param("dataInicio", datei.toString()).param("dataFim", datef.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.agendamentos", hasSize(1)))
                .andExpect(jsonPath("$.agendamentos[0].id", Is.is(1)))
                .andExpect(jsonPath("$.agendamentos[0].titulo", Is.is("AG1")))
                .andExpect(jsonPath("$.agendamentos[0].sala.id", Is.is(1)))
                .andExpect(jsonPath("$.agendamentos[0].sala.nome", Is.is("SL1")));
    }

    @Test
    public void pesquisarPorNome() throws Exception {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, "Sl1");
        String termoPesquisa = "Sl";
        given(agendamentoService.pesquisarPorNomeSala(termoPesquisa)).willReturn(Helper.createList(ag1));

        restAgendamentoMockMvc.perform(get("/api/agendamento/").param("sala.nome", termoPesquisa))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.agendamentos", hasSize(1)))
                .andExpect(jsonPath("$.agendamentos[0].id", Is.is(1)))
                .andExpect(jsonPath("$.agendamentos[0].titulo", Is.is("AG1")))
                .andExpect(jsonPath("$.agendamentos[0].sala.id", Is.is(1)))
                .andExpect(jsonPath("$.agendamentos[0].sala.nome", Is.is("SL1")));
    }

    @Test
    public void pesquisarPorIdSala() throws Exception {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, "Sl1");
        given(agendamentoService.pesquisarPorIdSala(1L)).willReturn(Helper.createList(ag1));

        restAgendamentoMockMvc.perform(get("/api/agendamento/").param("sala.id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.agendamentos", hasSize(1)))
                .andExpect(jsonPath("$.agendamentos[0].id", Is.is(1)))
                .andExpect(jsonPath("$.agendamentos[0].titulo", Is.is("AG1")))
                .andExpect(jsonPath("$.agendamentos[0].sala.id", Is.is(1)))
                .andExpect(jsonPath("$.agendamentos[0].sala.nome", Is.is("SL1")));
    }

    @Test
    public void criarAgendamento() throws Exception {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);

        Agendamento ag1 = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, "Sl1");
        given(agendamentoService.criar(any())).willReturn(ag1);

        String content = "{\"titulo\":\"Ag1\",\"dataInicio\":\"2018-01-01T12:30:00\",\"dataFim\":\"2018-03-01T12:30:00\",\"sala\":{\"id\":1}}";
        restAgendamentoMockMvc.perform(post("/api/agendamento/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.titulo", Is.is("AG1")))
                .andExpect(jsonPath("$.sala.id", Is.is(1)))
                .andExpect(jsonPath("$.sala.nome", Is.is("SL1")));
    }

    @Test
    public void criarAgendamento_when_IdSalaNaoPreenchida() throws Exception {
        String content = "{\"titulo\":\"Ag1\",\"dataInicio\":\"2018-01-01T12:30:00\",\"dataFim\":\"2018-03-01T12:30:00\",\"sala\":{\"id\": null}}";
        try {
            restAgendamentoMockMvc.perform(post("/api/agendamento/")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(content));

        } catch (NestedServletException e) {
            CustomParameterizedException cause = (CustomParameterizedException) e.getCause();
            Assert.assertEquals(cause.getErrorDto().getMessage(), MessageConstants.ERR_FIELD_REQUIRED);
            Assert.assertEquals(cause.getErrorDto().getParamMap().get("param0"), Constants.FIELD_AGENDAMENTO_SALA_ID);
        }
    }

    @Test
    public void atualizarAgendamento() throws Exception {
        LocalDateTime datei = LocalDateTime.of(2018, 1, 1, 12, 30, 0);
        LocalDateTime datef = LocalDateTime.of(2018, 3, 1, 12, 30, 0);

        Agendamento ag1 = Helper.criaAgendamento(1L, datei, datef, "Ag1", 1L, "Sl1");
        given(agendamentoService.atualizar(any())).willReturn(ag1);

        String content = "{\"id\":\"1\",\"titulo\":\"Ag1\",\"dataInicio\":\"2018-01-01T12:30:00\",\"dataFim\":\"2018-03-01T12:30:00\",\"sala\":{\"id\":1}}";
        restAgendamentoMockMvc.perform(put("/api/agendamento/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isNoContent());
    }

    @Test
    public void atualizarAgendamento_when_IdAgendamentoNaoPreenchida() throws Exception {
        try {
            String content = "{\"id\":\"null\",\"titulo\":\"Ag1\",\"dataInicio\":\"2018-01-01T12:30:00\",\"dataFim\":\"2018-03-01T12:30:00\",\"sala\":{\"id\":1}}";
            restAgendamentoMockMvc.perform(put("/api/agendamento/")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(content))
                    .andExpect(status().isNoContent());

        } catch (NestedServletException e) {
            CustomParameterizedException cause = (CustomParameterizedException) e.getCause();
            Assert.assertEquals(cause.getErrorDto().getMessage(), MessageConstants.ERR_FIELD_REQUIRED);
            Assert.assertEquals(cause.getErrorDto().getParamMap().get("param0"), Constants.FIELD_AGENDAMENTO_ID);
        }
    }

    @Test
    public void atualizarAgendamento_when_IdSalaNaoPreenchida() throws Exception {
        try {
            String content = "{\"id\":\"1\",\"titulo\":\"Ag1\",\"dataInicio\":\"2018-01-01T12:30:00\",\"dataFim\":\"2018-03-01T12:30:00\",\"sala\":{\"id\":null}}";
            restAgendamentoMockMvc.perform(put("/api/agendamento/")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(content))
                    .andExpect(status().isNoContent());

        } catch (NestedServletException e) {
            CustomParameterizedException cause = (CustomParameterizedException) e.getCause();
            Assert.assertEquals(cause.getErrorDto().getMessage(), MessageConstants.ERR_FIELD_REQUIRED);
            Assert.assertEquals(cause.getErrorDto().getParamMap().get("param0"), Constants.FIELD_AGENDAMENTO_SALA_ID);
        }
    }

    @Test
    public void removerAgendamento() throws Exception {
        restAgendamentoMockMvc.perform(delete("/api/agendamento/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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
            Sala s1 = new Sala();
            s1.setId(idSl);
            s1.setNome(slNm);
            a1.setSala(s1);
            return a1;
        }
    }
}
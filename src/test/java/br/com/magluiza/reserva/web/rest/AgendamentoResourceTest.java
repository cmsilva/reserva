package br.com.magluiza.reserva.web.rest;

import br.com.magluiza.reserva.ReservaApp;
import br.com.magluiza.reserva.domain.Agendamento;
import br.com.magluiza.reserva.domain.Sala;
import br.com.magluiza.reserva.service.AgendamentoService;
import br.com.magluiza.reserva.service.SalaService;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AgendamentoResource REST controller.
 *
 * @see AgendamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservaApp.class)
public class AgendamentoResourceTest {

    private MockMvc restLogsMockMvc;

    @MockBean
    AgendamentoService agendamentoService;

    @Before
    public void setup() {
        AgendamentoResource logsResource = new AgendamentoResource(agendamentoService);
        this.restLogsMockMvc = MockMvcBuilders
                .standaloneSetup(logsResource)
                .build();
    }

    @Test
    public void recuperarTodosAgendamentos() throws Exception {
        LocalDateTime date = LocalDateTime.of(2016, 1, 1, 12, 30, 0);
        Agendamento ag1 = Helper.criaAgendamento(1L, date, date, "Ag1", 1L, "Sl1");
        given(agendamentoService.pesquisarTudo()).willReturn(Helper.createList(ag1));

        restLogsMockMvc.perform(get("/api/agendamento/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.agendamentos", hasSize(1)))
                .andExpect(jsonPath("$.agendamentos[0].id", Is.is(1)))
                .andExpect(jsonPath("$.agendamentos[0].titulo", Is.is("AG1")));
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
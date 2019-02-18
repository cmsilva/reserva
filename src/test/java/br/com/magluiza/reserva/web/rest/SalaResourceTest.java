package br.com.magluiza.reserva.web.rest;

import br.com.magluiza.reserva.ReservaApp;
import br.com.magluiza.reserva.core.Constants;
import br.com.magluiza.reserva.core.MessageConstants;
import br.com.magluiza.reserva.core.exception.CustomParameterizedException;
import br.com.magluiza.reserva.domain.Sala;
import br.com.magluiza.reserva.service.SalaService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SalaResource REST controller.
 *
 * @see SalaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservaApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalaResourceTest {

    private MockMvc restSalaMockMvc;

    @MockBean
    SalaService salaService;

    @Before
    public void setup() {
        SalaResource salaResource = new SalaResource(salaService);
        this.restSalaMockMvc = MockMvcBuilders
                .standaloneSetup(salaResource)
                .build();
    }

    @Test
    public void recuperarTodasSalas() throws Exception {
        Sala s1 = Helper.criarSala(1L, "Sl1");
        given(salaService.pesquisarTudo()).willReturn(Helper.createList(s1));

        restSalaMockMvc.perform(get("/api/sala/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.salas", hasSize(1)))
                .andExpect(jsonPath("$.salas[0].id", Is.is(1)))
                .andExpect(jsonPath("$.salas[0].nome", Is.is("SL1")));
    }

    @Test
    public void recuperarPorId() throws Exception {
        Sala s1 = Helper.criarSala(1L, "Sl1");
        given(salaService.pesquisarPorId(any())).willReturn(s1);

        restSalaMockMvc.perform(get("/api/sala/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.nome", Is.is("SL1")));
    }

    @Test
    public void criarSala() throws Exception {
        Sala s1 = Helper.criarSala(1L, "Sl1");
        given(salaService.criar(any())).willReturn(s1);

        String content = "{\"nome\":\"Sl1\"}";
        restSalaMockMvc.perform(post("/api/sala/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.nome", Is.is("SL1")));

    }

    @Test
    public void atualizarSala() throws Exception {
        Sala s1 = Helper.criarSala(1L, "Sl2");
        given(salaService.atualizar(any())).willReturn(s1);

        String content = "{\"id\":\"1\",\"nome\":\"Sl2\"}";
        restSalaMockMvc.perform(put("/api/sala/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isNoContent());

    }

    @Test
    public void atualizarSala_when_IdSalaNaoPreenchido() throws Exception {
        String content = "{\"id\":\"null\",\"nome\":\"Sl2\"}";
        try {
            restSalaMockMvc.perform(put("/api/sala/")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(content))
                    .andExpect(status().isNoContent());
        } catch (NestedServletException e) {
            CustomParameterizedException cause = (CustomParameterizedException) e.getCause();
            Assert.assertEquals(cause.getErrorDto().getMessage(), MessageConstants.ERR_FIELD_REQUIRED);
            Assert.assertEquals(cause.getErrorDto().getParamMap().get("param0"), Constants.FIELD_SALA_ID);
        }
    }

    @Test
    public void removerAgendamento() throws Exception {
        restSalaMockMvc.perform(delete("/api/sala/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    static class Helper {

        static <E> List<E> createList(E... elementos) {
            return Arrays.asList(elementos);
        }

        static Sala criarSala(Long idSl, String slNm) {
            Sala sala = new Sala();
            sala.setId(idSl);
            sala.setNome(slNm);
            sala.setAgendamentos(new ArrayList<>());
            return sala;
        }
    }
}
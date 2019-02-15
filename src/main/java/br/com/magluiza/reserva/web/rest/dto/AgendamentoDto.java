package br.com.magluiza.reserva.web.rest.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class AgendamentoDto implements Serializable {

    private static final long serialVersionUID = -7535450871070866294L;

    private Long id;

    @NotNull
    @NotBlank
    private String titulo;

    @NotNull
    private Date dataInicio;

    @NotNull
    private Date dataFim;

    private SalaDto sala;

    public AgendamentoDto() {
        super();
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public SalaDto getSala() {
        return sala;
    }

    public void setSala(SalaDto sala) {
        this.sala = sala;
    }
}

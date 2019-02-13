package br.com.magluiza.reserva.web.rest.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SalaDto implements Serializable {

    private static final long serialVersionUID = -7535450871070866294L;

    private Long id;

    @NotNull
    @NotBlank
    private String nome;

    public SalaDto() {
        super();
    }

    public SalaDto(String nome) {
        this.nome = nome;
    }

    public SalaDto(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

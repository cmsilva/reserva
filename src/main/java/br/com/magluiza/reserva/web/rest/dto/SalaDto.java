package br.com.magluiza.reserva.web.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaDto implements Serializable {

    private static final long serialVersionUID = -7535450871070866294L;
    private Long id;

    @NotNull
    @NotBlank
    private String nome;
}

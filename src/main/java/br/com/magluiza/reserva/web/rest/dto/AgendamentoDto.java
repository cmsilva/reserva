package br.com.magluiza.reserva.web.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoDto implements Serializable {

    private static final long serialVersionUID = -7535450871070866294L;

    private Long id;

    @NotNull
    @NotBlank
    private String titulo;

    @NotNull
    private LocalDateTime dataInicio;

    @NotNull
    private LocalDateTime dataFim;

    private SalaDto sala;

}

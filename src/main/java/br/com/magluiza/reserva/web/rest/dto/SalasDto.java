package br.com.magluiza.reserva.web.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalasDto implements Serializable {

    private static final long serialVersionUID = -3048172558584185868L;
    private List<SalaDto> salas;
}

package br.com.magluiza.reserva.web.rest.dto;

import java.io.Serializable;
import java.util.List;

public class SalasDto implements Serializable {

    private static final long serialVersionUID = -3048172558584185868L;
    private List<SalaDto> salas;

    public SalasDto(List<SalaDto> salas) {
        this.salas = salas;
    }

    public List<SalaDto> getSalas() {
        return salas;
    }

    public void setSalas(List<SalaDto> salas) {
        this.salas = salas;
    }
}

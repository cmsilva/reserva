package br.com.magluiza.reserva.web.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SalasDto {
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

    public void add(SalaDto salaDto) {
        if (Objects.isNull(salas)) {
            this.salas = new ArrayList<>(10);
        }
        this.salas.add(salaDto);
    }

    public void clear() {
        if (Objects.isNull(salas)) {
            this.salas = new ArrayList<>(10);
        }
        this.salas.clear();
    }
}

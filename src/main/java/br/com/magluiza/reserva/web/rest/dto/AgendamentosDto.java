package br.com.magluiza.reserva.web.rest.dto;

import java.io.Serializable;
import java.util.List;

public class AgendamentosDto implements Serializable {

    private static final long serialVersionUID = -3048172558584185868L;
    private List<AgendamentoDto> agendamentos;

    public AgendamentosDto(List<AgendamentoDto> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public List<AgendamentoDto> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<AgendamentoDto> agendamentos) {
        this.agendamentos = agendamentos;
    }
}

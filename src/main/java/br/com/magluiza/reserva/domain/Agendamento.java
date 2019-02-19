package br.com.magluiza.reserva.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "agendamento")
@Data
@AllArgsConstructor
public class Agendamento extends AbstractEntity {
    private static final long serialVersionUID = -6026445659348010835L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String titulo;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    public Agendamento() {
        super();
    }

    public Agendamento(Long id) {
        this.id = id;
    }
}

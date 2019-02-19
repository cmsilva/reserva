package br.com.magluiza.reserva.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "sala")
@Data
@AllArgsConstructor
public class Sala extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "sala", targetEntity = Agendamento.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Agendamento> agendamentos;

    public Sala() {
        super();
    }

    public Sala(Long id) {
        this.id = id;
    }
}

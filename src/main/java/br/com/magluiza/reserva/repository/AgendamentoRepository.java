package br.com.magluiza.reserva.repository;

import br.com.magluiza.reserva.domain.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    @Query("SELECT ag FROM Agendamento ag WHERE ag.sala.id = :idSala AND (:dataInicio < ag.dataFim AND :dataFim > ag.dataInicio)")
    List<Agendamento> findAllNoPeriodoParaSala(@Param("idSala") Long idSala, @Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
}
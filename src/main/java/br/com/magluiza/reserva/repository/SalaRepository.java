package br.com.magluiza.reserva.repository;

import br.com.magluiza.reserva.domain.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
}

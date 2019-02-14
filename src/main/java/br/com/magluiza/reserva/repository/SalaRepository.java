package br.com.magluiza.reserva.repository;

import br.com.magluiza.reserva.domain.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {

    Optional<Sala> findByNomeIgnoreCase(String nome);
}

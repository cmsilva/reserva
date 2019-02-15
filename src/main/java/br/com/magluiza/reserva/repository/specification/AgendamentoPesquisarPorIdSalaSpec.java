package br.com.magluiza.reserva.repository.specification;

import br.com.magluiza.reserva.domain.Agendamento;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Spec(path = "sala.id", params = "sala.id", spec = Equal.class)
public interface AgendamentoPesquisarPorIdSalaSpec extends Specification<Agendamento> {
}
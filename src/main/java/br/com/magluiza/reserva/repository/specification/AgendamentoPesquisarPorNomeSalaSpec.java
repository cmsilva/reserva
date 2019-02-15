package br.com.magluiza.reserva.repository.specification;

import br.com.magluiza.reserva.domain.Agendamento;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Spec(path = "sala.nome", params = "sala.nome", spec = LikeIgnoreCase.class)
public interface AgendamentoPesquisarPorNomeSalaSpec extends Specification<Agendamento> {
}
package br.com.magluiza.reserva.repository.specification;

import br.com.magluiza.reserva.domain.Agendamento;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({@Spec(path = "dataInicio", params = {"dataFim"}, config = "yyyy-MM-dd", spec = LessThanOrEqual.class),
        @Spec(path = "dataFim", params = {"dataInicio"}, config = "yyyy-MM-dd", spec = GreaterThanOrEqual.class)})
public interface AgendamentoPesquisarPorPeriodoSpec extends Specification<Agendamento> {
}
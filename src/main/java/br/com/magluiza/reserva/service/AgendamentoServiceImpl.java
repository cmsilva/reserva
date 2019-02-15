package br.com.magluiza.reserva.service;

import br.com.magluiza.reserva.core.Constants;
import br.com.magluiza.reserva.core.MessageConstants;
import br.com.magluiza.reserva.core.exception.NegocioException;
import br.com.magluiza.reserva.domain.Agendamento;
import br.com.magluiza.reserva.domain.Sala;
import br.com.magluiza.reserva.repository.AgendamentoRepository;
import br.com.magluiza.reserva.repository.SalaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AgendamentoServiceImpl implements AgendamentoService {

    private static final Logger log = LoggerFactory.getLogger(AgendamentoServiceImpl.class);

    private final AgendamentoRepository repository;
    private final SalaRepository salaRepository;
    private final MessageSource messageSource;

    public AgendamentoServiceImpl(MessageSource messageSource, AgendamentoRepository repository, SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
        this.repository = repository;
        this.messageSource = messageSource;
    }

    @Override
    public List<Agendamento> recuperarTudo() {
        return repository.findAll();
    }

    @Override
    public List<Agendamento> recuperarTudo(Specification<Agendamento> specification) {
        return repository.findAll(specification);
    }

    @Override
    public Agendamento recuperarPorId(Long id) {
        validarAcao(new Agendamento(id), Constants.Acao.PESQUISAR_ID);
        return repository.findById(id).get();
    }

    @Override
    public void remover(Long id) {
        validarAcao(new Agendamento(id), Constants.Acao.REMOVER);
        repository.delete(repository.findById(id).get());
    }

    @Override
    public Agendamento criar(final Agendamento agendamento) {
        validarAcao(agendamento, Constants.Acao.CRIAR);

        agendamento.setSala(salaRepository.findById(agendamento.getSala().getId()).get());
        return repository.save(agendamento);
    }

    @Override
    public Agendamento atualizar(Agendamento agendamento) {
        validarAcao(agendamento, Constants.Acao.ATUALIZAR);

        Agendamento agendamentoParaAtualizar = repository.findById(agendamento.getId()).get();
        agendamentoParaAtualizar.setTitulo(agendamento.getTitulo());
        agendamentoParaAtualizar.setDataInicio(agendamento.getDataInicio());
        agendamentoParaAtualizar.setDataFim(agendamento.getDataFim());
        agendamentoParaAtualizar.setSala(salaRepository.findById(agendamento.getSala().getId()).get());
        return repository.save(agendamentoParaAtualizar);
    }

    private void validarAcao(Agendamento agendamento, Constants.Acao acao) {
        if (Constants.Acao.ATUALIZAR.equals(acao) || Constants.Acao.REMOVER.equals(acao) || Constants.Acao.PESQUISAR_ID.equals(acao)) {
            Optional<Agendamento> agDB = repository.findById(agendamento.getId());
            if (!agDB.isPresent()) {
                log.error("Ação: {}, Agendamento não encontrado, Id: {}", acao, agendamento.getId());
                throw new NegocioException(MessageConstants.ERR_AGENDAMENTO_NAO_ENCONTRADO, messageSource);
            }
        }
        if (Constants.Acao.ATUALIZAR.equals(acao) || Constants.Acao.CRIAR.equals(acao)) {
            Optional<Sala> slDb = this.salaRepository.findById(agendamento.getSala().getId());
            if (!slDb.isPresent()) {
                log.error("Ação: {}, Sala não encontrada, Id: {}", acao, agendamento.getSala().getId());
                throw new NegocioException(MessageConstants.ERR_SALA_NAO_ENCONTRADA, messageSource);
            }
            if (isPeriodoAgendamentoInvalido(agendamento.getDataInicio(), agendamento.getDataFim())) {
                log.error("Ação: {}, Período de agendamento inválido, Id: {}, Início: {}, Fim: {}", acao, agendamento.getId(), agendamento.getDataInicio(), agendamento.getDataFim());
                throw new NegocioException(MessageConstants.ERR_PERIODO_AGENDAMENTO_INVALIDO, messageSource);
            }

            if (existeConflitoAgendaSala(agendamento.getSala().getId(), agendamento.getDataInicio(), agendamento.getDataFim())) {
                log.error("Ação: {}, Conflito na agenda, Id: {}, Início: {}, Fim: {}", acao, agendamento.getId(), agendamento.getDataInicio(), agendamento.getDataFim());
                throw new NegocioException(MessageConstants.ERR_CONFLITO_AGENDA_SALA, messageSource);
            }
        }
    }

    private boolean existeConflitoAgendaSala(Long idSala, Date dataInicio, Date dataFim) {
        List<Agendamento> agendamentos = repository.findAllNoPeriodoParaSala(idSala, dataInicio, dataFim);
        return !agendamentos.isEmpty();
    }

    private boolean isPeriodoAgendamentoInvalido(Date dataInicio, Date dataFim) {
        return dataInicio.after(dataFim) || dataInicio.equals(dataFim);
    }
}

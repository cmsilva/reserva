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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    public List<Agendamento> pesquisarTudo() {
        return repository.findAll();
    }

    @Override
    public List<Agendamento> pesquisarPorNomeSala(String nome) {
        return repository.findPorNomeSala(nome);
    }

    @Override
    public List<Agendamento> pesquisarPorIdSala(Long idSala) {
        return repository.findPorIdSala(idSala);
    }

    @Override
    public List<Agendamento> pesquisarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return repository.findPorPeriodo(dataInicio, dataFim);
    }

    @Override
    public Agendamento pesquisarPorId(Long id) {
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
        Optional<Agendamento> agDB = Optional.empty();
        if (!Objects.isNull(agendamento.getId())) {
            agDB = repository.findById(agendamento.getId());
        }

        if (Constants.Acao.ATUALIZAR.equals(acao) || Constants.Acao.REMOVER.equals(acao) || Constants.Acao.PESQUISAR_ID.equals(acao)) {
            if (!agDB.isPresent()) {
                log.error("Ação: {}, Agendamento não encontrado, Id: {}", acao, agendamento.getId());
                throw new NegocioException(MessageConstants.ERR_AGENDAMENTO_NAO_ENCONTRADO, messageSource);
            }
        }

        if (Constants.Acao.ATUALIZAR.equals(acao) || Constants.Acao.CRIAR.equals(acao)) {
            Optional<Sala> slDB = this.salaRepository.findById(agendamento.getSala().getId());
            if (!slDB.isPresent()) {
                log.error("Ação: {}, Sala não encontrada, Id: {}", acao, agendamento.getSala().getId());
                throw new NegocioException(MessageConstants.ERR_SALA_NAO_ENCONTRADA, messageSource);
            }
            if (isPeriodoAgendamentoInvalido(agendamento.getDataInicio(), agendamento.getDataFim())) {
                log.error("Ação: {}, Período de agendamento inválido, Id: {}, Início: {}, Fim: {}", acao, agendamento.getId(), agendamento.getDataInicio(), agendamento.getDataFim());
                throw new NegocioException(MessageConstants.ERR_PERIODO_AGENDAMENTO_INVALIDO, messageSource);
            }

            if (!agDB.isPresent() || (agDB.isPresent() && isNecessarioVerificarConflitoAgenda(agDB.get(), agendamento))) {
                if (isExisteConflitoAgendaSala(agendamento.getSala().getId(), agendamento.getDataInicio(), agendamento.getDataFim())) {
                    log.error("Ação: {}, Conflito na agenda, Id: {}, Início: {}, Fim: {}", acao, agendamento.getId(), agendamento.getDataInicio(), agendamento.getDataFim());
                    throw new NegocioException(MessageConstants.ERR_CONFLITO_AGENDA_SALA, messageSource);
                }
            }
        }
    }

    private boolean isNecessarioVerificarConflitoAgenda(Agendamento agAntigo, Agendamento agNovo) {
        return !(
                agAntigo.getDataInicio().equals(agNovo.getDataInicio()) &&
                        agAntigo.getDataFim().equals(agNovo.getDataFim()) &&
                        agAntigo.getSala().getId().equals(agNovo.getSala().getId()));
    }

    private boolean isExisteConflitoAgendaSala(Long idSala, LocalDateTime dataInicio, LocalDateTime dataFim) {
        List<Agendamento> agendamentos = repository.findAllNoPeriodoParaSala(idSala, dataInicio, dataFim);
        return !agendamentos.isEmpty();
    }

    private boolean isPeriodoAgendamentoInvalido(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return dataInicio.isAfter(dataFim) || dataInicio.equals(dataFim);
    }
}

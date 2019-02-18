package br.com.magluiza.reserva.service;

import br.com.magluiza.reserva.core.Constants;
import br.com.magluiza.reserva.core.MessageConstants;
import br.com.magluiza.reserva.core.exception.NegocioException;
import br.com.magluiza.reserva.domain.Sala;
import br.com.magluiza.reserva.repository.SalaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SalaServiceImpl implements SalaService {

    private static final Logger log = LoggerFactory.getLogger(SalaServiceImpl.class);
    private final SalaRepository repository;
    private final MessageSource messageSource;

    public SalaServiceImpl(MessageSource messageSource, SalaRepository repository) {
        this.messageSource = messageSource;
        this.repository = repository;
    }

    @Override
    public List<Sala> pesquisarTudo() {
        return repository.findAll();
    }

    @Override
    public Sala pesquisarPorId(Long id) {
        validarAcao(new Sala(id), Constants.Acao.PESQUISAR_ID);
        return repository.findById(id).get();
    }

    @Override
    public void remover(Long id) {
        validarAcao(new Sala(id), Constants.Acao.REMOVER);
        repository.delete(repository.findById(id).get());
    }

    @Override
    public Sala criar(Sala sala) {
        validarAcao(sala, Constants.Acao.CRIAR);
        return repository.save(sala);
    }

    @Override
    public Sala atualizar(Sala sala) {
        validarAcao(sala, Constants.Acao.ATUALIZAR);

        Sala salaParaAtualizar = repository.findById(sala.getId()).get();
        salaParaAtualizar.setNome(sala.getNome());
        return repository.save(salaParaAtualizar);
    }

    private void validarAcao(Sala sala, Constants.Acao acao) {
        if (Constants.Acao.ATUALIZAR.equals(acao) || Constants.Acao.REMOVER.equals(acao) || Constants.Acao.PESQUISAR_ID.equals(acao)) {
            Optional<Sala> slDb = repository.findById(sala.getId());
            if (!slDb.isPresent()) {
                log.error("Ação: {}, Sala não encontrada, Id: {}", acao, sala.getId());
                throw new NegocioException(MessageConstants.ERR_SALA_NAO_ENCONTRADA, messageSource);
            }
        }
        if (Constants.Acao.ATUALIZAR.equals(acao) || Constants.Acao.CRIAR.equals(acao)) {
            if (existeSalaComMesmoNome(sala.getNome())) {
                log.error("Ação: {}, Sala já existe, Nome: {}", acao, sala.getNome());
                throw new NegocioException(MessageConstants.ERR_SALA_COM_MESMO_NOME, messageSource);
            }
        }
    }

    private boolean existeSalaComMesmoNome(String nome) {
        return repository.findByNomeIgnoreCase(nome).isPresent();
    }
}

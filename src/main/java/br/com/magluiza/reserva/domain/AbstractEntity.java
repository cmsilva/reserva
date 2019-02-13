package br.com.magluiza.reserva.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

/**
 * Classe abstrata base para entidades que devem conter
 * as informa&ccedil;&otilde;es da data de &uacute;ltima
 * modifica&ccedil;&atilde;o e a data de cria&ccedil;&atilde;o
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedDate
    @Column(name = "data_criacao", updatable = false)
    @JsonIgnore
    private Instant dataCriacao = Instant.now();

    @LastModifiedDate
    @Column(name = "data_ultima_modificacao")
    @JsonIgnore
    private Instant dataUltimaModificacao = Instant.now();

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Instant dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Instant getDataUltimaModificacao() {
        return dataUltimaModificacao;
    }

    public void setDataUltimaModificacao(Instant dataUltimaModificacao) {
        this.dataUltimaModificacao = dataUltimaModificacao;
    }
}

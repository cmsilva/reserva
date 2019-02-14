package br.com.magluiza.reserva.core.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

public class NegocioException extends RuntimeException {
    private static final long serialVersionUID = -6360892962340997774L;
    private String chave;

    public NegocioException(String chave, MessageSource messageSource) {
        super(new MessageSourceAccessor(messageSource).getMessage(chave));
        this.chave = chave;
    }

    public String getChave() {
        return chave;
    }
}

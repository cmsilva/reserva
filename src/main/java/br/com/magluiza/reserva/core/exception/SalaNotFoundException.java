package br.com.magluiza.reserva.core.exception;

public class SalaNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -6046927401636687599L;
    private final Long id;

    public SalaNotFoundException(Long id, String message) {
        super(message);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

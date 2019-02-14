package br.com.magluiza.reserva.core.exception;

public class SalaExistsException extends RuntimeException {

    private static final long serialVersionUID = -3484921691772222356L;
    private final String nome;

    public SalaExistsException(String nome, String message) {
        super(message);
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}

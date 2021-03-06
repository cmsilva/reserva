package br.com.magluiza.reserva.core;

/**
 * Constantes globais da aplica&ccdil.&atilde;o.
 */
public final class Constants {

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_SWAGGER = "swagger";

    public static final String FIELD_AGENDAMENTO_ID = "agendamento.id";
    public static final String FIELD_AGENDAMENTO_SALA_ID = "agendamento.sala.id";
    public static final String FIELD_SALA_ID = "sala.id";

    public enum Acao {
        CRIAR, ATUALIZAR, REMOVER, PESQUISAR_ID
    }

    private Constants() {
        super();
    }
}

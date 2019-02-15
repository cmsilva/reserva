package br.com.magluiza.reserva.core;

public final class MessageConstants {

    public static final String ERR_VALIDATION = "error.validation";
    public static final String ERR_METHOD_NOT_SUPPORTED = "error.methodNotSupported";
    public static final String ERR_INTERNAL_SERVER_ERROR = "error.internalServerError";
    public static final String ERR_MEDIA_NOT_SUPPORTED = "error.methodNotSupported";
    public static final String ERR_FIELD_REQUIRED = "error.fieldRequired";

    public static final String ERR_AGENDAMENTO_NAO_ENCONTRADO = "error.agendamentoNaoEncontrado";
    public static final String ERR_PERIODO_AGENDAMENTO_INVALIDO = "error.periodoAgendamentoInvalido";
    public static final String ERR_CONFLITO_AGENDA_SALA = "error.conflitoAgendaSala";
    public static final String ERR_SALA_NAO_ENCONTRADA = "error.salaNaoEncontrada";
    public static final String ERR_SALA_COM_MESMO_NOME = "error.salaComMesmoNome";

    private MessageConstants() {
        super();
    }
}
